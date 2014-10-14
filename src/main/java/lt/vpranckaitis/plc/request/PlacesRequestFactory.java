package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class PlacesRequestFactory implements RequestFactory {

	private PositionDatabaseAdapter mPositionsDb;
	private PlacesDatabaseAdapter mPlacesDb;

	public PlacesRequestFactory(PositionDatabaseAdapter positions,
			PlacesDatabaseAdapter places) {
		mPositionsDb = positions;
		mPlacesDb = places;
	}

	@Override
	public Request constructRequest(HttpMethod method, String uri,
			String query, String content) {
		switch (method) {
		case GET:
			if (true) {
				return new GetPlacesRequest(mPositionsDb, mPlacesDb);
			}
			break;
		}
		return new InvalidRequest();
	}

}
