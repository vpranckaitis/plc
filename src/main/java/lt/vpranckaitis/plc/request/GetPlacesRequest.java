package lt.vpranckaitis.plc.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lt.vpranckaitis.plc.Constants;
import lt.vpranckaitis.plc.database.PlacesDatabaseAdapter;
import lt.vpranckaitis.plc.database.PositionsDatabaseAdapter;
import lt.vpranckaitis.plc.geo.Place;
import lt.vpranckaitis.plc.transport.ResponseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetPlacesRequest implements Request {

	private PositionsDatabaseAdapter mPositionDatabase;
	private PlacesDatabaseAdapter mPlacesDatabase;
	private double mLatitude = 0.0;
	private double mLongitude = 0.0;
	private List<String> mTypes = new ArrayList<>();
	private String mKey;
	

	public GetPlacesRequest(PositionsDatabaseAdapter position, PlacesDatabaseAdapter places, String key, double lat, double lon, List<String> types) {
		mPositionDatabase = position;
		mPlacesDatabase = places;
		mLatitude = lat;
		mLongitude = lon;
		mTypes = types;
		mKey = key;
		System.out.println(Arrays.toString(mTypes.toArray()));
	}

	@Override
	public ResponseData getResponse() {
		Map<String, String> response = new HashMap<String, String>();
		ResponseData responseData = new ResponseData();
		if (!mPositionDatabase.checkKeyExists(mKey)) {
			responseData.setStatus(404);
		} else {
			List<Place> places = mPlacesDatabase.getPlaces(mLatitude, mLongitude, Constants.RADIUS_FOR_PLACES, (mTypes != null ? mTypes.toArray(new String[1]) : null));
			Iterator<Place> placesIt = places.iterator();
			for (Long proximityScore : mPositionDatabase.getProximity(places)) {
				placesIt.next().setProximityScore(proximityScore);
			}
			JSONObject responseJSON = new JSONObject();
			try {
				responseData.setStatus(200);
				JSONArray results = new JSONArray();
				
				Collections.sort(places);
				for (Place place : places) {
					Map<String, String> placeInfo = new HashMap<String, String>();
					placeInfo.put("placeId", place.placeId);
					placeInfo.put("lat", Double.toString(place.latitude));
					placeInfo.put("lon", Double.toString(place.longitude));
					placeInfo.put("name", place.name);
					placeInfo.put("vicinity", place.vicinity);
					placeInfo.put("proximity_score", Long.toString(place.getProximityScore()));
					results.put(new JSONObject(placeInfo));
				}
				responseJSON.put("results", results);
				responseData.setResponseBody(responseJSON.toString());
			} catch (JSONException e) {
				e.printStackTrace();
				responseData.setStatus(500);
			}
		}
		return responseData;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		GetPlacesRequest temp = (GetPlacesRequest) obj;
		boolean typesEquals = (mTypes == null && temp.mTypes == null) 
				|| (mTypes != null && temp.mTypes != null && new HashSet<String>(mTypes).equals(new HashSet<String>(temp.mTypes)));
		double EPS = 1e-9;
		return (Math.abs(mLatitude - temp.mLatitude) < EPS) && (Math.abs(mLongitude - temp.mLongitude) < EPS) 
				&& typesEquals && mKey.equals(temp.mKey) 
				&& mPositionDatabase.equals(temp.mPositionDatabase)
				&& mPlacesDatabase.equals(temp.mPlacesDatabase);
	}

	@Override
	public String toString() {
		return "GetPlacesRequest[lat=" + mLatitude + "; lon=" + mLongitude + "; types=" + Arrays.toString(mTypes.toArray()) + "]";
	}
	
}
