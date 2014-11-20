package lt.vpranckaitis.plc.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Scheduler {
	private TreeMap<Calendar, List<Command>> mHistory;
	private TreeMap<Calendar, List<Command>> mSchedule;
	
	public Scheduler() {
		mSchedule = new TreeMap<Calendar, List<Command>>();
		mHistory = new TreeMap<Calendar, List<Command>>();
	}
	
	public void addCommand(Calendar date, Command command) {
		date.set(Calendar.SECOND, 0);
		if (!mSchedule.containsKey(date)) {
			mSchedule.put(date, new ArrayList<Command>());
		}
		mSchedule.get(date).add(command);
	}
	
	public void update(Calendar current) {
		for (Entry<Calendar, List<Command>> e : mSchedule.headMap(current, true).entrySet()) {
			for (Command c : e.getValue()) {
				c.execute();
			}
		}
		mHistory.putAll(mSchedule.headMap(current, true));
		mSchedule.keySet().removeAll(mSchedule.headMap(current, true).keySet());
	}
}
