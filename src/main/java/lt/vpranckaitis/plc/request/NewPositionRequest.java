package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;

public class NewPositionRequest extends PositionRequest {

	public NewPositionRequest(DatabaseAdapter position) {
		super(position);
	}

	@Override
	public String getResponse() {
		return "{\n\tstatus:\"OK\",\n\tkey: \"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa\"\n\t/*Generate new key, insert into database, return key*/\n}";
	}

}
