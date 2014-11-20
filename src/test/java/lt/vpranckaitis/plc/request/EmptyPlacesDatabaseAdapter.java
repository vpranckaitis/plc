package lt.vpranckaitis.plc.request;

import java.util.List;

import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.geo.Place;

public class EmptyPlacesDatabaseAdapter implements PlacesDatabaseAdapter {

	@Override
	public List<Place> getPlaces(double latitude, double longtitude,
			int radius, String[] types) {
		return null;
	}

}
