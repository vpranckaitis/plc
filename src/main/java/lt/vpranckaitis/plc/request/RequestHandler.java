package lt.vpranckaitis.plc.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lt.vpranckaitis.plc.Constants;
import lt.vpranckaitis.plc.database.ElasticsearchPositionsDatabaseAdapter;
import lt.vpranckaitis.plc.database.GooglePlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;
import lt.vpranckaitis.plc.transport.RequestListener;
import lt.vpranckaitis.plc.transport.ResponseData;

public class RequestHandler implements RequestListener {
	
	private RequestFactory mPositionsFactory;
	private RequestFactory mPlacesFactory;
	
	public RequestHandler() {
		mPositionsFactory = new PositionsRequestFactory(new ElasticsearchPositionsDatabaseAdapter());
		mPlacesFactory = new PlacesRequestFactory(
				new ElasticsearchPositionsDatabaseAdapter(),
				new GooglePlacesDatabaseAdapter(Constants.GOOGLE_PLACES_API_KEY)
				);
	}

	@Override
	public ResponseData handleRequest(HttpMethod method, String uri, String query, String content) {
		RequestFactory factory = chooseRequestFactory(uri);
		if (factory != null) {
			return factory.constructRequest(method, uri, query, content).getResponse();
		}
		return new InvalidRequest().getResponse();
	}
	
	private RequestFactory chooseRequestFactory(String uri) {
		Matcher m = Pattern.compile("^/([a-z]+)/?", Pattern.CASE_INSENSITIVE).matcher(uri);
		if (m.find()) {
			switch (m.group(1).toLowerCase()) {
			case "positions":
				return mPositionsFactory;
			case "places":
				return mPlacesFactory;
			}
		}
		return null;
	}

}
