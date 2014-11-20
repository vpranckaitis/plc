package lt.vpranckaitis.plc.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lt.vpranckaitis.plc.geo.Place;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.geo.GeoDistance;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.FilterBuilders.*;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.NodeBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElasticsearchPositionsDatabaseAdapterTests {

	private static ElasticsearchPositionsDatabaseAdapter sDb;
	private static Client sClient;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sClient = NodeBuilder.nodeBuilder().clusterName("test-database").local(false).node().client();
		
	}

	@Before
	public void setUp() throws Exception {
		sClient.prepareDeleteByQuery("positions").setQuery(QueryBuilders.matchAllQuery()).setTypes("position").execute();
	}
	
	@Test
	public void updatePlacesWithProximity_mustCountIn250mRadius() throws Exception {
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
		long[] timeDiffs = {0, 0, 0, 0, 0};
		addPositions(keys, lats, lons, timeDiffs);
		Thread.sleep(2000);
		ElasticsearchPositionsDatabaseAdapter db = new ElasticsearchPositionsDatabaseAdapter(sClient);
		List<Long> actual = db.getProximity(places);
		List<Long> expected = Arrays.asList(new Long[]{3l, 3l, 0l});
		assertEquals(expected, actual);
	}
	
	@Test
	public void updatePlacesWithProximity_mustCountNoOlderThan30min() throws Exception {
		List<Place> places = new ArrayList<Place>() {
			{
				add(new Place("1", 0.0, 0.0, "Must count 3", "Must count 3 city"));
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
		double[] lats = {0.0, 0.0, 0.0, 0.0, 0.0};
		double[] lons = {0.0, 0.0, 0.0, 0.0, 0.0};
		long[] timeDiffs = {-35*60*1000, -30*60*1000, -28*60*1000, -15*60*1000, 0};
		addPositions(keys, lats, lons, timeDiffs);
		Thread.sleep(2000);
		ElasticsearchPositionsDatabaseAdapter db = new ElasticsearchPositionsDatabaseAdapter(sClient);
		List<Long> actual = db.getProximity(places);
		List<Long> expected = Arrays.asList(new Long[]{3l});
		assertEquals(expected, actual);
	}
	
	private void addPositions(String[] keys, double[] lats, double[] lons, long[] timeDiffs) {
		long timestamp = new Date().getTime();
		BulkRequestBuilder bulk = sClient.prepareBulk();
		for (int i = 0; i < keys.length; i++) {
			IndexRequestBuilder index = sClient.prepareIndex("positions", "position", keys[i])
					.setSource(String.format(Locale.ENGLISH, "{\"location\" : {\"lat\" : %1$f, \"lon\" : %2$f}}", lats[i], lons[i]))
					.setTimestamp(Long.toString(timestamp + timeDiffs[i]));
			bulk.add(index);
		}
		System.out.println(bulk.get().buildFailureMessage());
	}

	@Test
	public void checkKeyExists_shouldFindFirstTwoKeys() throws Exception {
		String[] keys = new String[] {
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"};
		double[] lats = {0.0, 0.0};
		double[] lons = {0.0, 0.0};
		long[] timeDiffs = {0, 0};
		addPositions(keys, lats, lons, timeDiffs);
		Thread.sleep(2000);
		
		String[] checkedKeys = new String[] {
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab",
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac",
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaad"};
		Boolean[] expecteds = new Boolean[] {true, true, false, false};
		Boolean[] actuals = new Boolean[checkedKeys.length];
		
		ElasticsearchPositionsDatabaseAdapter db = new ElasticsearchPositionsDatabaseAdapter(sClient);
		for (int i = 0; i < checkedKeys.length; i++) {
			actuals[i] = db.checkKeyExists(checkedKeys[i]);
		}
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void updatePosition_goodKey_shouldBeUpdated() throws Exception {
		String[] keys = new String[] {
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"};
		double[] lats = {0.0, 0.0};
		double[] lons = {0.0, 0.0};
		long[] timeDiffs = {0, 0};
		addPositions(keys, lats, lons, timeDiffs);
		Thread.sleep(2000);
		
		ElasticsearchPositionsDatabaseAdapter db = new ElasticsearchPositionsDatabaseAdapter(sClient);
		String updateKey = keys[0];
		boolean isUpdatedActual = db.updatePosition(updateKey, 1.0, 1.0);
		boolean isUpdatedExpected = true;
		Thread.sleep(2000);
		long actual = sClient.prepareCount("positions").setQuery(filteredQuery(boolQuery().must(idsQuery("position").ids(updateKey)), geoDistanceFilter("location").distance("1m").point(1.0, 1.0).geoDistance(GeoDistance.PLANE))).get().getCount();
		long expected = 1;
		assertEquals(expected, actual);
		assertEquals(isUpdatedExpected, isUpdatedActual);
	}
	
	@Test
	public void updatePosition_badKey_shouldNotBeUpdated() throws Exception {
		String[] keys = new String[] {
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"};
		double[] lats = {0.0, 0.0};
		double[] lons = {0.0, 0.0};
		long[] timeDiffs = {0, 0};
		addPositions(keys, lats, lons, timeDiffs);
		Thread.sleep(2000);
		
		ElasticsearchPositionsDatabaseAdapter db = new ElasticsearchPositionsDatabaseAdapter(sClient);
		String updateKey = "badkey00-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		boolean isUpdatedActual = db.updatePosition(updateKey, 1.0, 1.0);
		boolean isUpdatedExpected = false;
		Thread.sleep(2000);
		long actual = sClient.prepareCount("positions").setQuery(filteredQuery(boolQuery().must(idsQuery("position").ids(updateKey)), geoDistanceFilter("location").distance("1m").point(1.0, 1.0).geoDistance(GeoDistance.PLANE))).get().getCount();
		long expected = 0;
		assertEquals(expected, actual);
		assertEquals(isUpdatedExpected, isUpdatedActual);
		//assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	public void updatePosition_goodKey_shouldBeDeleted() throws Exception {
		String[] keys = new String[] {
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"};
		double[] lats = {0.0, 0.0};
		double[] lons = {0.0, 0.0};
		long[] timeDiffs = {0, 0};
		addPositions(keys, lats, lons, timeDiffs);
		Thread.sleep(2000);
		
		ElasticsearchPositionsDatabaseAdapter db = new ElasticsearchPositionsDatabaseAdapter(sClient);
		String deleteKey = keys[0];
		boolean isDeletedActual = db.deletePosition(deleteKey);
		boolean isDeletedExpected = true;
		Thread.sleep(2000);
		long actual = sClient.prepareCount("positions").setQuery(idsQuery("position").ids(deleteKey)).get().getCount();
		long expected = 0;
		assertEquals(expected, actual);
		assertEquals(isDeletedExpected, isDeletedActual);
	}

	
	@Test
	public void updatePosition_badKey_shouldBeDeleted() throws Exception {
		String[] keys = new String[] {
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 
				"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"};
		double[] lats = {0.0, 0.0};
		double[] lons = {0.0, 0.0};
		long[] timeDiffs = {0, 0};
		addPositions(keys, lats, lons, timeDiffs);
		Thread.sleep(2000);
		
		ElasticsearchPositionsDatabaseAdapter db = new ElasticsearchPositionsDatabaseAdapter(sClient);
		String deleteKey = "badkey00-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		boolean isDeletedActual = db.deletePosition(deleteKey);
		boolean isDeletedExpected = false;
		Thread.sleep(2000);
		long actual = sClient.prepareCount("positions").setQuery(idsQuery("position").ids(deleteKey)).get().getCount();
		long expected = 0;
		assertEquals(expected, actual);
		assertEquals(isDeletedExpected, isDeletedActual);
	}
}
