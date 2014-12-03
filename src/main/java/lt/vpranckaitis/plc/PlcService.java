package lt.vpranckaitis.plc;

import lt.vpranckaitis.plc.request.RequestHandler;
import lt.vpranckaitis.plc.transport.Server;

public class PlcService {

	public static void main(String[] list) throws Exception {
		Server server = new Server(8080);
		server.setRequestListener(new RequestHandler());
	}
}