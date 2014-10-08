package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;

public class DeletePositionRequest extends PositionRequest {

	public DeletePositionRequest(DatabaseAdapter position) {
		super(position);
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\t /*Check if key exists, delete key from database*/\n}";
	}

}
