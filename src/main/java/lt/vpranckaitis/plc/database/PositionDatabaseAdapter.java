package lt.vpranckaitis.plc.database;

import java.util.List;

import lt.vpranckaitis.plc.geo.Place;

public interface PositionDatabaseAdapter {
	public boolean checkKeyExists(String key);
	public void updatePosition(String key, double latitude, double longitude);
	public boolean createPosition(String key);
	public void deletePosition(String key);
	public void updatePlacesWithProximity(List<Place> places);
}
