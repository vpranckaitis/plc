package lt.vpranckaitis.plc;

import java.io.PrintStream;
import java.util.List;

import lt.vpranckaitis.plc.database.GooglePlacesDatabase;
import lt.vpranckaitis.plc.geo.Place;
import lt.vpranckaitis.plc.request.RequestHandler;
import lt.vpranckaitis.plc.transport.Server;

public class PlcService {

	public static void main(String[] list) throws Exception {
		PlcService service = new PlcService();
		Server server = new Server(8080);
		server.setRequestListener(new RequestHandler());
		
		/*GooglePlacesDatabase g = new GooglePlacesDatabase(Constants.GOOGLE_PLACES_API_KEY);
		List<Place> pl = g.getPlaces(54.904262, 23.958592, Constants.RADIUS_FOR_PLACES, null);
		System.out.println(pl.size());
		for (Place p : pl) {
			System.out.println(p.toString());
		}*/
	}
	
	public static void doSomeLogging(PrintStream out) {
		out.println("Much logging");
	}

}