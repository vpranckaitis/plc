package lt.vpranckaitis.plc.request;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public class NewPositionRequest extends PositionRequest {
	
	public NewPositionRequest(PositionsDatabaseAdapter position) {
		super(position, "");
	}

	@Override
	public String getResponse() {
		String key = UUID.randomUUID().toString();
		getPositionDatabase().createPosition(key);
		String response = "";
		try {
			response = new JSONObject().put("status", "OK").put("key", key).put("comment", "You've got the key").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
}
