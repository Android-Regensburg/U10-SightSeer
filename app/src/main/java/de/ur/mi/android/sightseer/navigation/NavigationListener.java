package de.ur.mi.android.sightseer.navigation;

public interface NavigationListener {

    void onNavigationDetailChanged(NavigationDetail navigationDetail);
    void onSignalFound();
    void onSignalLost();
}
