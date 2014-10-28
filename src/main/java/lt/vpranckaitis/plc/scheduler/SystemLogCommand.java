package lt.vpranckaitis.plc.scheduler;

public class SystemLogCommand implements Command {

	@Override
	public void execute() {
		System.out.println("Logging entire system");
	}
}
