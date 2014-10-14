package lt.vpranckaitis.plc.transport;

public interface RequestListener {
	public String handleRequest(HttpMethod method, String uri, String query, String content);
}
