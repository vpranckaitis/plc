package lt.vpranckaitis.plc.request;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


public class InvalidRequest implements Request {

	@Override
	public String getResponse() {
		Map<String, String> response = new HashMap<String, String>();
		response.put("status", "400");
		return new JSONObject(response).toString();
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
