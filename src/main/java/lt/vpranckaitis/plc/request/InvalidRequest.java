package lt.vpranckaitis.plc.request;

import java.util.HashMap;
import java.util.Map;

import lt.vpranckaitis.plc.transport.ResponseData;

import org.json.JSONObject;


public class InvalidRequest implements Request {

	@Override
	public ResponseData getResponse() {
		return new ResponseData(400,  new JSONObject().toString());
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
