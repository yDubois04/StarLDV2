package fr.istic.mob.starldv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import fr.istic.mob.starldv2.fragment.BusFragment;
import fr.istic.mob.starldv2.model.BusRoute;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BusFragment fragmentBus;
    private Spinner spinnerBus;
    private Spinner spinnerSens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerBus = findViewById(R.id.busSpinner);
        spinnerSens = findViewById(R.id.sensSpinner);
        this.initializeSpinners();

        fragmentManager = this.getSupportFragmentManager();
        fragmentBus = (BusFragment)fragmentManager.findFragmentById(R.id.fragmentBus);

    }

    private void initializeSpinners(){

        Cursor cursor = getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/busroute"),null,null,null,null);
        final ArrayList<BusRoute> buses = convertCursorToArrayList(cursor);
        SpinnerAdapter adapter = new SpinnerAdapter(buses, getApplicationContext());
        spinnerBus.setAdapter(adapter);

        spinnerBus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> sens = getSensBus(buses, buses.get(i).getShortName());
                ArrayAdapter<String> listSensAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, sens);
                spinnerSens.setAdapter(listSensAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private ArrayList<BusRoute> convertCursorToArrayList (Cursor cursor) {
        ArrayList<BusRoute> ret = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BusRoute bus = new BusRoute(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6));
            ret.add(bus);
            cursor.moveToNext();
        }
        cursor.close();
        return ret;
    }

    private ArrayList<String> getSensBus (ArrayList<BusRoute> buses, String shortName) {
        ArrayList<String> ret = new ArrayList<>();

        for (BusRoute bus : buses) {
            if (shortName.equals(bus.getShortName())) {

                String longName = bus.getLongName();
                String [] columns = longName.split("<>");

                ret.add(columns[0]);
                ret.add(columns[columns.length-1]);
            }
        }

        return ret;
    }
}
