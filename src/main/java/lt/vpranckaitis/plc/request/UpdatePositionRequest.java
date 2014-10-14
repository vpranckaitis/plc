package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;

public class UpdatePositionRequest extends PositionRequest {

	public UpdatePositionRequest(PositionDatabaseAdapter position, String key) {
		super(position, key);
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\t /*Check if key exists, update position*/\n}";
	}

}
