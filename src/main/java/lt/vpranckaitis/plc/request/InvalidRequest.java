package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;

public class InvalidRequest implements Request {

	@Override
	public String getResponse() {
		return "{\n\tstatus: \"INVALID_REQUEST\"\n}";
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
