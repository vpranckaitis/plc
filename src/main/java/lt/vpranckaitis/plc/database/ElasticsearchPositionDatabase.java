package lt.vpranckaitis.plc.database;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.util.List;

import lt.vpranckaitis.plc.geo.Place;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.json.JSONObject;

public class ElasticsearchPositionDatabase implements PositionDatabaseAdapter {
	
	private Node mNode;
	private Client mClient;
	
	public ElasticsearchPositionDatabase() {
		this(false);
	}
	
	public ElasticsearchPositionDatabase(boolean local) {
		mNode = nodeBuilder().client(true).local(local).node();
		mClient = mNode.client();
	}
	
	@Override
	public boolean checkKeyExists(String key) {
		return mClient.prepareGet("positions", "position", key).execute().actionGet().isExists();
	}

	@Override
	public void updatePosition(String key, double latitude, double longitude) {
		try {
			XContentBuilder source = XContentFactory.jsonBuilder().startObject()
					.startObject("location")
					.field("lat", latitude)
					.field("lon", longitude)
					.endObject()
					.endObject();
			UpdateResponse response = mClient.prepareUpdate("positions", "position", key)
					.setSource(source).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean createPosition(String key) {
		try {
			
			XContentBuilder source = XContentFactory.jsonBuilder().startObject()
					.startObject("location")
					.field("lat", 0.0)
					.field("lon", 0.0)
					.endObject()
					.endObject();
			System.out.println(key);
			System.out.println(source.toString());
			IndexResponse response = mClient.prepareIndex("positions", "position", key).setSource(source).execute().actionGet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void deletePosition(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePlacesWithProximity(List<Place> places) {
		// TODO Auto-generated method stub

	}

}
