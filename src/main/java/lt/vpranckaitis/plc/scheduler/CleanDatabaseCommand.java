package lt.vpranckaitis.plc.scheduler;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;

public class CleanDatabaseCommand extends DatabaseCommand {
	
	public CleanDatabaseCommand(PositionsDatabaseAdapter database) {
		super(database);
	}

	@Override
	public void execute() {
		System.out.println("Cleaning database");
	}

}
