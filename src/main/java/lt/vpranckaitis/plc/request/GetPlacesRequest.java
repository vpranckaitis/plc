package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;

public class GetPlacesRequest implements Request {

	private PositionDatabaseAdapter mPositionDatabase;
	private PlacesDatabaseAdapter mPlacesDatabase;

	public GetPlacesRequest(PositionDatabaseAdapter position, PlacesDatabaseAdapter places) {
		mPositionDatabase = position;
		mPlacesDatabase = places;
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\t /*Get places according to filters, count the value of proximity of users around places, return list of places ordered by proximity*/\n}";
	}

}
