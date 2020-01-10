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
import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.adapter.StopAdapter;
import fr.istic.mob.starldv2.R;

public class StopFragment extends Fragment {

    private ListView list;
    private Cursor cursor;
    private StopFragmentListener stopFragmentListener;

    public interface StopFragmentListener {
        void validateOnClicked (long idStop);
    }

    public StopFragment () {

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
        long id = getArguments().getLong("id");
        long sens = getArguments().getLong("sens");

        cursor = getContext().getContentResolver().
                query(Uri.parse("content://fr.istic.starproviderLD/stop"), null, Long.toString(id), null, Long.toString(sens));

        final StopAdapter adapter = new StopAdapter(getContext(),cursor);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stopFragmentListener.validateOnClicked(adapter.getItemId(i));
            }
        });
    }

    public static StopFragment newInstance (long id, long sens) {
        StopFragment stopFragment = new StopFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putLong("sens",sens);
        stopFragment.setArguments(args);
        return stopFragment;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        if (getActivity() instanceof StopFragment.StopFragmentListener) {
            stopFragmentListener = (StopFragment.StopFragmentListener) getActivity();
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        stopFragmentListener = null;
    }
}
