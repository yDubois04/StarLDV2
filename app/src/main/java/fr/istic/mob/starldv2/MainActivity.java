package fr.istic.mob.starldv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fr.istic.mob.starldv2.adapter.RouteDetailsAdapter;
import fr.istic.mob.starldv2.adapter.SearchAdapter;
import fr.istic.mob.starldv2.fragment.BusFragment;
import fr.istic.mob.starldv2.fragment.RouteDetailsFragment;
import fr.istic.mob.starldv2.fragment.StopFragment;
import fr.istic.mob.starldv2.fragment.StopTimesFragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements BusFragment.BusFragmentListener, StopFragment.StopFragmentListener, StopTimesFragment.StopTimesFragmentListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean search;
    private SearchView searchView;
    private Fragment actualFragment;
    private long busRouteId;
    private int sens;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = false;

        fragmentManager= this.getSupportFragmentManager();
        BusFragment busFragment = BusFragment.newInstance();
        replaceFragment(busFragment);
    }


    private void replaceFragment (Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        actualFragment = fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search = true;

                setContentView(R.layout.search_view);

                ListView list = findViewById(R.id.search_list);

                Cursor c = getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/search"),null,query.trim(),null,null);
                SearchAdapter adapter = new SearchAdapter(getApplicationContext(), c);
                list.setAdapter(adapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (search) {
            searchView.onActionViewCollapsed();
            search = false;
            setContentView(R.layout.activity_main);
            if (actualFragment instanceof BusFragment) {
                BusFragment busFragment = BusFragment.newInstance();
                replaceFragment(busFragment);
            }
            else {
                fragmentManager.popBackStack();
            }
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void validateOnClicked(long id, int sens, Calendar chooseDate) {
        this.busRouteId = id;
        this.sens = sens;
        this.calendar = chooseDate;
        StopFragment stopFragment = StopFragment.newInstance(id,sens);
        replaceFragment(stopFragment);
    }

    @Override
    public void validateOnClicked(long idStop) {
        StopTimesFragment stopTimesFragment = StopTimesFragment.newInstance(idStop, this.busRouteId, this.sens);
        replaceFragment(stopTimesFragment);
    }

    @Override
    public void validateOnClicked(int id, String schedule) {
        RouteDetailsFragment routeDetailsFragment = RouteDetailsFragment.newInstance(schedule,id);
        replaceFragment(routeDetailsFragment);
    }
}
