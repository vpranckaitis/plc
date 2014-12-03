package lt.vpranckaitis.plc;

public class Logger {
	
	public static final int INFO = 1 << 0;
	public static final int DEBUG = 1 << 1;
	public static final int ERROR = 1 << 2;
	
	private static Logger sLogger;
	
	private static Handler sHandler;
	
	private Logger() {
		sHandler = new InfoHandler();
		sHandler.setSuccessor(new DebugHandler()).setSuccessor(new ErrorHandler());
	}
	
	public static Logger getInstance() {
		if (sLogger == null) {
			 sLogger = new Logger();
		}
		return sLogger;
	}
	
	public void println(String s, int type) {
		sHandler.handle(s, type);
	}
	
	private static abstract class Handler {
		private Handler mSuccessor = null;
		
		public void handle(String s, int type) {
			if (mSuccessor != null) {
				mSuccessor.handle(s, type);
			}
		}

		public Handler setSuccessor(Handler successor) {
			mSuccessor = successor;
			return successor;
		}
	}

	private static class InfoHandler extends Handler {
		@Override
		public void handle(String s, int type) {
			if ((type & INFO) != 0) {
				System.out.println("<INFO>" + s + "</INFO>");
			} 
			super.handle(s, type);
		}
	}
	
	private static class DebugHandler extends Handler {
		@Override
		public void handle(String s, int type) {
			if ((type & DEBUG) != 0) {
				System.out.println("<DEBUG>" + s + "</DEBUG>");
			} 
			super.handle(s, type);
		}
	}
	
	private static class ErrorHandler extends Handler {
		@Override
		public void handle(String s, int type) {
			if ((type & ERROR) != 0) {
				System.out.println("<ERROR>" + s + "</ERROR>");
			} 
			super.handle(s, type);
		}
	}
}
