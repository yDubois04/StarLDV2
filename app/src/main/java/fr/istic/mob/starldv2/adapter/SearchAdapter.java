package fr.istic.mob.starldv2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.core.content.PermissionChecker;
import fr.istic.mob.starldv2.R;

public class SearchAdapter extends CursorAdapter {

    Cursor cursor;
    Context context;

    public SearchAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
        this.context = context;
    }


    @Override
    public long getItemId (int i) {
        cursor.moveToFirst();
        cursor.move(i);
        return  cursor.getInt(0);
    }

    public int getIdStop (int i) {
        cursor.moveToFirst();
        cursor.move(i);
        String stopName = cursor.getString(cursor.getColumnIndexOrThrow("stop_name"));
        Cursor c = context.getContentResolver().query(Uri.parse("content://fr.istic.starproviderLD/stopId"),null,stopName,null,null);
        c.moveToFirst();

        return c.getInt(0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvBus = view.findViewById(R.id.textView1);
        TextView tvStop = view.findViewById(R.id.textView2);

        String stopName = cursor.getString(cursor.getColumnIndexOrThrow("stop_name"));
        String busName = cursor.getString(1);

        int color = Color.parseColor("#"+cursor.getString(5));
        int colorTxt = Color.parseColor("#"+cursor.getString(6));

        tvStop.setBackgroundColor(color);
        tvBus.setBackgroundColor(color);

        tvStop.setText(stopName);
        tvBus.setText(busName);

        tvBus.setTextColor(colorTxt);
        tvStop.setTextColor(colorTxt);
    }
}
