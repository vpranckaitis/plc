package lt.vpranckaitis.plc.transport;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URLDecoder;

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
				long time = System.currentTimeMillis();
				response.setValue("Content-Type", "text/plain; charset=UTF-8");
				response.setValue("Server", "PlcServer/1.0");
				response.setValue("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
				response.setValue("Access-Control-Allow-Headers", "origin, content-type, accept");
				response.setValue("Access-Control-Allow-Origin", "*");
				response.setDate("Date", time);
				response.setDate("Last-Modified", time);
				
				PrintStream body = response.getPrintStream();
				if (mRequestListener != null) {
					
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
					String path = URLDecoder.decode(request.getPath().getPath(), "UTF-8");
					String query = URLDecoder.decode(request.getQuery().toString(), "UTF-8");
					String content = request.getContent();
					body.print(notifyListener(method, path, query, content));
				} else {
					body.println("Server won't process any requests");
				}
				body.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public String notifyListener(HttpMethod method, String uri, String query, String content) {
			if (mRequestListener != null) {
				return mRequestListener.handleRequest(method, uri,
						query, content);
			} 
			return "";
		}
		
	}
}
