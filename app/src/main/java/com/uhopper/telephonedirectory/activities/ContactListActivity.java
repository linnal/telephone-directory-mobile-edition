package com.uhopper.telephonedirectory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.adapters.RealmSearchViewAdapter;
import com.uhopper.telephonedirectory.fragments.ContactFormFragment;
import com.uhopper.telephonedirectory.interfaces.ContactDetailListener;
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
public class ContactListActivity extends AppCompatActivity implements ContactDetailListener{

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

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(Constants.ARG_ITEM_ID, -1);
            arguments.putInt(Constants.ARG_TWO_PANE, 1);
            ContactFormFragment fragment = new ContactFormFragment();
            fragment.setArguments(arguments);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ContactFormActivity.class);
            intent.putExtra(Constants.ARG_ITEM_ID, -1);
            this.startActivityForResult(intent, Constants.ARG_REQUEST_CODE_FORM);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyItemInserted(adapter.getItemCount());
    }

    @Override
    public void onUpdate(int id) {
        Toast.makeText(this, "New contact added", Toast.LENGTH_LONG).show();

        if(adapter != null){
            adapter.notifyItemInserted(adapter.getItemCount());
        }

        adapter.setFragment(id);
    }
}
