package de.ur.mi.android.sightseer.navigation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class NavigationController implements LocationListener {

    private static NavigationController mInstance;

    public static NavigationController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NavigationController(context);
        }
        return mInstance;
    }

    private NavigationController(Context context) {
    }

    public float getEstimatedDistanceForLocation(Location location) { return -1; }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
