package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;

public class GetPlacesRequest implements Request {

	private DatabaseAdapter mPositionDatabase;
	private DatabaseAdapter mPlacesDatabase;

	public GetPlacesRequest(DatabaseAdapter position, DatabaseAdapter places) {
		mPositionDatabase = position;
		mPlacesDatabase = places;
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\t /*Get places according to filters, count the value of proximity of users around places, return list of places ordered by proximity*/\n}";
	}

}
