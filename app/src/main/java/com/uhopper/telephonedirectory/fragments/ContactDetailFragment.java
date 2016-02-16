package com.uhopper.telephonedirectory.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.activities.ContactDetailActivity;
import com.uhopper.telephonedirectory.activities.ContactListActivity;
import com.uhopper.telephonedirectory.data.Contact;
import com.uhopper.telephonedirectory.data.RealmDAO;
import com.uhopper.telephonedirectory.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
public class ContactDetailFragment extends Fragment {

    private Contact contact;


    @Bind(R.id.item_fullname) TextView itemFullname;
    @Bind(R.id.item_phone) TextView itemPhone;

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
            int id = getArguments().getInt(Constants.ARG_ITEM_ID);

            Realm realm = Realm.getInstance(this.getContext());
            contact = RealmDAO.getContactById(realm, id);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_detail, container, false);
        ButterKnife.bind(this, rootView);
        if(contact != null) {
            itemFullname.setText(contact.getName() + " " + contact.getSurname());
            itemPhone.setText(contact.getPhone());
        }
        return rootView;
    }
}
