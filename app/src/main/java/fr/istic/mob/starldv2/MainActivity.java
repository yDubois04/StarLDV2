package fr.istic.mob.starldv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fr.istic.mob.starldv2.fragment.BusFragment;
import fr.istic.mob.starldv2.fragment.StopFragment;
import fr.istic.mob.starldv2.model.BusRoute;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity implements BusFragment.BusFragmentListener {

    private FragmentManager fragmentManager;
    private BusFragment busFragment;
    private StopFragment stopFragment;
    private FragmentTransaction fragmentTransaction;

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
        fragmentTransaction.show(busFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void validateOnClicked() {
        System.out.println("dans le listener");
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(busFragment);
        fragmentTransaction.show(stopFragment);
        fragmentTransaction.commit();
    }
}
