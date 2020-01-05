package fr.istic.mob.starldv2.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;

import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.adapter.StopAdapter;
import fr.istic.mob.starldv2.adapter.StopTimesAdapter;
import fr.istic.mob.starldv2.model.StopTime;

public class StopTimesFragment extends Fragment {

    ListView list;
    long idStopTimes;
    long idBusRoute;
    long sensTrip;
    Calendar calendar;
    StopTimesFragmentListener fragmentListener;

    public interface StopTimesFragmentListener {
        void validateOnClicked (int id, String schedule);
    }

    public StopTimesFragment (long idStopTimes, long idBusRoute, long sensTrip) {
        this.idStopTimes = idStopTimes;
        this.idBusRoute = idBusRoute;
        this.sensTrip = sensTrip;
    }

    public StopTimesFragment () {

    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stop_times, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        list = view.findViewById(R.id.stopTimesList);

        String [] args = {Long.toString(idStopTimes),Long.toString(idBusRoute),Long.toString(sensTrip)};

        Cursor cursor = getContext().getContentResolver().
                query(Uri.parse("content://fr.istic.starproviderLD/stoptime"), null,null, args, null);

        final StopTimesAdapter adapter = new StopTimesAdapter(getContext(), cursor);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StopTime stopTime = adapter.getItem(i);
                fragmentListener.validateOnClicked(stopTime.getTrip_id(), stopTime.getArrivalTime());
            }
        });
    }

    public static StopTimesFragment newInstance (long idStopTimes, long idBusRoute, long sensTrip) {
        return new StopTimesFragment(idStopTimes, idBusRoute, sensTrip);
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        if (getActivity() instanceof StopTimesFragment.StopTimesFragmentListener) {
            fragmentListener = (StopTimesFragment.StopTimesFragmentListener) getActivity();
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        fragmentListener = null;
    }

}
