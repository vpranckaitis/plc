package lt.vpranckaitis.plc.request;

import java.util.List;

import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;
import lt.vpranckaitis.plc.geo.Place;

public class EmptyPositionDatabase implements PositionDatabaseAdapter {

	public boolean checkKeyExists(String key) {
		return false;
	}

	public void updatePosition(String key, double latitude, double longitude) {
		
	}

	public boolean createPosition(String key) {
		return false;
	}

	public void deletePosition(String key) {
		
	}

	public void updatePlacesWithProximity(List<Place> places) {
		
	}

}
