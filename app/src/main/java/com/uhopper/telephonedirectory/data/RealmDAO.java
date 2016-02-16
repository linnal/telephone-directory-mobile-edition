package com.uhopper.telephonedirectory.data;

import io.realm.Realm;

/**
 * Created by erinda on 2/16/16.
 */
public class RealmDAO {

    public static int saveContact(Realm realm, Contact contact){

        Number id = realm.where(contact.getClass()).max("id");
        int nextID = 0;
        // increatement index
        if(id != null){
            nextID =  id.intValue() + 1;
        }

        contact.setId(nextID);

        realm.beginTransaction();
        realm.copyToRealm(contact);
        realm.commitTransaction();

        return nextID;
    }
}