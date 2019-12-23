package fr.istic.mob.starldv2.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.model.BusRoute;
import fr.istic.mob.starldv2.model.Stop;

public class StopFragment extends Fragment {

    private ListView list;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);

        list = (ListView) view.findViewById(R.id.stopList);

      /*  Cursor cursor = getContext().getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/stop"),null,null,null,null);
        ArrayList<String> listStop = convertCursorToArrayList(cursor);
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_stop,listStop);
        list.setAdapter(adapter);*/

        return view;
    }

    private ArrayList<String> convertCursorToArrayList (Cursor cursor) {
        ArrayList<String> ret = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String stop = cursor.getString(1);
            ret.add(stop);
            cursor.moveToNext();
        }
        cursor.close();
        return ret;
    }
}
