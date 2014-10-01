package lt.vpranckaitis.plc;

import java.io.PrintStream;
import java.util.UUID;

import lt.vpranckaitis.plc.database.DatabaseAdapter;
import lt.vpranckaitis.plc.request.AbstractRequestFactory;
import lt.vpranckaitis.plc.request.RequestHandler;
import lt.vpranckaitis.plc.transport.HttpMethod;
import lt.vpranckaitis.plc.transport.Server;

public class PlcService {

	public static void main(String[] list) throws Exception {
		PlcService service = new PlcService();
		Server server = new Server(8080);
		server.setRequestListener(new RequestHandler());
		
		AbstractRequestFactory f = new AbstractRequestFactory(new DatabaseAdapter() {
		}, new DatabaseAdapter() {
		});
		f.constructRequest(HttpMethod.POST, "/position/fdasokfdsok/fdasfdsf/12131", "", "");
		
		Logger.printLn("Static printing");
		Thread.sleep(1000);
		
		doSomeLogging(Logger.getInstance());
		Thread.sleep(1000);
		doSomeLogging(System.out);
		System.out.println(UUID.randomUUID().toString());
		
		
	}
	
	public static void doSomeLogging(PrintStream out) {
		out.println("Much logging");
	}

}