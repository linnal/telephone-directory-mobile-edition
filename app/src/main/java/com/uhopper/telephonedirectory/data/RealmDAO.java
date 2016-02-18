package com.uhopper.telephonedirectory.data;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by erinda on 2/16/16.
 */
public class RealmDAO {

    public static int saveContact(Realm realm, Contact contact){

        Number id = realm.where(contact.getClass()).max("id");

        // increment index
        int nextID = (id != null) ? id.intValue() + 1 : 0;

        contact.setId(nextID);

        realm.beginTransaction();
        realm.copyToRealm(contact);
        realm.commitTransaction();

        return nextID;
    }

    public static Contact getContactById(Realm realm, int id){
        RealmQuery<Contact> query = realm.where(Contact.class);
        return query.equalTo("id", id).findFirst();
    }

    public static void updateContact(Realm realm, Contact contact, String name, String surname, String phoneNumber){
        realm.beginTransaction();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setPhone(phoneNumber);
        realm.commitTransaction();
    }
}
