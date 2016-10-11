package com.apnatutorials.androidsqlitedemo;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private ListView lvCustomer;
    private TextView tvMessage;

    SqliteHelper helper;
    CustomerCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        lvCustomer = (ListView) findViewById(R.id.lvCustomer);
        helper = new SqliteHelper(this);
        Cursor cursor = helper.getCustomerDao().getCustomers(new Customer());
        adapter = new CustomerCursorAdapter(this, cursor, true);
        lvCustomer.setAdapter(adapter);
        lvCustomer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void addCustomer(View view) {

        Customer customer = new Customer();
        customer.setId(-1);
        customer.setFirstName(etFirstName.getText().toString());
        customer.setLastName(etLastName.getText().toString());
        ReturnMessage rm = helper.getCustomerDao().addCustomer(customer);
        tvMessage.setText(rm.message);
        if (rm.success) {
            tvMessage.setTextColor(Color.BLUE);
            adapter.changeCursor(helper.getCustomerDao().getCustomers(new Customer()));
            resetForm();

        } else {
            tvMessage.setTextColor(Color.RED);
        }
    }

    private void resetForm() {
      //  lvCustomer.setSelected(false);
        lvCustomer.setItemChecked(-1, true);
        etFirstName.setText("");
        etLastName.setText("");
        etFirstName.requestFocus();
    }

    public void updateCustomer(View view) {
        //lvCustomer
        Toast.makeText(MainActivity.this, "Selected Position : "+ lvCustomer.getCheckedItemPosition() , Toast.LENGTH_SHORT).show();
        if (lvCustomer.getCheckedItemPosition() == -1) {
            tvMessage.setText(getString(R.string.select_a_customer_to_edit));
            tvMessage.setTextColor(Color.RED);
        } else {
            Customer customer = new Customer();
            Cursor c = (Cursor) adapter.getItem(lvCustomer.getCheckedItemPosition());
            Customer cust  = (Customer) SqliteHelper.getObjectFromCursor(c, Customer.class);
            customer.setId(cust.getId());
            customer.setFirstName(etFirstName.getText().toString());
            customer.setLastName(etLastName.getText().toString());
            ReturnMessage rm = helper.getCustomerDao().updateCustomer(customer);
            tvMessage.setText(rm.message);
            if (rm.success) {
                tvMessage.setTextColor(Color.BLUE);
                adapter.changeCursor(helper.getCustomerDao().getCustomers(new Customer()));
                resetForm();
            } else {
                tvMessage.setTextColor(Color.RED);
            }
        }
    }

    public void onClickOfUpdateDelete(View view) {

        ImageButton ib = (ImageButton) view;
        String[] temp = ib.getTag().toString().split(",");
        int position = Integer.parseInt(temp[1]);
        if (temp[0].equals("update")) {
            Cursor c = (Cursor) adapter.getItem(position);
            Customer customer = (Customer) SqliteHelper.getObjectFromCursor(c, Customer.class);
            Toast.makeText(this, customer.getFirstName() + " " + customer.getLastName(), Toast.LENGTH_SHORT).show();
            etFirstName.setText(customer.getFirstName());
            etLastName.setText(customer.getLastName());

            lvCustomer.setItemChecked(position, true);
            //lvCustomer.setSelected(true);
            //lvCustomer.setSe


        } else if (temp[0].equals("delete")) {
            Cursor c = (Cursor) adapter.getItem(position);
            Customer customer = (Customer) SqliteHelper.getObjectFromCursor(c, Customer.class);
            ReturnMessage rm = helper.getCustomerDao().deleteCustomer(customer.getId());
            tvMessage.setText(rm.message);
            if (rm.success) {
                tvMessage.setTextColor(Color.BLUE);
                Cursor cursor = helper.getCustomerDao().getCustomers(new Customer());
                adapter.changeCursor(cursor);
            } else {
                tvMessage.setTextColor(Color.RED);
            }

        }

    }

}
