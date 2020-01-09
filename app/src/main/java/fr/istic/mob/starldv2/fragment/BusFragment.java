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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import androidx.fragment.app.Fragment;
import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.adapter.SpinnerAdapter;

public class BusFragment extends Fragment {

    private TextView tvHour;
    private TextView tvDate;
    private Calendar calendar;
    private Spinner spinnerBus;
    private Spinner spinnerSens;
    private BusFragmentListener fragmentListener;
    private Button validateButton;
    private long idBus;
    private int sens;
    private SpinnerAdapter adapter;
    private Calendar chooseDate;

    public interface BusFragmentListener {
        void validateOnClicked (long id, int sens, Calendar chooseDate);
    }

    public static BusFragment newInstance () {
        return new BusFragment();
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bus, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvHour = view.findViewById(R.id.textViewHour);
        tvDate = view.findViewById(R.id.textViewDate);
        validateButton = view.findViewById(R.id.buttonValidate);
        calendar = Calendar.getInstance();

        //Initializes spinners
        spinnerBus = view.findViewById(R.id.busSpinner);
        spinnerSens = view.findViewById(R.id.sensSpinner);
        chooseDate = Calendar.getInstance();

        this.initializeSpinners();

        //Initializes Text View
        tvHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        tvHour.setText(getStringForInt(hour) + " : "+getStringForInt(minutes));
                        chooseDate.set(Calendar.HOUR_OF_DAY,hour);
                        chooseDate.set(Calendar.MINUTE, minutes);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        chooseDate.set(Calendar.YEAR, year);
                        chooseDate.set(Calendar.MONTH, month);
                        chooseDate.set(Calendar.DAY_OF_MONTH, day);
                        month = month+1;
                        tvDate.setText(getStringForInt(day)+ "/" + getStringForInt(month) + "/"+getStringForInt(year));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentListener != null) {
                    if (!tvDate.getText().equals("")&& !tvHour.getText().equals("")) {
                        if (chooseDate.after(calendar)) {
                            fragmentListener.validateOnClicked(idBus, sens,chooseDate);
                        }
                        else {
                            Toast.makeText(getContext(),getString(R.string.toast_validate_date),Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), getString(R.string.toast_validate_not_null), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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


    private void initializeSpinners() {

        Cursor cursor = getContext().getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/busroute"), null, null, null, null);
        adapter = new SpinnerAdapter(getContext(),cursor);
        spinnerBus.setAdapter(adapter);
        idBus = adapter.getItemId(0);
        sens = 1;

        spinnerBus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> sens = getSensBus(i);
                idBus = adapter.getItemId(i);
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
                if (i == 1) {
                    sens = 0;
                } else {
                    sens = 1;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private String getStringForInt (int i) {
        String ret;

        if (i < 10) {
            ret = "0"+i;
        }
        else {
            ret = Integer.toString(i);
        }

        return ret;
    }

    private ArrayList<String> getSensBus (int i) {
        ArrayList<String> ret = new ArrayList<>();

        String longName = adapter.getItem(i).getLongName();
        if (longName.contains("->")) {
            String [] columns = longName.split("->");
            ret.add(columns[0]);
            ret.add(columns[columns.length-1]);
        }
        else {
            String [] columns = longName.split("<>");
            ret.add(columns[0]);
            ret.add(columns[columns.length-1]);
        }

        return ret;
    }
}
