package lt.vpranckaitis.plc.request;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.transport.ResponseData;

public class DeletePositionRequest extends PositionRequest {

	public DeletePositionRequest(PositionsDatabaseAdapter position, String key) {
		super(position, key);
	}

	@Override
	public ResponseData getResponse() {
		ResponseData responseData = new ResponseData(); 
		responseData.setResponseBody(new JSONObject().toString());
		if (getPositionDatabase().deletePosition(mKey)) {
			responseData.setStatus(200);
		} else {
			responseData.setStatus(401);
		}
		return responseData;
	}

}
