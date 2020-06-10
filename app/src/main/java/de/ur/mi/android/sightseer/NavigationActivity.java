package de.ur.mi.android.sightseer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import de.ur.mi.android.sightseer.helpers.AppConfig;
import de.ur.mi.android.sightseer.helpers.JSONParser;
import de.ur.mi.android.sightseer.location.Destination;
import de.ur.mi.android.sightseer.navigation.NavigationController;
import de.ur.mi.android.sightseer.navigation.NavigationDetail;
import de.ur.mi.android.sightseer.navigation.NavigationListener;

public class NavigationActivity extends AppCompatActivity implements NavigationListener {

    private TextView navigationTitle,
            navigationDistance,
            gpsStatus;
    private ImageView navigationCompass;
    private Destination destination;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initNavigation();
        startNavigation();
    }

    private void initUI() {
        setContentView(R.layout.activity_navigation);
        navigationTitle = findViewById(R.id.navigation_title);
        navigationDistance = findViewById(R.id.navigation_distance);
        gpsStatus = findViewById(R.id.navigation_signal_info);
        navigationCompass = findViewById(R.id.compass_view);
    }

    private void initNavigation() {
        NavigationController.getInstance(this).setNavigationListener(this);
    }

    private void startNavigation() {
        String jsonString = getIntent().getStringExtra(AppConfig.INTENT_KEY_JSON_STRING);
        if (jsonString == null) return;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            destination = JSONParser.getDestination(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (destination != null) {
            NavigationController navigationController = NavigationController.getInstance(this);
            navigationController.setDestination(destination);
            navigationController.startNavigation();
        }
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

    @Override
    protected void onPause() {
        NavigationController.getInstance(this).stopNavigation();
        super.onPause();
    }
}
