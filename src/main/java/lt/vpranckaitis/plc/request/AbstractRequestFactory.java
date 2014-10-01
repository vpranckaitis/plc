package lt.vpranckaitis.plc.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lt.vpranckaitis.plc.database.DatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class AbstractRequestFactory implements RequestFactory {

	private PositionsRequestFactory mPositionsFactory;
	private PlacesRequestFactory mPlacesFactory;
	
	public AbstractRequestFactory(DatabaseAdapter positions, DatabaseAdapter places) {
		mPositionsFactory = new PositionsRequestFactory(positions);
		mPlacesFactory = new PlacesRequestFactory(positions, places);
	}
	
	@Override
	public Request constructRequest(HttpMethod method, String uri, String query,
			String content) {
		Matcher m = Pattern.compile("^/([a-z]+)/?", Pattern.CASE_INSENSITIVE).matcher(uri);
		System.out.println(uri);
		System.out.println(m.toString());
		if (m.find()) {
			switch (m.group(1).toLowerCase()) {
			case "positions":
				return mPositionsFactory.constructRequest(method, uri, query, content);
			case "places":
				return mPlacesFactory.constructRequest(method, uri, query, content);
			}
		} 
		return new InvalidRequest();
	}


}
