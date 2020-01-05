package fr.istic.mob.starldv2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.model.Stop;
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
        String schedule = cursor.getString(cursor.getColumnIndexOrThrow("arrival_time"));
        tvSchedule.setText(schedule);
    }
}