package de.ur.mi.android.sightseer.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import de.ur.mi.android.sightseer.location.Destination;

public class JSONParser {

    public static ArrayList<Destination> getPOIList(String JSONArrayString) {
        ArrayList<Destination> destinations = new ArrayList<Destination>();
        try {
            JSONArray jsonArray = new JSONArray(JSONArrayString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                destinations.add(getDestination(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return destinations;
    }

    public static Destination getDestination(JSONObject jsonObject) throws JSONException {
        String title = jsonObject.getString(AppConfig.TITLE_KEY);
        double latitude = jsonObject.getDouble(AppConfig.LATITUDE_KEY);
        double longitude = jsonObject.getDouble(AppConfig.LONGITUDE_KEY);
        double altitude = jsonObject.getDouble(AppConfig.ALTITUDE_KEY);

        return new Destination(title,latitude,longitude,altitude);
    }
}
