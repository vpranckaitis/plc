package lt.vpranckaitis.plc.request;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;

public class NewPositionRequest extends PositionRequest {
	
	public NewPositionRequest(PositionDatabaseAdapter position) {
		super(position, "");
	}

	@Override
	public String getResponse() {
		String key = UUID.randomUUID().toString();
		getPositionDatabase().createPosition(key);
		String response = "";
		try {
			response = new JSONObject().put("status", "OK").put("key", key).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
}
