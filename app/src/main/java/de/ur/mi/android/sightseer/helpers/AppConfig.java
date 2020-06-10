package de.ur.mi.android.sightseer.helpers;

public class AppConfig {

    public static final int LOCATION_UPDATE_INTERVAL = 1000;
    public static final int LOCATION_DISTANCE_THRESHOLD = 1;

    public static final String TITLE_KEY = "title";
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String ALTITUDE_KEY = "altitude";

    public static final String INTENT_KEY_JSON_STRING = "JSONString";

    public static class JSONString {
        public static final String JSON_STRING = "[" +
                "{\"" + TITLE_KEY + "\":\"Zentralbibliothek\",\"" + LATITUDE_KEY + "\":" + 48.99798687 + ",\"" + LONGITUDE_KEY + "\":" + 12.09586537 + ",\"" + ALTITUDE_KEY + "\":" + 420.0 + "}," +
                "{\"" + TITLE_KEY + "\":\"Mensa\",\"" + LATITUDE_KEY + "\":" + 48.99816648 + ",\"" + LONGITUDE_KEY + "\":" + 12.09365657 + ",\"" + ALTITUDE_KEY + "\":" + 418.0 + "}," +
                "{\"" + TITLE_KEY + "\":\"Studentenkanzlei\",\"" + LATITUDE_KEY + "\":" + 48.998523 + ",\"" + LONGITUDE_KEY + "\":" + 12.09161427 + ",\"" + ALTITUDE_KEY + "\":" + 419.0 + "}," +
                "{\"" + TITLE_KEY + "\":\"Bushaltestelle\",\"" + LATITUDE_KEY + "\":" + 48.99886894 + ",\"" + LONGITUDE_KEY + "\":" + 12.09271896 + ",\"" + ALTITUDE_KEY + "\":" + 414.0 + "}," +
                "{\"" + TITLE_KEY + "\":\"Rechenzentrum\",\"" + LATITUDE_KEY + "\":" + 48.99790167 + ",\"" + LONGITUDE_KEY + "\":" + 12.09595323 + ",\"" + ALTITUDE_KEY + "\":" + 416.0 + "}," +
                "{\"" + TITLE_KEY + "\":\"Vielberth-Gebäude\",\"" + LATITUDE_KEY + "\":" + 49.00018068 + ",\"" + LONGITUDE_KEY + "\":" + 12.09507768 + ",\"" + ALTITUDE_KEY + "\":" + 412.0 + "}" +
                "]";
    }
}
