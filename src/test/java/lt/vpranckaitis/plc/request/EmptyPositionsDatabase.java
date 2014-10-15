package lt.vpranckaitis.plc.request;

import java.util.List;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.geo.Place;

public class EmptyPositionsDatabase implements PositionsDatabaseAdapter {

	public boolean checkKeyExists(String key) {
		return false;
	}

	public boolean updatePosition(String key, double latitude, double longitude) {
		return true;
	}

	public boolean createPosition(String key) {
		return false;
	}

	public boolean deletePosition(String key) {
		return true;
	}

	public void updatePlacesWithProximity(List<Place> places) {
		
	}

}
