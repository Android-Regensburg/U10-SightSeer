package de.ur.mi.android.sightseer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import de.ur.mi.android.sightseer.R;
import de.ur.mi.android.sightseer.location.Destination;
import de.ur.mi.android.sightseer.navigation.NavigationController;

public class LocationListAdapter extends ArrayAdapter<Destination> {

    private ArrayList<Destination> destinations;
    private Context context;

    public LocationListAdapter(Context context, ArrayList<Destination> destinations) {
        super(context, R.layout.location_list_item, destinations);
        this.context = context;
        this.destinations = destinations;
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(destinations, new LocationDistanceComparator());
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_list_item, null);
        }
        Destination destination = destinations.get(position);
        TextView title = convertView.findViewById(R.id.list_item_title);
        TextView distance = convertView.findViewById(R.id.list_item_distance);

        title.setText(destination.getTitle());
        float targetDistance = NavigationController.getInstance(context).getEstimatedDistanceForLocation(destination.getLocation());
        String distanceString = Math.round(targetDistance) + "m";
        distance.setText(distanceString);
        return convertView;
    }

    private class LocationDistanceComparator implements Comparator<Destination> {

        @Override
        public int compare(Destination o1, Destination o2) {
            NavigationController navigationController = NavigationController.getInstance(context);
            float distanceToFirstLocation = navigationController.getEstimatedDistanceForLocation(o1.getLocation());
            float distanceToSecondLocation = navigationController.getEstimatedDistanceForLocation(o2.getLocation());
            return (int) (distanceToFirstLocation - distanceToSecondLocation);
        }
    }
}
