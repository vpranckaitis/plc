package lt.vpranckaitis.plc.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import lt.vpranckaitis.plc.geo.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlacesDatabase implements PlacesDatabaseAdapter {

	private GooglePlacesHelper mPlacesHelper;
	
	public GooglePlacesDatabase(String apiKey) {
		mPlacesHelper = new GooglePlacesHelper(apiKey);
	}
	
	@Override
	public List<Place> getPlaces(double latitude, double longtitude,
			int radius, String[] types) {
		String response = mPlacesHelper.makeRequest(latitude, longtitude, radius, types);
		List<Place> places = new LinkedList<>();
		if (mPlacesHelper.isStatusOk(response)) {
			System.out.println("status ok");
			System.out.println(mPlacesHelper.parsePlaces(response).size());
			places.addAll(mPlacesHelper.parsePlaces(response));
			String pageToken;
			while ((pageToken = mPlacesHelper.getPageToken(response)) != null) {
				try {
					Thread.sleep(2000);  //slow Google is slow
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				response = mPlacesHelper.makeRequest(pageToken);
				if (mPlacesHelper.isStatusOk(response)) {
					places.addAll(mPlacesHelper.parsePlaces(response));
				}
			}
		} 
		return places;
	}

	protected class GooglePlacesHelper {
		private static final String PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
		private static final String QUERY_FORMAT = PLACES_URL + "json?location=%1$f,%2$f&radius=%3$d%4$s&key=%5$s";
		private static final String QUERY_PAGE_FORMAT = PLACES_URL + "json?pagetoken=%1$s&key=%2$s";
		
		private String mApiKey;
		
		public GooglePlacesHelper(String apiKey) {
			mApiKey = apiKey;
		}
		
		public String makeRequest(double latitude, double longtitude, int radius, String[] types) {
			String typesFlat = "";
			if (types != null && types.length > 0) {
				StringBuilder sb = new StringBuilder("&types=");
				for (int i = 0; i < types.length - 1; i++) {
					sb.append(types[i]);
					sb.append('|');
				}
				sb.append(types[types.length - 1]);
				typesFlat += sb.toString();
			}
			String query = String.format(Locale.ENGLISH, QUERY_FORMAT, latitude, longtitude, radius, typesFlat, mApiKey);
			return performRequest(query);
		}
		
		public String makeRequest(String pageToken) {
			
			String query = String.format(Locale.ENGLISH, QUERY_PAGE_FORMAT, pageToken, mApiKey);
			return performRequest(query);
		}
		
		private String performRequest(String url) {
			System.out.println(url);
			try {
				System.out.println(new URL(url).toString());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BufferedReader resultReader = null;
			StringBuilder result = new StringBuilder();
			try {
				URLConnection conn = new URL(url).openConnection();
				resultReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = resultReader.readLine()) != null) {
					result.append(line);
					result.append('\n');
				}
				System.out.println(result.toString());
			} catch (Exception e) {
				
			} finally {
				try {
					resultReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result.toString();
		}
		
		public String getPageToken(String content) {
			try {
				JSONObject json = new JSONObject(content);
				if (json.has("next_page_token")) {
					return json.getString("next_page_token");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public boolean isStatusOk(String content) {
			try {
				JSONObject json = new JSONObject(content);
				String status = json.getString("status").toLowerCase();
				return (status.equals("ok") ? true : false);
			} catch (JSONException e) {
				
			}
			return false;
		}
		
		public List<Place> parsePlaces(String content) {
			List<Place> places = new LinkedList<>();
			try {
				JSONObject json = new JSONObject(content);
				JSONArray resultsJson = json.getJSONArray("results");
				for (int i = 0; i < resultsJson.length(); i++) {
					JSONObject placeJson = resultsJson.getJSONObject(i);
					JSONObject locationJson = placeJson.getJSONObject("geometry").getJSONObject("location");
					double latitude = locationJson.getDouble("lat");
					double longtitude = locationJson.getDouble("lng");
					String placeId = placeJson.getString("place_id");
					String name = placeJson.getString("name");
					String vicinity = placeJson.getString("vicinity");
					places.add(new Place(placeId, latitude, longtitude, name, vicinity));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return places;
		}
	}
}
