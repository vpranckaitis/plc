package lt.vpranckaitis.plc.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lt.vpranckaitis.plc.Constants;
import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class PlacesRequestFactory implements RequestFactory {
	
	private static final String URI_PLACES_PATTERN = "^/places/("
			+ Constants.ACCESS_KEY_PATTERN + ")/?";
	private static final String QUERY_PLACES_PATTERN = "(?:([\\w]=[\\w\\.\\|]*)&?){2, 3}$";

	private PositionsDatabaseAdapter mPositionsDb;
	private PlacesDatabaseAdapter mPlacesDb;

	public PlacesRequestFactory(PositionsDatabaseAdapter positions,
			PlacesDatabaseAdapter places) {
		mPositionsDb = positions;
		mPlacesDb = places;
	}

	@Override
	public Request constructRequest(HttpMethod method, String uri,
			String query, String content) {
		switch (method) {
		case GET:
			Matcher m = Pattern.compile(URI_PLACES_PATTERN,
					Pattern.CASE_INSENSITIVE).matcher(uri);
			if (m.matches()) {
				double lat = Double.NaN;
				double lon = Double.NaN;
				List<String> types = null;
				String key = m.group(1);
				
				for (String param : query.split("&")) {
					String[] keyValPair = param.split("=");
					if (keyValPair[0].toLowerCase().equals("types")) {
						if (keyValPair[1] != null && keyValPair[1].length() != 0) {
							types = new ArrayList<String>();
							for (String type : keyValPair[1].split("\\|")) {
								types.add(type);
							}
						}
					} else if (keyValPair.length == 2) {
						try {
							switch (keyValPair[0].toLowerCase()) {
							case "lat":
								lat = Double.parseDouble(keyValPair[1]);
								break;
							case "lon":
								lon = Double.parseDouble(keyValPair[1]);
								break;
							}
						} catch (NumberFormatException e) {}
					}
				}
				
				if (Double.isNaN(lat) || Double.isNaN(lon)) {
					return new InvalidRequest();
				} else {
					return new GetPlacesRequest(mPositionsDb, mPlacesDb, key, lat, lon, types);
				}
			}
		default:
			return new InvalidRequest();
		}
		
	}

}
