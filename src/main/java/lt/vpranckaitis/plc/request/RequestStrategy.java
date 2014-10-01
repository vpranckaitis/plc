package lt.vpranckaitis.plc.request;

import java.util.Map;

public interface RequestStrategy {
	public String handle(String query);
}
