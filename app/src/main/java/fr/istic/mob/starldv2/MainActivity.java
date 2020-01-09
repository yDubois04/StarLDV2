package fr.istic.mob.starldv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BusFragment.BusFragmentListener, StopFragment.StopFragmentListener, StopTimesFragment.StopTimesFragmentListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean search;
    private SearchView searchView;
    private long busRouteId;
    private int sensBus;
    private Calendar calendar;
    private BusFragment busFragment;
    private StopFragment stopFragment;
    private StopTimesFragment stopTimesFragment;
    private RouteDetailsFragment routeDetailsFragment;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = false;
        isTablet = getResources().getBoolean(R.bool.isTablet);
        //isTablet = true;

        fragmentManager= this.getSupportFragmentManager();
        busFragment = BusFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, busFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void validateOnClicked(long id, int sens, Calendar chooseDate) {
        this.busRouteId = id;
        this.sensBus = sens;
        this.calendar = chooseDate;
        stopFragment = StopFragment.newInstance(id,sens);
        replaceFragment(stopFragment);
    }

    @Override
    public void validateOnClicked(int id, String schedule) {
        routeDetailsFragment = RouteDetailsFragment.newInstance(schedule,id);
        replaceFragment(routeDetailsFragment);
    }


    private void replaceFragment (Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_left_entrer,R.anim.anim_exit_left,R.anim.right_enter,R.anim.right_exit);

        if (!isTablet) {
            fragmentTransaction.replace(R.id.frame, fragment);
        } else {
            if (fragment instanceof StopFragment) {
                fragmentTransaction.replace(R.id.frame2, fragment);
            } else if (fragment instanceof StopTimesFragment) {
                fragmentTransaction.replace(R.id.frame, fragment);
            } else if (fragment instanceof RouteDetailsFragment) {
                fragmentTransaction.replace(R.id.frame2, fragment);
            }
            else if (fragment instanceof BusFragment) {
                fragmentTransaction.replace(R.id.frame, fragment);
            }
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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

                final Cursor c = getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/search"),null,query.trim(),null,null);
                final SearchAdapter adapter = new SearchAdapter(getApplicationContext(), c);
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
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }
            else {
               BusFragment busFragment = BusFragment.newInstance();
               fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.add(R.id.frame,busFragment);
               fragmentTransaction.commit();
            }
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void validateOnClicked(long idStop) {
        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        String hour = getHours(calendar);

        stopTimesFragment = StopTimesFragment.newInstance(idStop, this.busRouteId, this.sensBus, day, hour);
        replaceFragment(stopTimesFragment);
    }

    private String getHours (Calendar calendar) {
        int intH = calendar.get(Calendar.HOUR_OF_DAY);
        String h;
        if(intH < 10){
            h = "0" + intH;
        }else{
            h = Integer.toString(intH);
        }

        int intM = calendar.get(Calendar.MINUTE);
        String m;
        if(intM < 10){
            m = "0" + intM;
        }else{
            m = Integer.toString(intM);
        }

        String hour = h+":"+m+":00";
        return hour;
    }
}
