package com.apnatutorials.androidsqlitedemo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by Angel on 7/24/2016.
 */
public class CustomerCursorAdapter extends CursorAdapter {

    public CustomerCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate( R.layout.customer_row_update_delete, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Customer c = (Customer)SqliteHelper.getObjectFromCursor(cursor,Customer.class);
        TextView tvUpdateDeleteTitle =(TextView) view.findViewById(R.id.tvUpdateDeleteTitle);
        ImageButton ibDeleteCustomer =(ImageButton) view.findViewById(R.id.ibDeleteCustomer);
        ImageButton ibUpdateCustomer =(ImageButton) view.findViewById(R.id.ibUpdateCustomer);
        ibDeleteCustomer.setTag( "delete," + cursor.getPosition());
        ibUpdateCustomer.setTag("update," +cursor.getPosition());
        tvUpdateDeleteTitle.setText(c.getFirstName() + " " + c.getLastName());

       // super.bindView(view, context, cursor);
    }
}
