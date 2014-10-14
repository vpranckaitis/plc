package lt.vpranckaitis.plc.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public class GetPlacesRequest implements Request {

	private PositionsDatabaseAdapter mPositionDatabase;
	private PlacesDatabaseAdapter mPlacesDatabase;
	private double mLatitude = 0.0;
	private double mLongitude = 0.0;
	private List<String> mTypes = new ArrayList<>();
	

	public GetPlacesRequest(PositionsDatabaseAdapter position, PlacesDatabaseAdapter places, double lat, double lon, List<String> types) {
		mPositionDatabase = position;
		mPlacesDatabase = places;
		mLatitude = lat;
		mLongitude = lon;
		mTypes = types;
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\t /*Get places according to filters, count the value of proximity of users around places, return list of places ordered by proximity*/\n}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		GetPlacesRequest temp = (GetPlacesRequest) obj;
		boolean typesEquals = (mTypes == null && temp.mTypes == null) 
				|| (mTypes != null && temp.mTypes != null && new HashSet<String>(mTypes).equals(new HashSet<String>(temp.mTypes)));
		return (mLatitude == temp.mLatitude) && (mLongitude == temp.mLongitude) && typesEquals 
				&& mPositionDatabase.equals(temp.mPositionDatabase)
				&& mPlacesDatabase.equals(temp.mPositionDatabase);
	}

}
