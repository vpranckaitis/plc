package lt.vpranckaitis.plc.request;

import java.util.ArrayList;
import java.util.Collections;
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

	@Override
	public List<Long> getProximity(List<Place> places) {
		return Collections.nCopies(places.size(), new Long(0));
	}

}
