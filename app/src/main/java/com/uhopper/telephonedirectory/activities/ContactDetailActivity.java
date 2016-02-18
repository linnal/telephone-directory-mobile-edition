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
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.fragments.ContactDetailFragment;
import com.uhopper.telephonedirectory.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a single Contact detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * contact details are presented side-by-side with a list of contacts
 * in a {@link ContactListActivity}.
 */
public class ContactDetailActivity extends AppCompatActivity {

    int id = -1;

    ContactDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            id = getIntent().getIntExtra(Constants.ARG_ITEM_ID, 0);
            fragment = ContactDetailFragment.newInstance(id, false);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contact_detail_container, fragment)
                    .commit();
        }
    }

    @OnClick(R.id.button_edit)
    public void editContact(View view) {
        Intent intent = new Intent(this, ContactFormActivity.class);
        intent.putExtra(Constants.ARG_ITEM_ID, id);
        this.startActivityForResult(intent, Constants.ARG_REQUEST_CODE_FORM);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fragment.updateContent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ContactListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
