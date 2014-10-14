package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;

public abstract class PositionRequest implements Request {

	private PositionDatabaseAdapter mPositionDatabase;
	
	public PositionRequest(PositionDatabaseAdapter position) {
		mPositionDatabase = position;
	}
	
	@Override
	public abstract String getResponse();
	
	protected PositionDatabaseAdapter getPositionDatabase() {
		return mPositionDatabase;
	}
}
