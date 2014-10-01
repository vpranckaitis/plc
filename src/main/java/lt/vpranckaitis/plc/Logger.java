package lt.vpranckaitis.plc;

import java.io.PrintStream;

public class Logger extends PrintStream {
	private static Logger mLogger;
	
	private Logger() {
		super(System.err);
	}
	
	public static Logger getInstance() {
		if (mLogger == null) {
			 mLogger = new Logger();
		}
		return mLogger;
	}
	
	public static void printLn(String s) {
		if (mLogger == null) {
			getInstance();
		}
		mLogger.println(s);
	}

}
