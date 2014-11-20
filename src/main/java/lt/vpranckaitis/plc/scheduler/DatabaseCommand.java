package lt.vpranckaitis.plc.scheduler;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public abstract class DatabaseCommand implements Command {

	protected PositionsDatabaseAdapter mDatabase;
	
	public DatabaseCommand(PositionsDatabaseAdapter database) {
		mDatabase = database;
	}
}
