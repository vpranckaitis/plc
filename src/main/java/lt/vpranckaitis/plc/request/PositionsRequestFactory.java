package lt.vpranckaitis.plc.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import lt.vpranckaitis.plc.Constants;
import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class PositionsRequestFactory implements RequestFactory {

	private static final String URI_GET_ACCESS_KEY_PATTERN = "^/positions/?$";
	private static final String URI_UPDATE_POSITION_PATTERN = "^/positions/("
			+ Constants.ACCESS_KEY_PATTERN + ")/?$";
	private static final String URI_DELETE_POSITION_PATTERN = URI_UPDATE_POSITION_PATTERN;

	private PositionsDatabaseAdapter mPositionsDb;

	public PositionsRequestFactory(PositionsDatabaseAdapter positions) {
		mPositionsDb = positions;
	}

	@Override
	public Request constructRequest(HttpMethod method, String uri,
			String query, String content) {
		switch (method) {
		case POST: {
			Matcher m = Pattern.compile(URI_GET_ACCESS_KEY_PATTERN,
					Pattern.CASE_INSENSITIVE).matcher(uri);
			if (m.matches()) {
				return new NewPositionRequest(mPositionsDb);
			}
			break;
		}
		case PUT: {
			Matcher m = Pattern.compile(URI_UPDATE_POSITION_PATTERN,
					Pattern.CASE_INSENSITIVE).matcher(uri);
			if (m.matches()) {
				try {
					JSONObject locationJSON = new JSONObject(content).getJSONObject("location");
					double latitude = locationJSON.getDouble("lat");
					double longitude = locationJSON.getDouble("lon");
					return new UpdatePositionRequest(mPositionsDb, m.group(1), latitude, longitude);
				} catch (JSONException e) {
					return new InvalidRequest();
				}
			}
			break;
		}
		case DELETE: {
			Matcher m = Pattern.compile(URI_DELETE_POSITION_PATTERN,
					Pattern.CASE_INSENSITIVE).matcher(uri);
			if (m.matches()) {
				return new DeletePositionRequest(mPositionsDb, m.group(1));
			}
			break;
		}
		}
		return new InvalidRequest();
	}

}
