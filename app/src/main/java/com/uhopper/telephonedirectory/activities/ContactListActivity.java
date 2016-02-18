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

package com.uhopper.telephonedirectory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.adapters.RealmSearchViewAdapter;
import com.uhopper.telephonedirectory.fragments.ContactDetailFragment;
import com.uhopper.telephonedirectory.fragments.ContactFormFragment;
import com.uhopper.telephonedirectory.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;
import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import io.realm.Realm;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ContactListActivity extends AppCompatActivity{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RealmSearchViewAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RealmSearchView searchView = (RealmSearchView) findViewById(R.id.search_view);
        assert searchView != null;


        if (findViewById(R.id.contact_detail_container) != null) {
            // If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true;
        }

        realm = Realm.getInstance(this);
        adapter = new RealmSearchViewAdapter(this, realm, "name", mTwoPane);
        searchView.setAdapter(adapter);

    }

    @OnClick(R.id.button_add)
    public void addNewContact(View view) {
        showForm(-1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }




    public void showDetail(int id){
        if (mTwoPane) {
            adapter.notifyDataSetChanged();

            ContactDetailFragment fragment = ContactDetailFragment.newInstance(id, mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container, fragment)
                    .commit();
        }else{
            Intent intent = new Intent(this, ContactDetailActivity.class);
            intent.putExtra(Constants.ARG_ITEM_ID, id);

            startActivity(intent);
        }
    }

    public void showForm(int id){
        if (mTwoPane) {
            adapter.notifyDataSetChanged();

            ContactFormFragment fragment = ContactFormFragment.newInstance(id, mTwoPane);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ContactFormActivity.class);
            intent.putExtra(Constants.ARG_ITEM_ID, id);
            this.startActivityForResult(intent, Constants.ARG_REQUEST_CODE_FORM);
        }
    }

}
