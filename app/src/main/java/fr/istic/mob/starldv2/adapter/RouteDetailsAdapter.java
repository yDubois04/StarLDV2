package fr.istic.mob.starldv2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fr.istic.mob.starldv2.R;

public class RouteDetailsAdapter extends CursorAdapter {

    public RouteDetailsAdapter (Context context, Cursor cursor) {
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv2 = view.findViewById(R.id.textView2);
        TextView tv1 = view.findViewById(R.id.textView1);

        String stopName = cursor.getString(1);
        String schedule = cursor.getString(2);

        tv2.setTextColor(Color.RED);
        tv1.setTextColor(Color.BLACK);

        tv2.setText(stopName);
        tv1.setText(schedule);
    }
}
