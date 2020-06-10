package de.ur.mi.android.sightseer.location;

import android.location.Location;
import android.location.LocationManager;
import de.ur.mi.android.sightseer.helpers.AppConfig;

public class Destination {

    private String title;
    private Location location;
    private String jsonString;

    public Destination(String title, double latitude, double longitude, double altitude) {
        this.title = title;
        this.location = createLocation(latitude, longitude, altitude);
        this.jsonString = createJsonString(latitude, longitude, altitude);
    }

    private Location createLocation(double latitude, double longitude, double altitude) {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(altitude);
        return location;
    }

    private String createJsonString(double latitude, double longitude, double altitude) {
        return "{\"" + AppConfig.TITLE_KEY + "\":\"" + title + "\",\"" + AppConfig.LATITUDE_KEY + "\":" + latitude + ",\"" + AppConfig.LONGITUDE_KEY + "\":" + longitude + ",\"" + AppConfig.ALTITUDE_KEY + "\":" + altitude + "}";
    }

    public String getTitle() {
        return title;
    }

    public Location getLocation() {
        return location;
    }

    public String getJsonString() {
        return jsonString;
    }
}
