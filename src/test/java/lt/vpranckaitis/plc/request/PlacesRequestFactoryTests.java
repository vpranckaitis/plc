package lt.vpranckaitis.plc.request;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlacesRequestFactoryTests {

	private PlacesRequestFactory mFactory;
	private static PositionsDatabaseAdapter mPositionsDb;
	private static PlacesDatabaseAdapter mPlacesDb;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mPositionsDb = new EmptyPositionsDatabase();
		mPlacesDb = new EmptyPlacesDatabase();
	}
	
	@Before
	public void setUp() throws Exception {
		mFactory = new PlacesRequestFactory(mPositionsDb, mPlacesDb);
	}

	@Test
	public void getPlacesRequest_shouldReturnGetPlacesRequest() throws Exception {
		HttpMethod method = HttpMethod.GET;
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/places/" + key;
		String query = "lat=0.1&lon=0.2&types=establishment|school";
		String content = "";
		List<String> types = new ArrayList<String>() {{add("school"); add("establishment");}};
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new GetPlacesRequest(mPositionsDb, mPlacesDb, 0.1, 0.2, types);
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPlacesRequestPostMethod_shouldReturnInvalidRequest() throws Exception {
		HttpMethod method = HttpMethod.GET;
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/places/" + key;
		String query = "lat=0.1&lon=0.2&types=establishment|school";
		String content = "";
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPlacesRequestPutMethod_shouldReturnInvalidRequest() throws Exception {
		HttpMethod method = HttpMethod.PUT;
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/places/" + key;
		String query = "lat=0.1&lon=0.2&types=establishment|school";
		String content = "";
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPlacesRequestDeleteMethod_shouldReturnInvalidRequest() throws Exception {
		HttpMethod method = HttpMethod.DELETE;
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/places/" + key;
		String query = "lat=0.1&lon=0.2&types=establishment|school";
		String content = "";
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPlacesRequestWrongKeyFormat_shouldReturnInvalidRequest() throws Exception {
		HttpMethod method = HttpMethod.GET;
		String key = "baaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/places/" + key;
		String query = "lat=0.1&lon=0.2&types=establishment|school";
		String content = "";
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPlacesRequestMissingLat_shouldReturnInvalidRequest() throws Exception {
		HttpMethod method = HttpMethod.GET;
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/places/" + key;
		String query = "lon=0.2&types=establishment|school";
		String content = "";
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPlacesRequestMissingLon_shouldReturnInvalidRequest() throws Exception {
		HttpMethod method = HttpMethod.GET;
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/places/" + key;
		String query = "lat=0.1&types=establishment|school";
		String content = "";
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPlacesRequestMissingKey_shouldReturnInvalidRequest() throws Exception {
		HttpMethod method = HttpMethod.GET;
		String key = "";
		String uri = "/places/" + key;
		String query = "lat=0.1&lon=0.2&types=establishment|school";
		String content = "";
		Request actual = mFactory.constructRequest(method, uri, query, content);
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}


}
