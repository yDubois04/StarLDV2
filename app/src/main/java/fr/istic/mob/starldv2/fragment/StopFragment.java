package fr.istic.mob.starldv2.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.adapter.StopAdapter;
import fr.istic.mob.starldv2.R;

public class StopFragment extends Fragment {

    private ListView list;
    private long id;
    private long sens;
    private Cursor cursor;

    public StopFragment () {

    }

    public StopFragment (long id, long sens) {
        this.id = id;
        this.sens = sens;
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        list = view.findViewById(R.id.stopList);

        cursor = getContext().getContentResolver().
                    query(Uri.parse("content://fr.istic.starproviderLD/stop"), null, Long.toString(id), null, Long.toString(sens));

        final StopAdapter adapter = new StopAdapter(getContext(),cursor);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext()," "+ adapter.getItem(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public static StopFragment newInstance (long id, long sens) {
        return new StopFragment(id, sens);
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("id", id);
        outState.putLong("sens", sens);
    }
}
