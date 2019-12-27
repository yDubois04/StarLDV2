package fr.istic.mob.starldv2.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.SpinnerAdapter;
import fr.istic.mob.starldv2.model.BusRoute;

public class BusFragment extends Fragment {

    private TextView chooseHour;
    private TextView chooseDate;
    private Calendar calendar;
    private Spinner spinnerBus;
    private Spinner spinnerSens;
    private BusFragmentListener fragmentListener;
    private Button validateButton;
    private int idBus;
    private int sens;

    public interface BusFragmentListener {
        void validateOnClicked (int id, int sens);
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus, container,false);

        chooseHour = view.findViewById(R.id.textViewHour);
        chooseDate = view.findViewById(R.id.textViewDate);
        validateButton = view.findViewById(R.id.buttonValidate);
        calendar = GregorianCalendar.getInstance();

        //Initializes spinners
        spinnerBus = view.findViewById(R.id.busSpinner);
        spinnerSens = view.findViewById(R.id.sensSpinner);

        this.initializeSpinners();

        //Initializes Text View
        chooseHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        chooseHour.setText(hour + " : "+minutes);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentListener != null) {
                    fragmentListener.validateOnClicked(idBus, sens);
                }
            }
        });

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int annee, int mois, int jour) {
                        chooseDate.setText(jour + " " + mois+ " "+annee);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        return view;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        if (getActivity() instanceof BusFragmentListener) {
            fragmentListener = (BusFragmentListener) getActivity();
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        fragmentListener = null;
    }


    private void initializeSpinners(){

        Cursor cursor = getContext().getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/busroute"),null,null,null,null);
        final ArrayList<BusRoute> buses = convertCursorToArrayList(cursor);
        SpinnerAdapter adapter = new SpinnerAdapter(buses, getContext());
        spinnerBus.setAdapter(adapter);
        idBus = buses.get(0).getId();
        sens = 0;

        spinnerBus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> sens = getSensBus(buses, buses.get(i).getShortName());
                idBus = buses.get(i).getId();
                ArrayAdapter<String> listSensAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, sens);
                spinnerSens.setAdapter(listSensAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerSens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sens = i;
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
