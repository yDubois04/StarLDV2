package fr.istic.mob.starldv2.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.StopAdapter;
import fr.istic.mob.starldv2.R;

public class StopFragment extends Fragment {

    private ListView list;
    private StopAdapter adapter;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        list = view.findViewById(R.id.stopList);

        return view;
    }

    public void createList (long id, int sens) {
        Cursor cursor = getContext().getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/stop"), null, Long.toString(id), null, Integer.toString(sens));

        StopAdapter adapter = new StopAdapter(getContext(),cursor);
        list.setAdapter(adapter);
    }

   /* @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("stops",stops);
    }*/
}
