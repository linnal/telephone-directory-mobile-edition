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


package com.uhopper.telephonedirectory.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.uhopper.telephonedirectory.activities.ContactListActivity;
import com.uhopper.telephonedirectory.adapters.views.ContactItemView;
import com.uhopper.telephonedirectory.data.Contact;

import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by erinda on 2/15/16.
 */
public class RealmSearchViewAdapter extends RealmSearchAdapter<Contact, RealmSearchViewAdapter.ViewHolder> {


    private Realm realm;
    boolean mTwoPane;
    ContactListActivity activity;

    public RealmSearchViewAdapter(
            ContactListActivity activity,
            Realm realm,
            String filterColumnName,
            boolean mTwoPane) {
        super(activity, realm, filterColumnName);

        this.realm = realm;
        this.mTwoPane = mTwoPane;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int position) {
        ViewHolder vh = new ViewHolder(new ContactItemView(viewGroup.getContext()));
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final Contact contact = realmResults.get(position);
        viewHolder.contactItemView.bind(contact);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showDetail(contact.getId());
            }
        });
    }

    @Override
    public void filter(String input){
        RealmResults<Contact> businesses;
        RealmQuery<Contact> where = realm.where(clazz);
        if (input.isEmpty()) {
            businesses = where.findAllSorted("name", Sort.DESCENDING);
        }else{
            businesses = where.contains("name", input, Case.INSENSITIVE)
                    .or()
                    .contains("surname", input, Case.INSENSITIVE)
                    .or()
                    .contains("phone", input, Case.INSENSITIVE)
                    .findAll();
        }

        updateRealmResults(businesses);
    }

    public class ViewHolder extends RealmSearchViewHolder {

        private ContactItemView contactItemView;

        public ViewHolder(ContactItemView contactItemView) {
            super(contactItemView);
            this.contactItemView = contactItemView;
        }
    }






}
