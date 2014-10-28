package lt.vpranckaitis.plc.geo;

public class Place {
	public final String placeId;
	public final double latitude;
	public final double longitude;
	public final String name;
	public final String vicinity;
	private long mProximity = -1;
	
	public Place(String placeId, double latitude, double longtitude,
			String name, String vicinity) {
		super();
		this.placeId = placeId;
		this.latitude = latitude;
		this.longitude = longtitude;
		this.name = name;
		this.vicinity = vicinity;
	}
	
	public long getProximityCount() {
		return mProximity;
	}
	
}
