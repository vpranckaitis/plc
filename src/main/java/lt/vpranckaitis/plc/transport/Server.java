package lt.vpranckaitis.plc.transport;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class Server implements Closeable {

	private Connection mConnection;
	private RequestListener mRequestListener;

	public Server(int port) throws IOException {
		mConnection = new SocketConnection(new ContainerServer(new RequestHandler()));
		SocketAddress address = new InetSocketAddress(port);
		mConnection.connect(address);
	}
	
	public void setRequestListener(RequestListener listener) {
		mRequestListener = listener;
	}

	@Override
	public void close() throws IOException {
		mConnection.close();
	}
	
	
	private class RequestHandler implements Container {

		@Override
		public void handle(Request request, Response response) {
			try {
				PrintStream body = response.getPrintStream();
				if (mRequestListener != null) {
					long time = System.currentTimeMillis();

					response.setValue("Content-Type", "text/plain");
					response.setValue("Server", "PlcServer/1.0");
					response.setDate("Date", time);
					response.setDate("Last-Modified", time);
					String message = "";
					HttpMethod method;
					switch (request.getMethod().toLowerCase()) {
					case "post":
						method = HttpMethod.POST;
						break;
					case "put":
						method = HttpMethod.PUT;
						break;
					case "get":
						method = HttpMethod.GET;
						break;
					case "delete":
						method = HttpMethod.DELETE;
						break;
					default:
						method = HttpMethod.UNKNOWN;
					}
					body.print(mRequestListener.handleRequest(method, request
							.getPath().getPath(),
							request.getQuery().toString(), request.getContent()));
				} else {
					body.println("Server won't process any requests");
				}
				body.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
