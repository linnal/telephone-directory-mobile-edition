package com.uhopper.telephonedirectory.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.activities.ContactDetailActivity;
import com.uhopper.telephonedirectory.adapters.views.ContactItemView;
import com.uhopper.telephonedirectory.data.Contact;
import com.uhopper.telephonedirectory.fragments.ContactDetailFragment;
import com.uhopper.telephonedirectory.utils.Constants;

import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.Realm;

/**
 * Created by erinda on 2/15/16.
 */
public class RealmSearchViewAdapter extends RealmSearchAdapter<Contact, RealmSearchViewAdapter.ViewHolder> {


    private Realm realm;
    boolean mTwoPane;
    AppCompatActivity activity;

    public RealmSearchViewAdapter(
            AppCompatActivity activity,
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

        viewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            setFragment(contact.getId());
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ContactDetailActivity.class);
                            intent.putExtra(Constants.ARG_ITEM_ID, contact.getId());

                            context.startActivity(intent);
                        }


                    }
                }
        );
    }


    public class ViewHolder extends RealmSearchViewHolder {

        private ContactItemView contactItemView;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(ContactItemView contactItemView) {
            super(contactItemView);
            this.contactItemView = contactItemView;
        }
    }


    public void setFragment(int id){
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.ARG_ITEM_ID, id);
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setArguments(arguments);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.contact_detail_container, fragment)
                .commit();
    }
}
