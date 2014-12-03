package lt.vpranckaitis.plc.transport;

public class ResponseData {
	private String mResponseBody = "";
	private int mStatus = 0;
	
	public ResponseData() {
		
	}
	
	public ResponseData(int status, String responseBody) {
		mResponseBody = responseBody;
		mStatus = status;
	}
	public String getResponseBody() {
		return mResponseBody;
	}
	public int getStatus() {
		return mStatus;
	}
	
	public void setResponseBody(String responseBody) {
		mResponseBody = responseBody;
	}
	public void setStatus(int status) {
		mStatus = status;
	}
	
	
}
