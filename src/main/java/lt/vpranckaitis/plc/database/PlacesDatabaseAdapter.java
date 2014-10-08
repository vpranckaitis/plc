package lt.vpranckaitis.plc.database;

import java.util.List;

import lt.vpranckaitis.plc.geo.Place;

public interface PlacesDatabaseAdapter {
	public List<Place> getPlaces(double latitude, double longtitude, int radius, String[] types);
}
