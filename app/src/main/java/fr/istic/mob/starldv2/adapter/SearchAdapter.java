package fr.istic.mob.starldv2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.starldv2.R;

public class SearchAdapter extends CursorAdapter {

    public SearchAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvStop = (TextView) view;

        String stopName = cursor.getString(cursor.getColumnIndexOrThrow("stop_name"));
        String busName = cursor.getString(1);
        tvStop.setText(stopName +"  "+ busName);
    }
}
