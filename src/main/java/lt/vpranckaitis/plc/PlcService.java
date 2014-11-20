package lt.vpranckaitis.plc;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;

import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.geo.Place;
import lt.vpranckaitis.plc.request.EmptyPositionsDatabaseAdapter;
import lt.vpranckaitis.plc.request.RequestHandler;
import lt.vpranckaitis.plc.scheduler.BackupDatabaseCommand;
import lt.vpranckaitis.plc.scheduler.CleanDatabaseCommand;
import lt.vpranckaitis.plc.scheduler.Scheduler;
import lt.vpranckaitis.plc.scheduler.SystemLogCommand;
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
		
		/*
		demoScheduler(new PositionsDatabaseAdapter() {
			@Override 
			public boolean updatePosition(String key, double latitude, double longitude) { return false; }
			@Override
			public List<Long> getProximity(List<Place> places) { return null; }
			@Override
			public boolean deletePosition(String key) { return false; }
			@Override
			public boolean createPosition(String key) { return false; }
			@Override
			public boolean checkKeyExists(String key) { return false; }
		});*/
	}
	
	public static void doSomeLogging(PrintStream out) {
		out.println("Much logging");
	}
	
	public static void demoScheduler(PositionsDatabaseAdapter database) {
		Scheduler scheduler = new Scheduler();
		
		Calendar dates[] = {Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance()};
		
		dates[1].add(Calendar.MINUTE, 10);
		scheduler.addCommand(dates[1], new CleanDatabaseCommand(database));
		scheduler.addCommand(dates[1], new BackupDatabaseCommand(database));
		
		dates[2].add(Calendar.MINUTE, 20);
		scheduler.addCommand(dates[2], new SystemLogCommand());
		
		for (Calendar date : dates) {
			System.out.println("Time: " + date.getTime().toString());
			scheduler.update(date);
			System.out.println("----------------------------------");
		}
		
	}

}