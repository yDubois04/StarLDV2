package fr.istic.mob.starldv2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.starldv2.R;
import fr.istic.mob.starldv2.model.BusRoute;

public class SpinnerAdapter extends CursorAdapter {

    Cursor cursor;

    public SpinnerAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public BusRoute getItem(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        BusRoute busRoute = new BusRoute(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3),cursor.getInt(4), cursor.getString(5),cursor.getString(6));

        return busRoute;
    }

    @Override
    public long getItemId(int i) {
        cursor.moveToFirst();
        cursor.move(i);
        return cursor.getLong(0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int color = Color.parseColor("#"+cursor.getString(5));
        view.setBackgroundColor(color);

        TextView textView = (TextView) view;
        textView.setText(cursor.getString(1));

        color = Color.parseColor("#"+cursor.getString(6));
        textView.setTextColor(color);
    }
}
