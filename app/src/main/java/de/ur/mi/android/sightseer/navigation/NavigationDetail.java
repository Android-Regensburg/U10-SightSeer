package de.ur.mi.android.sightseer.navigation;

public class NavigationDetail {

    private final String title;
    private final float distance;
    private final float bearing;

    public NavigationDetail(String title, float distance, float bearing) {
        this.title = title;
        this.distance = distance;
        this.bearing = bearing;
    }

    public String getTitle() {
        return title;
    }

    public float getDistance() {
        return distance;
    }

    public float getBearing() {
        return bearing;
    }
}
