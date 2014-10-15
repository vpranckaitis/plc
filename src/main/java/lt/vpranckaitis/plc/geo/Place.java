package lt.vpranckaitis.plc.geo;

public class Place {
	public final String placeId;
	public final double latitude;
	public final double longitude;
	public final String name;
	public final String vicinity;
	
	public Place(String placeId, double latitude, double longtitude,
			String name, String vicinity) {
		super();
		this.placeId = placeId;
		this.latitude = latitude;
		this.longitude = longtitude;
		this.name = name;
		this.vicinity = vicinity;
	}
}
