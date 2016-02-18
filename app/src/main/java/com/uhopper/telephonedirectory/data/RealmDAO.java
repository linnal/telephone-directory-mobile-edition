/*
 * Copyright 2016 Erinda Jaupaj
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
