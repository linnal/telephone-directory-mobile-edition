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
    private  String fullName;

    @Required
    private  String phone;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
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
