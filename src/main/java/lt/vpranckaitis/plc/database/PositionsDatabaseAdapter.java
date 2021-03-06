package lt.vpranckaitis.plc.database;

import java.util.List;

import lt.vpranckaitis.plc.geo.Place;

public interface PositionsDatabaseAdapter {
	public boolean checkKeyExists(String key);
	public boolean createPosition(String key);
	public boolean updatePosition(String key, double latitude, double longitude);
	public boolean deletePosition(String key);
	public List<Long> getProximity(List<Place> places);
}
