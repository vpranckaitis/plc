package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;

public class DeletePositionRequest extends PositionRequest {

	public DeletePositionRequest(PositionDatabaseAdapter position) {
		super(position);
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\t /*Check if key exists, delete key from database*/\n}";
	}

}
