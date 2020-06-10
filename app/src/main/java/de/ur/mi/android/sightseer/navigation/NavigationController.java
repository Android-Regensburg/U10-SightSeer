package de.ur.mi.android.sightseer.navigation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import de.ur.mi.android.sightseer.helpers.AppConfig;
import de.ur.mi.android.sightseer.location.Destination;

public class NavigationController implements LocationListener {

    private static NavigationController mInstance;
    private Location lastLocation;
    private Context context;

    private LocationManager locationManager;
    private NavigationListener listener;
    private String provider;
    private Destination destination;

    public static NavigationController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NavigationController(context);
        }
        return mInstance;
    }

    private NavigationController(Context context) {
        this.context = context.getApplicationContext();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        setProvider();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lastLocation = locationManager.getLastKnownLocation(provider);
        }
    }

    private void setProvider() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setBearingRequired(true);
        provider = locationManager.getBestProvider(criteria, true);
        if (provider == null) {
            Log.e("setProvider", "no provider set");
        }
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
        NavigationDetail navigationDetail = generateNavigationDetail(destination);
        listener.onNavigationDetailChanged(navigationDetail);
    }

    private NavigationDetail generateNavigationDetail(Destination destination) {
        if (lastLocation == null) {
            lastLocation = new Location(provider);
        }
        float distance = lastLocation.distanceTo(destination.getLocation());
        float bearing = lastLocation.bearingTo(destination.getLocation());
        return new NavigationDetail(destination.getTitle(), distance, bearing - lastLocation.getBearing());
    }

    public void startNavigation() {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(provider, AppConfig.LOCATION_UPDATE_INTERVAL, AppConfig.LOCATION_DISTANCE_THRESHOLD, this);
        }
    }

    public void stopNavigation() {
        locationManager.removeUpdates(this);
    }

    public void setNavigationListener(NavigationListener listener) {
        this.listener = listener;
    }

    public float getEstimatedDistanceForLocation(Location location) {
        if (lastLocation == null) {
            lastLocation = new Location(provider);
        }
        return lastLocation.distanceTo(location);
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        if (listener != null) {
            listener.onSignalFound();
            if (destination != null) {
                NavigationDetail navigationDetail = generateNavigationDetail(destination);
                listener.onNavigationDetailChanged(navigationDetail);
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (listener != null) {
            listener.onSignalLost();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (listener != null) {
            listener.onSignalFound();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
