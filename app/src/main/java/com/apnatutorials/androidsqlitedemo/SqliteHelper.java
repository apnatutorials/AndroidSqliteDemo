package com.apnatutorials.androidsqlitedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Angel on 7/23/2016.
 */
public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "customerdb.db" ;
    public static final int VERSION = 1 ;
    private static CustomerDao customerDao = null;
    private Context context ;
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            CustomerDao.createCustomerTable( sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Method return an object of CustomerDAO
     * @return
     */
    public CustomerDao getCustomerDao(){
        return CustomerDao.getInstance(this);
    }

    /**
     * Method used to process cursor and return corresponding object
     * @param cursor
     * @param cls
     * @return
     */
    public static Object getObjectFromCursor(Cursor cursor, Object cls ){
        Object returnObject = null;
        if(cls.getClass().isInstance(Customer.class)){
            if (cursor != null && cursor.getCount() > 0){
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex(CustomerDao.COL_ID)));
                customer.setFirstName(cursor.getString(cursor.getColumnIndex(CustomerDao.COL_FIRST_NAME)));
                customer.setLastName(cursor.getString(cursor.getColumnIndex(CustomerDao.COL_LAST_NAME)));
                returnObject = customer ;
            }
        }
        return returnObject;
    }

    public Context getContext(){
        return this.context ;
    }

}
