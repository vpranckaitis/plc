package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PositionDatabaseAdapter;

public abstract class PositionRequest implements Request {

	private PositionDatabaseAdapter mPositionDatabase;
	protected String mKey = "";
	
	public PositionRequest(PositionDatabaseAdapter position, String key) {
		mPositionDatabase = position;
		mKey = key;
	}
	
	@Override
	public abstract String getResponse();
	
	protected PositionDatabaseAdapter getPositionDatabase() {
		return mPositionDatabase;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		PositionRequest temp = (PositionRequest) obj;
		return mKey.equals(temp.mKey) 
				&& mPositionDatabase.getClass().equals(temp.mPositionDatabase.getClass());
	}
	
}
