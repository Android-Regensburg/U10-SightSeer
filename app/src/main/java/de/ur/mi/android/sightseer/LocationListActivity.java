package de.ur.mi.android.sightseer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import de.ur.mi.android.sightseer.adapter.LocationListAdapter;
import de.ur.mi.android.sightseer.helpers.AppConfig;
import de.ur.mi.android.sightseer.helpers.JSONParser;
import de.ur.mi.android.sightseer.location.Destination;

public class LocationListActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 0;
    private ListView locationList;
    private ArrayList<Destination> destinations;
    private LocationListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
        } else {
            init();
        }
    }

    @Override
    protected void onResume() {
        initLocationData();
        super.onResume();
    }

    private void init() {
        initUI();
        initApplication();
        initLocationData();
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        locationList = findViewById(R.id.location_list);
    }

    private void initApplication() {
        destinations = new ArrayList<>();
        listAdapter = new LocationListAdapter(this, destinations);
        locationList.setAdapter(listAdapter);
        locationList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationSelected(destinations.get(position));
            }
        });
    }

    private void initLocationData() {
        destinations.clear();
        destinations.addAll(JSONParser.getDestinationList(AppConfig.JSONString.JSON_STRING));
        listAdapter.notifyDataSetChanged();
    }

    private void locationSelected(Destination destination) {
        String jsonString = destination.getJsonString();
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra(AppConfig.INTENT_KEY_JSON_STRING, jsonString);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
                init();
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
