package fr.istic.mob.starldv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fr.istic.mob.starldv2.adapter.SearchAdapter;
import fr.istic.mob.starldv2.fragment.BusFragment;
import fr.istic.mob.starldv2.fragment.StopFragment;
import android.database.Cursor;
import android.graphics.Bitmap;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager= this.getSupportFragmentManager();

        BusFragment busFragment = BusFragment.newInstance();
        replaceFragment(busFragment);
    }

    @Override
    public void validateOnClicked(long id, int sens) {
        StopFragment stopFragment = StopFragment.newInstance(id, sens);
        replaceFragment(stopFragment);
    }

    private void replaceFragment (Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame, fragment);
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
                SearchAdapter adapter = new SearchAdapter(getApplicationContext(), c);
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
