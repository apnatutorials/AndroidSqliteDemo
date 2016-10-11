package com.apnatutorials.androidsqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CustomerDao {
    public static final String TABLE_NAME = "customer" ;
    public static final String COL_ID = "_id" ;
    public static final String COL_FIRST_NAME = "firstName" ;
    public static final String COL_LAST_NAME = "lastName" ;
    private static SqliteHelper healper = null ;
    private static String TAG ="CustomerDao" ;
    /**
     * I dont want that anyone can create an instance of CustomerDAo except SqliteHelper
     */
    private CustomerDao() {
    }

    protected static CustomerDao getInstance(SqliteHelper helper){
        healper = helper;
        return new CustomerDao();
    }

    /**
     * This method called once version of SQLiteHelper get updated
     * @param db is writable database passed from SQLiteHelper
     */
    public static void createCustomerTable(SQLiteDatabase db){
        String createQuery = "Create Table " +
                TABLE_NAME + " ( " +
                COL_ID + " Integer Primary key AutoIncrement , " +
                COL_FIRST_NAME + " text not null," +
                COL_LAST_NAME + " text not null )" ;
        db.execSQL(createQuery);

    }

    /**
     * Method called from SqliteHelper in case of upgrade
     * @param db
     */
    public static void upgradeCustomerTable(SQLiteDatabase db){
        // write upgrade query if any
        String upgradeQuery = "" ;
        db.execSQL(upgradeQuery);
       // db.close();
    }

    /**
     * Method used to add customer in custoemr table
     * @param customer
     * @return
     */
    public ReturnMessage addCustomer(Customer customer){

        ReturnMessage rm = new ReturnMessage();
        ContentValues cv = new ContentValues();
        cv.put(COL_FIRST_NAME,customer.getFirstName());
        cv.put(COL_LAST_NAME,customer.getLastName());
        long i =healper.getWritableDatabase().insert(TABLE_NAME, null,cv) ;
        Log.v(TAG,"Row Id: " + i);
        if (i>0)
        {
            rm.message = healper.getContext().getString(R.string.customer_added_successfully)  ;
        }
        else
        {

            rm.success = false ;
            rm.message = healper.getContext().getString(R.string.customer_could_not_be_added)  ;
        }
        return rm ;
    }

    /**
     * Method used to update customer
     * @param customer
     * @return
     */
    public ReturnMessage updateCustomer(Customer customer){
        ReturnMessage rm = new ReturnMessage();
        ContentValues cv = new ContentValues();
        cv.put(COL_FIRST_NAME, customer.getFirstName());
        cv.put(COL_LAST_NAME, customer.getLastName());
        String whereClause = COL_ID + "=?" ;
        int i = healper.getWritableDatabase().update(TABLE_NAME ,cv, whereClause , new String[]{customer.getId()+""} );
        if(i>0){
            rm.message = healper.getContext().getString(R.string.customer_updated_successfully)  ;
        }
        else{
            rm.success = false ;
            rm.message = healper.getContext().getString(R.string.customer_couldnt_be_updated)  ;
        }
        return rm ;
    }

    /**
     * Method used to delete customer record
     * @param id
     * @return
     */
    public ReturnMessage deleteCustomer(int id){
        ReturnMessage rm = new ReturnMessage();
        int i = healper.getWritableDatabase().delete(TABLE_NAME,COL_ID + "=?" , new String[]{id+""});
        if(i>0){
            rm.message = healper.getContext().getString(R.string.customer_deleted_successfully)  ; ;
        }
        else{
            rm.success = false ;
            rm.message = healper.getContext().getString(R.string.customer_couldnt_be_deleted)  ;
        }
        return rm ;
    }

    public Cursor getCustomers(Customer customer){

        Cursor cursor = null ;
        String []columns = new String[]{COL_ID,COL_FIRST_NAME,COL_LAST_NAME} ;
        String selection = "" ;
        String selectionArg[] = null ;
        String orderBy =  COL_FIRST_NAME + "," +COL_LAST_NAME ;
        if( customer.getId() > 0 ){
            selection = COL_ID + "=?" ;
            selectionArg =new String[]{customer.getId()+""};
            cursor = healper.getReadableDatabase().query(TABLE_NAME, columns, selection, selectionArg, null,null,orderBy);

        }
        else if(!customer.getFirstName().equals("") && !customer.getLastName().equals("")){
            selection = COL_FIRST_NAME + " like ?" +
            " AND "+  COL_LAST_NAME + " like ?" ;
            selectionArg =new String[]{"%" + customer.getFirstName() + "%" , "%" + customer.getLastName() + "%"};

            cursor = healper.getReadableDatabase().query(TABLE_NAME, columns, selection, selectionArg, null,null,orderBy);
        }
        else if(!customer.getFirstName().equals("") && customer.getLastName().equals("")){
            selection = COL_FIRST_NAME + " like ?"   ;
            selectionArg =new String[]{"%" + customer.getFirstName() + "%"};
            cursor = healper.getReadableDatabase().query(TABLE_NAME, columns, selection, selectionArg, null,null,orderBy);
           // cursor = healper.getReadableDatabase().query(TABLE_NAME, columns, selection, selectionArg, null,null,orderBy);
        }
        else if(customer.getFirstName().equals("") && !customer.getLastName().equals("")){
            selection = COL_LAST_NAME + " like ?" ;
            selectionArg = new String[]{"%" + customer.getLastName() + "%"};

            cursor = healper.getReadableDatabase().query(TABLE_NAME, columns, selection, selectionArg, null,null,orderBy);
        }
        else if(customer.getFirstName().equals("") && customer.getLastName().equals("")){
           cursor = healper.getReadableDatabase().query(TABLE_NAME, columns, null, null, null,null,orderBy);
        }
        return cursor ;

    }
}
