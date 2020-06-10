package de.ur.mi.android.sightseer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.ur.mi.android.sightseer.navigation.NavigationDetail;
import de.ur.mi.android.sightseer.navigation.NavigationListener;

public class NavigationActivity extends AppCompatActivity implements NavigationListener {

    private TextView navigationTitle,
            navigationDistance,
            gpsStatus;
    private ImageView navigationCompass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_navigation);
        navigationTitle = findViewById(R.id.navigation_title);
        navigationDistance = findViewById(R.id.navigation_distance);
        gpsStatus = findViewById(R.id.navigation_signal_info);
        navigationCompass = findViewById(R.id.compass_view);
    }

    private void updateUI(String title, float distance, float bearing) {
        navigationTitle.setText(title);
        String distanceString = Math.round(distance) + "m";
        navigationDistance.setText(distanceString);
        rotateCompass(bearing);
    }

    private void rotateCompass(float bearing) {
        navigationCompass.setRotation(0);
        navigationCompass.setRotation(bearing);
    }

    @Override
    public void onNavigationDetailChanged(NavigationDetail navigationDetail) {
        updateUI(navigationDetail.getTitle(), navigationDetail.getDistance(), navigationDetail.getBearing());
    }

    @Override
    public void onSignalFound() {
        gpsStatus.setText(getString(R.string.navigation_signal_info_connected));
        gpsStatus.setBackgroundColor(getColor(R.color.gps_green));
        navigationCompass.setImageResource(R.drawable.compass);
    }

    @Override
    public void onSignalLost() {
        gpsStatus.setText(getString(R.string.navigation_signal_info_searching));
        gpsStatus.setBackgroundColor(getColor(R.color.gps_red));
        navigationCompass.setImageResource(R.drawable.compass_no_gps);
    }

}
