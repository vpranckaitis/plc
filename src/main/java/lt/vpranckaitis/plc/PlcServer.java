package lt.vpranckaitis.plc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class PlcServer implements Container {

   public void handle(Request request, Response response) {
      try {
         PrintStream body = response.getPrintStream();
         long time = System.currentTimeMillis();
   
         response.setValue("Content-Type", "text/plain");
         response.setValue("Server", "PlcServer/1.0 (Simple 4.0)");
         response.setDate("Date", time);
         response.setDate("Last-Modified", time);
         String message = "";
         HttpURLConnection conn = (HttpURLConnection) new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=54.89,23.91&radius=1000&key=AIzaSyAmTFs28OlBPQpX7IWMY75tS1dW8mg35LQ").openConnection();
         if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	 BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	 message = r.lines().reduce("", (x, y) -> x + y + "\n");
         }
        
         body.println("Your value was: " + message);
         body.close();
      } catch(Exception e) {
         e.printStackTrace();
      }
   } 

   public static void main(String[] list) throws Exception {
      Container container = new PlcServer();
      Server server = new ContainerServer(container);
      Connection connection = new SocketConnection(server);
      SocketAddress address = new InetSocketAddress(8080);
      
      connection.connect(address);
      
      Node node = NodeBuilder.nodeBuilder().node();
      Client client = node.client();
      
      
   }
}