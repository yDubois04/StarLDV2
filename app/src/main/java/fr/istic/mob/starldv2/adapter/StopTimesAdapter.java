package fr.istic.mob.starldv2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.model.StopTime;

public class StopTimesAdapter extends CursorAdapter {

    Cursor cursor;

    public StopTimesAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
    }

    @Override
    public StopTime getItem(int i) {
        cursor.moveToFirst();
        cursor.move(i);

        StopTime stopTime = new StopTime(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                cursor.getString(3), cursor.getInt(4), cursor.getInt(5));

        return stopTime;
    }

    @Override
    public long getItemId(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        return cursor.getLong(0);
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvSchedule = (TextView) view;
        String arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow("arrival_time"));
        int hourOfArrival = Integer.parseInt(arrivalTime.substring(0,2));
        int minuteOfArrival = Integer.parseInt(arrivalTime.substring(3,5));
        tvSchedule.setText(arrivalTime);
        if(hourOfArrival > 23) {
            hourOfArrival = hourOfArrival % 24;
        }
        arrivalTime = getStringOfHour(hourOfArrival) + ":"+getStringOfHour(minuteOfArrival)+":00";
        tvSchedule.setText(arrivalTime);
    }

    /**
     * Add 0 of an hour or minutes if it less than 10
     * @param hour
     * @return
     */
    private String getStringOfHour (int hour) {
        String h;
        if(hour < 10){
            h = "0" + hour;
        }else{
            h = Integer.toString(hour);
        }
        return h;
    }

}
