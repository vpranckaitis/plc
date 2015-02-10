package lt.vpranckaitis.plc.request;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.ResponseData;

public class UpdatePositionRequest extends PositionRequest {

	private double mLatitude;
	private double mLongitude;
	
	public UpdatePositionRequest(PositionsDatabaseAdapter position, String key, double latitude, double longitude) {
		super(position, key);
		mLatitude = latitude;
		mLongitude = longitude;
	}

	@Override
	public ResponseData getResponse() {
		ResponseData responseData = new ResponseData();
		responseData.setResponseBody(new JSONObject().toString());
		if (getPositionDatabase().updatePosition(mKey,mLatitude, mLongitude)) {
			responseData.setStatus(200);
		} else {
			responseData.setStatus(404);
		}
		return responseData;
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
