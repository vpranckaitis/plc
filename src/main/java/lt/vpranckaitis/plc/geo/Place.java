package lt.vpranckaitis.plc.geo;

public class Place {
	public final String placeId;
	public final double latitude;
	public final double longtitude;
	public final String name;
	public final String vicinity;
	
	public Place(String placeId, double latitude, double longtitude,
			String name, String vicinity) {
		super();
		this.placeId = placeId;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.name = name;
		this.vicinity = vicinity;
	}
}
