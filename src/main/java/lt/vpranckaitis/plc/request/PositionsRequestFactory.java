package lt.vpranckaitis.plc.request;

import lt.vpranckaitis.plc.database.DatabaseAdapter;
import lt.vpranckaitis.plc.transport.HttpMethod;

public class PositionsRequestFactory implements RequestFactory {

	DatabaseAdapter mPositionsDb;
	
	public PositionsRequestFactory(DatabaseAdapter positions) {
		mPositionsDb = positions;
	}

	@Override
	public Request constructRequest(HttpMethod method, String uri,
			String query, String content) {
		// TODO Auto-generated method stub
		return null;
	}

}
