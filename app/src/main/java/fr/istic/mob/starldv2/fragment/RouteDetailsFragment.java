package fr.istic.mob.starldv2.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.adapter.RouteDetailsAdapter;

public class RouteDetailsFragment extends Fragment {

    private String schedule;
    private int idTrip;
    private ListView list;

    public RouteDetailsFragment () {

    }

    public RouteDetailsFragment (String schedule, int idTrip) {
        this.idTrip = idTrip;
        this.schedule = schedule;
    }

    public static RouteDetailsFragment newInstance (String schedule, int idTrip) {
        return new RouteDetailsFragment(schedule,idTrip);
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stop, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        list = view.findViewById(R.id.stopList);

        Cursor cursor = getContext().getContentResolver().
                query(Uri.parse("content://fr.istic.starproviderLD/routedetail"), null, Integer.toString(idTrip),null,schedule);

        System.out.println("count" + cursor.getCount()+ idTrip+ schedule);

        final RouteDetailsAdapter adapter = new RouteDetailsAdapter(getContext(),cursor);
        list.setAdapter(adapter);

    }


}
