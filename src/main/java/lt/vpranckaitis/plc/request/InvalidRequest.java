package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;

public class InvalidRequest implements Request {

	@Override
	public String getResponse() {
		return "{status: \"INVALID_REQUEST\"}";
	}

}