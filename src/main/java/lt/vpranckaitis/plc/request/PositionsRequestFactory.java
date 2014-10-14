package lt.vpranckaitis.plc.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class PositionsRequestFactory implements RequestFactory {

	private static final String ACCESS_KEY_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
	private static final String URI_GET_ACCESS_KEY_PATTERN = "^/positions/?$";
	private static final String URI_UPDATE_POSITION_PATTERN = "^/positions/("
			+ ACCESS_KEY_PATTERN + ")/?$";
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
				return new UpdatePositionRequest(mPositionsDb, m.group(1));
			}
			break;
		}
		case DELETE: {
			Matcher m = Pattern.compile(URI_UPDATE_POSITION_PATTERN,
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
