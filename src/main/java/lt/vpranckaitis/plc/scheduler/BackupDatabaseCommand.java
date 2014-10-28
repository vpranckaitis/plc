package lt.vpranckaitis.plc.scheduler;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public class BackupDatabaseCommand extends DatabaseCommand {
	
	public BackupDatabaseCommand(PositionsDatabaseAdapter database) {
		super(database);
	}
	
	@Override
	public void execute() {
		System.out.println("Database has been backed-up");
	}

}
