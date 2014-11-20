package lt.vpranckaitis.plc.request;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public class DeletePositionRequest extends PositionRequest {

	public DeletePositionRequest(PositionsDatabaseAdapter position, String key) {
		super(position, key);
	}

	@Override
	public String getResponse() {
		Map<String, String> response = new HashMap<String, String>();
		if (getPositionDatabase().deletePosition(mKey)) {
			response.put("status", "200");
		} else {
			response.put("status", "404");
		}
		return new JSONObject(response).toString();
	}

}
