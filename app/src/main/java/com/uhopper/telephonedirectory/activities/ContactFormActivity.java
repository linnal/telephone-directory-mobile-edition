package com.uhopper.telephonedirectory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.fragments.ContactFormFragment;
import com.uhopper.telephonedirectory.utils.Constants;

/**
 * Created by erinda on 2/16/16.
 */
public class ContactFormActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


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
            int id = getIntent().getIntExtra(Constants.ARG_ITEM_ID, 0);
            ContactFormFragment fragment = ContactFormFragment.newInstance(id, false);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contact_form_container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ContactDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
