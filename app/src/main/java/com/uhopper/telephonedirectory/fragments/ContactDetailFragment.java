package com.uhopper.telephonedirectory.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uhopper.telephonedirectory.activities.ContactDetailActivity;
import com.uhopper.telephonedirectory.activities.ContactListActivity;
import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.data.Contact;
import com.uhopper.telephonedirectory.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
public class ContactDetailFragment extends Fragment {

    private Contact contact;

    int id=-1;

    @Bind(R.id.item_fullname) TextView item_fullname;
    @Bind(R.id.item_phone) TextView item_phone;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constants.ARG_ITEM_ID)) {
            id = getArguments().getInt(Constants.ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_detail, container, false);
        ButterKnife.bind(this, rootView);
        item_fullname.setText("ID: " + id);
        return rootView;
    }
}
