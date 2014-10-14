package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class PlacesRequestFactory implements RequestFactory {

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
			if (true) {
				return new GetPlacesRequest(mPositionsDb, mPlacesDb, 0.0, 0.0, null);
			}
		}
		return new InvalidRequest();
	}

}
