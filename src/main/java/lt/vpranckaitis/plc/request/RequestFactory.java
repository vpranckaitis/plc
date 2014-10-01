package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.transport.HttpMethod;

public interface RequestFactory {
	public Request constructRequest(HttpMethod method, String uri, String query, String content);
}
