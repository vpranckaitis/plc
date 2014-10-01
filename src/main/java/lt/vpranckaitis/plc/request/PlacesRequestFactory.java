package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class PlacesRequestFactory implements RequestFactory {

	private DatabaseAdapter mPositionsDb;
	private DatabaseAdapter mPlacesDb;

	public PlacesRequestFactory(DatabaseAdapter positions,
			DatabaseAdapter places) {
		mPositionsDb = positions;
		mPlacesDb = places;
	}

	@Override
	public Request constructRequest(HttpMethod method, String uri,
			String query, String content) {
		
		return null;
	}

}
