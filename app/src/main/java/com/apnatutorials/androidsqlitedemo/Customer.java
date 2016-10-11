package com.apnatutorials.androidsqlitedemo;

/**
 * Created by Angel on 7/23/2016.
 */
public class Customer {
    private int id = -1 ;
    private String firstName ="" ;
    private String lastName ="" ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
