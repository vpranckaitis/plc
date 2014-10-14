package lt.vpranckaitis.plc.request;

import static org.junit.Assert.assertEquals;
import lt.vpranckaitis.plc.transport.HttpMethod;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PositionRequestFactoryTests {

	private PositionsRequestFactory mFactory;
	
	@Before
	public void setUp() throws Exception {
		mFactory = new PositionsRequestFactory(new EmptyPositionDatabase());
	}
	
	@Test
	public void newPositionRequest_shouldReturnNewPositionRequestObject() {
		Request actual = mFactory.constructRequest(HttpMethod.POST, "/positions/", "", "");
		Request expected = new NewPositionRequest(new EmptyPositionDatabase());
		assertEquals(expected, actual);
	}
	
	@Test
	public void newPositionRequestPutMethod_shouldReturnInvalidRequestObject() {
		Request actual = mFactory.constructRequest(HttpMethod.PUT, "/positions/", "", "");
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void newPositionRequestDeleteMethod_shouldReturnInvalidRequestObject() {
		Request actual = mFactory.constructRequest(HttpMethod.DELETE, "/positions/", "", "");
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void newPositionRequestGetMethod_shouldReturnInvalidRequestObject() {
		Request actual = mFactory.constructRequest(HttpMethod.GET, "/positions/", "", "");
		Request expected = new InvalidRequest();
		assertEquals(expected, actual);
	}
	
	@Test
	public void updatePositionRequest_shouldReturnUpdatePositionRequestObject() {
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/positions/" + key;
		String query = "";
		String content = "{\"location\" : {\"lat\" : 0.0, \"lon\" : 0.0}}";
		Request actual = mFactory.constructRequest(HttpMethod.PUT, uri, query, content);
		Request expected = new UpdatePositionRequest(new EmptyPositionDatabase(), key);
		assertEquals(expected, actual);
	}
	
	@Test
	public void deletePositionRequest_shouldReturnDeletePositionRequestObject() {
		String key = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
		String uri = "/positions/" + key;
		String query = "";
		String content = "";
		Request actual = mFactory.constructRequest(HttpMethod.DELETE, uri, query, content);
		Request expected = new DeletePositionRequest(new EmptyPositionDatabase(), key);
		assertEquals(expected, actual);
	}
}
