package fr.istic.mob.starldv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fr.istic.mob.starldv2.fragment.BusFragment;
import fr.istic.mob.starldv2.fragment.StopFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BusFragment.BusFragmentListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BusFragment busFragment;
    private StopFragment stopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initializes fragments
        fragmentManager = this.getSupportFragmentManager();
        busFragment = (BusFragment)fragmentManager.findFragmentById(R.id.fragmentBus);
        stopFragment = (StopFragment) fragmentManager.findFragmentById(R.id.fragmentStop);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(stopFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void validateOnClicked(long id, int sens) {

        stopFragment.createList (id, sens);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(stopFragment);
        fragmentTransaction.hide(busFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setContentView(R.layout.search_view);

                ListView list = findViewById(R.id.search_list);

                Cursor c = getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/search"),null,query.trim(),null,null);
                ArrayList<String> results = convertCursorToArrayList(c);
                ArrayAdapter <String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,results);
                list.setAdapter(adapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setContentView(R.layout.activity_main);
                return false;
            }
        });

        return true;
    }

    private ArrayList<String> convertCursorToArrayList (Cursor cursor) {
        ArrayList<String> ret = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ret.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return ret;
    }
}
