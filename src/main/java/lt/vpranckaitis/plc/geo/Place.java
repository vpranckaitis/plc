package lt.vpranckaitis.plc.geo;

public class Place implements Comparable<Place> {
	public final String placeId;
	public final double latitude;
	public final double longitude;
	public final String name;
	public final String vicinity;
	private long mProximity = 0;
	
	public static final Place EMPTY; 
	
	static {
		EMPTY = new Place("", 0.0, 0.0, "", "");
	}
	
	public Place(String placeId, double latitude, double longitude,
			String name, String vicinity) {
		super();
		this.placeId = placeId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.vicinity = vicinity;
	}
	
	public long getProximityScore() {
		return mProximity;
	}
	
	public void setProximityScore(long proximity) {
		mProximity = proximity;
	}

	@Override
	public int compareTo(Place place) {
		if (mProximity == place.mProximity) {
			return name.compareTo(place.name);
		}
		return (mProximity > place.mProximity ? -1 : 1);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	public static class Builder {
		
		private String mPlaceId = "";
		private double mLatitude = 0.0;
		private double mLongitude = 0.0;
		private String mName = "";
		private String mVicinity = "";
		private long mProximity = 0;
		
		private Builder() {}
		
		public Builder setPlaceId(String placeId) {
			mPlaceId = placeId;
			return this;
		}

		public Builder setLatitude(double latitude) {
			mLatitude = latitude;
			return this;
		}

		public Builder setLongitude(double longitude) {
			mLongitude = longitude;
			return this;
		}

		public Builder setName(String name) {
			mName = name;
			return this;
		}

		public Builder setVicinity(String vicinity) {
			mVicinity = vicinity;
			return this;
		}

		public Builder setProximity(long proximity) {
			mProximity = proximity;
			return this;
		}
		
		public Place build() {
			Place p = new Place(mPlaceId, mLatitude, mLongitude,
					mName, mVicinity);
			p.setProximityScore(mProximity);
			return p;
		}
	}
}
