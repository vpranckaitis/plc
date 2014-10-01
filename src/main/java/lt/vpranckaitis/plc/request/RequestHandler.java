package lt.vpranckaitis.plc.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lt.vpranckaitis.plc.transport.HttpMethod;
import lt.vpranckaitis.plc.transport.RequestListener;

public class RequestHandler implements RequestListener {
	
	private static final String AUTHENTICATION_URI_PATTERN = "^/position/?$";
	private static final String UPDATE_POSITION_URI_PATTERN = "^/position/([0-9a-f]{16})$";

	@Override
	public String handleRequest(HttpMethod method, String uri, String query, String content) {
		uri = uri.toLowerCase();
		System.out.println(uri);
		switch (method) {
		case POST: {
			if (uri.matches(AUTHENTICATION_URI_PATTERN)) {
				System.out.println("authentication " + uri);
			}
		}
		case GET: {
			
		}
		case PUT: {
			Matcher m = Pattern.compile(UPDATE_POSITION_URI_PATTERN).matcher(uri);
			if (m.matches()) {
				
			}
		}
		case DELETE: {
			
		}
		}
		return "";
	}

}
