package de.ur.mi.android.sightseer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.ur.mi.android.sightseer.adapter.LocationListAdapter;
import de.ur.mi.android.sightseer.helpers.AppConfig;
import de.ur.mi.android.sightseer.helpers.JSONParser;
import de.ur.mi.android.sightseer.location.Destination;

public class LocationListActivity extends AppCompatActivity {

    private ListView locationList;
    private ArrayList<Destination> destinations;
    private LocationListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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
        destinations.addAll(JSONParser.getPOIList(AppConfig.JSONString.JSON_STRING));
        listAdapter.notifyDataSetChanged();
    }

    private void locationSelected(Destination destination) {
        String jsonString = destination.getJsonString();
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra(AppConfig.INTENT_KEY_JSON_STRING, jsonString);
        startActivity(intent);
    }
}
