package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public abstract class PositionRequest implements Request {

	private PositionsDatabaseAdapter mPositionDatabase;
	protected String mKey = "";
	
	public PositionRequest(PositionsDatabaseAdapter position, String key) {
		mPositionDatabase = position;
		mKey = key;
	}
	
	@Override
	public abstract String getResponse();
	
	protected PositionsDatabaseAdapter getPositionDatabase() {
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
