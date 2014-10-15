package lt.vpranckaitis.plc.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lt.vpranckaitis.plc.geo.Place;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.NodeBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElasticsearchPositionsDatabaseTests {

	private static ElasticsearchPositionsDatabase sDb;
	private static Client sClient;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sClient = NodeBuilder.nodeBuilder().local(false).node().client();
		
	}

	@Before
	public void setUp() throws Exception {
		sClient.prepareDeleteByQuery("positions").setQuery(QueryBuilders.matchAllQuery()).setTypes("position").execute();
	}
	
	@Test
	public void count() throws Exception {
		List<Place> places = new ArrayList<Place>() {
			{
				add(new Place("1", 0.0, 0.0, "Must count 3", "Must count 3 city"));
				add(new Place("2", -0.002, 0.002, "Must count 3, too", "Must count 3, too city"));
				add(new Place("2", 0.0, 0.006, "Must count 0", "Must count 0 city"));
			}
		};
		//IndexResponse response = sClient.prepareIndex("positions", "position", "aaaaaaaa-bbab-bbbb-bbbb-bbbbbbbbbbbc").setSource("{\"location\" : {\"lat\" : 0.0, \"lon\" : 0.0}}").get();
		//ElasticsearchPositionsDatabase db = new ElasticsearchPositionsDatabase();
		//db.createPosition("aaaaaaaa-bbab-bbbb-bbbb-bbbbbbbbbbbv");
		//System.out.println(client.prepareCount("positions").get().getCount());
		String[] keys = new String[] {
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaad", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaae"};
		double[] lats = {0.0, 0.0, 0.002, -0.002, 0.0};
		double[] lons = {0.002, -0.002, 0.0, 0.002, 0.003};
		addPositions(keys, lats, lons);
		Thread.sleep(2000);
		ElasticsearchPositionsDatabase db = new ElasticsearchPositionsDatabase(sClient);
		db.updatePlacesWithProximity(places);
	}
	
	private void addPositions(String[] keys, double[] lats, double[] lons) {
		BulkRequestBuilder bulk = sClient.prepareBulk();
		for (int i = 0; i < keys.length; i++) {
			IndexRequestBuilder index = sClient.prepareIndex("positions", "position", keys[i])
					.setSource(String.format(Locale.ENGLISH, "{\"location\" : {\"lat\" : %1$f, \"lon\" : %2$f}}", lats[i], lons[i]));
			bulk.add(index);
		}
		System.out.println(bulk.get().buildFailureMessage());
	}

}
