package com.uhopper.telephonedirectory.adapters;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.uhopper.telephonedirectory.data.Contact;

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
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {

    }


    public class ViewHolder extends RealmSearchViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
