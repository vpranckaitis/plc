package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;

public abstract class PositionRequest implements Request {

	private DatabaseAdapter mPositionDatabase;
	
	public PositionRequest(DatabaseAdapter position) {
		mPositionDatabase = position;
	}
	
	@Override
	public abstract String getResponse();
}
