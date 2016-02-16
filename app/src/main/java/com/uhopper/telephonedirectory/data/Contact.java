package com.uhopper.telephonedirectory.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by erinda on 2/15/16.
 */

public class Contact extends RealmObject{

    public Contact(){

    }

    @PrimaryKey
    private int id;

    @Required
    private  String name;

    @Required
    private  String surname;

    @Required
    private  String phone;


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
