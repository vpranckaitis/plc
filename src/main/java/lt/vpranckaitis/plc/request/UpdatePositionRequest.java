package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public class UpdatePositionRequest extends PositionRequest {

	public UpdatePositionRequest(PositionsDatabaseAdapter position, String key) {
		super(position, key);
	}

	@Override
	public String getResponse() {
		return "{\n\t\"status\":\"OK\",\n\t \"comment\":\"Check if key exists, location info for given key\"\n}";
	}

}
