package com.uhopper.telephonedirectory.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.adapters.RealmSearchViewAdapter;

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
public class ContactListActivity extends AppCompatActivity {

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


        if (findViewById(R.id.item_detail_container) != null) {
            // If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true;
        }

        realm = Realm.getInstance(this);
        adapter = new RealmSearchViewAdapter(this, realm, "fullName", mTwoPane);
        searchView.setAdapter(adapter);

    }

    @OnClick(R.id.button_add)
    public void addNewContact(View view) {
        Snackbar.make(view, "TODO add new contact", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }



}
