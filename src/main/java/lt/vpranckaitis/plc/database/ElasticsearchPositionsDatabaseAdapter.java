package lt.vpranckaitis.plc.database;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.vpranckaitis.plc.Constants;
import lt.vpranckaitis.plc.geo.Place;

import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;

public class ElasticsearchPositionsDatabaseAdapter implements PositionsDatabaseAdapter {
	
	private Client mClient;
	
	public ElasticsearchPositionsDatabaseAdapter() {
		this(false);
	}
	
	public ElasticsearchPositionsDatabaseAdapter(boolean local) {
		Node node = nodeBuilder().client(true).local(local).node();
		mClient = node.client();
	}
	
	public ElasticsearchPositionsDatabaseAdapter(Client client) {
		mClient = client;
	}
	
	@Override
	public boolean checkKeyExists(String key) {
		return mClient.prepareGet("positions", "position", key).execute().actionGet().isExists();
	}

	@Override
	public boolean updatePosition(String key, double latitude, double longitude) {
		try {
			XContentBuilder source = XContentFactory.jsonBuilder().startObject()
					.startObject("location")
					.field("lat", latitude)
					.field("lon", longitude)
					.endObject()
					.endObject();
			mClient.prepareUpdate("positions", "position", key)
					.setDoc(source).get();
		} catch (DocumentMissingException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	@Override
	public boolean createPosition(String key) {
		XContentBuilder source = null;
		try {
			source = XContentFactory.jsonBuilder().startObject()
					.startObject("location")
					.field("lat", 0.0)
					.field("lon", 0.0)
					.endObject()
					.endObject();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IndexResponse response = mClient.prepareIndex("positions", "position", key).setSource(source).get();
		return response.isCreated();
	}

	@Override
	public boolean deletePosition(String key) {
		DeleteResponse response = mClient.prepareDelete("positions", "position", key)
				.execute().actionGet();
		return response.isFound();
	}

	@Override
	public List<Long> getProximity(List<Place> places) {
		List<Long> proximity = new ArrayList<>();
		for (Place place : places) {
			FilterBuilder distanceFilter = FilterBuilders
					.geoDistanceFilter("position.location")
					.point(place.latitude, place.longitude)
					.distance(Constants.RADIUS_FOR_PEOPLE, DistanceUnit.METERS)
					.geoDistance(GeoDistance.SLOPPY_ARC);
					
			FilterBuilder timestampFilter = FilterBuilders
					.rangeFilter("position._timestamp")
					.from("now-30m");
			FilterBuilder filter = FilterBuilders
					.andFilter(distanceFilter, timestampFilter);
			FilteredQueryBuilder query = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filter);
			CountRequestBuilder countRequest = mClient.prepareCount("positions").setQuery(query);
			CountResponse response = countRequest.get();
			proximity.add(response.getCount());
		}
		return proximity;
	}

}
