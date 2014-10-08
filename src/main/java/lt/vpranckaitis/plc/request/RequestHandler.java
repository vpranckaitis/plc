package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;
import lt.vpranckaitis.plc.transport.RequestListener;

public class RequestHandler implements RequestListener {
	
	private RequestFactory mRequestFactory;
	
	public RequestHandler() {
		mRequestFactory = new AbstractRequestFactory(new DatabaseAdapter() {}, new DatabaseAdapter() {});
	}

	@Override
	public String handleRequest(HttpMethod method, String uri, String query, String content) {
		return mRequestFactory.constructRequest(method, uri, query, content).getResponse();
	}

}
