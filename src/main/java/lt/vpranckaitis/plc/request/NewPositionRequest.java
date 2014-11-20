package lt.vpranckaitis.plc.request;

import java.util.HashMap;
import java.util.Map;
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
		Map<String, String> response = new HashMap<String, String>();
		response.put("status", "201");
		response.put("key", key);
		return new JSONObject(response).toString();
	}
}
