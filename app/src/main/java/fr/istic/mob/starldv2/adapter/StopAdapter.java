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

public class StopAdapter extends CursorAdapter {

    Cursor cursor;

    public StopAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
    }

    @Override
    public Stop getItem(int i) {
        cursor.moveToFirst();
        cursor.move(i);

        Stop stop = new Stop(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getDouble(3), cursor.getLong(4), cursor.getInt(5));

        return stop;
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
        TextView tvStop = (TextView) view;
        String stopName = cursor.getString(cursor.getColumnIndexOrThrow("stop_name"));
        tvStop.setText(stopName);
    }
}
