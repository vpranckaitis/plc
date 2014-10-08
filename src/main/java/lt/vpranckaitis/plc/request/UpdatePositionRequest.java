package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;

public class UpdatePositionRequest extends PositionRequest {

	public UpdatePositionRequest(DatabaseAdapter position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\t /*Check if key exists, update position*/\n}";
	}

}
