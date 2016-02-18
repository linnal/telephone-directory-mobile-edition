package com.uhopper.telephonedirectory.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.activities.ContactDetailActivity;
import com.uhopper.telephonedirectory.activities.ContactListActivity;
import com.uhopper.telephonedirectory.data.Contact;
import com.uhopper.telephonedirectory.data.RealmDAO;
import com.uhopper.telephonedirectory.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
public class ContactDetailFragment extends Fragment {

    private int id = -1;
    private Realm realm;

    private ContactListActivity contactListActivity = null;

    @Bind(R.id.item_fullname) TextView itemFullname;
    @Bind(R.id.item_phone) TextView itemPhone;
    @Bind(R.id.button_edit) Button buttonEdit;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactDetailFragment() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id contact id.
     * @param mTwoPane true on large screens.
     * @return A new instance of fragment BlankFragment.
     */
    public static ContactDetailFragment newInstance(int id, boolean mTwoPane) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_ITEM_ID, id);
        args.putBoolean(Constants.ARG_TWO_PANE, mTwoPane);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constants.ARG_ITEM_ID)) {
            id = getArguments().getInt(Constants.ARG_ITEM_ID);
            realm = Realm.getInstance(this.getContext());
        }

        if (getArguments().getBoolean(Constants.ARG_TWO_PANE, false)) {
            contactListActivity = (ContactListActivity) this.getActivity();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_detail, container, false);
        ButterKnife.bind(this, rootView);

        if(id != -1) {
            updateContent();
        }

        // just for smartphones hide the edit button of the fragment
        if(contactListActivity == null){
            buttonEdit.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    @OnClick(R.id.button_edit)
    public void onEditClick(){
        if(contactListActivity != null){
            contactListActivity.showForm(id);
        }
    }

    public void updateContent(){
        Contact contact = RealmDAO.getContactById(realm, id);
        itemFullname.setText(contact.getName() + " " + contact.getSurname());
        itemPhone.setText(contact.getPhone());
    }
}
