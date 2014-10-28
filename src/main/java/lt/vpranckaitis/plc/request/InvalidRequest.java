package lt.vpranckaitis.plc.request;


public class InvalidRequest implements Request {

	@Override
	public String getResponse() {
		return "{\n\t\"status\":\"INVALID_REQUEST\",\n\t \"comment\":\"Something is wrong with the request\"\n}";
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
