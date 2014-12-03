package lt.vpranckaitis.plc.transport;

public interface RequestListener {
	public ResponseData handleRequest(HttpMethod method, String uri, String query, String content);
}
