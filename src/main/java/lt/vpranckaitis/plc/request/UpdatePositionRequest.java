package lt.vpranckaitis.plc.request;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public class UpdatePositionRequest extends PositionRequest {

	private double mLatitude;
	private double mLongitude;
	
	public UpdatePositionRequest(PositionsDatabaseAdapter position, String key, double latitude, double longitude) {
		super(position, key);
		mLatitude = latitude;
		mLongitude = longitude;
	}

	@Override
	public String getResponse() {
		Map<String, String> response = new HashMap<String, String>();
		if (getPositionDatabase().updatePosition(mKey,mLatitude, mLongitude)) {
			response.put("status", "200");
		} else {
			response.put("status", "404");
		}
		return new JSONObject(response).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		UpdatePositionRequest temp = (UpdatePositionRequest) obj;
		double EPS = 1e-9;
		return super.equals(temp) && (Math.abs(mLatitude - temp.mLatitude) < EPS) && (Math.abs(mLongitude - temp.mLongitude) < EPS);
	}
}
