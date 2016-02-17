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
import com.uhopper.telephonedirectory.interfaces.ContactDetailListener;
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

    private ContactDetailListener detailListener = null;

    @Bind(R.id.item_fullname) TextView itemFullname;
    @Bind(R.id.item_phone) TextView itemPhone;
    @Bind(R.id.button_edit) Button buttonEdit;

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
            realm = Realm.getInstance(this.getContext());
        }

        if (getArguments().containsKey(Constants.ARG_TWO_PANE)) {
            detailListener = (ContactDetailListener) this.getActivity();
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

        // just for smartphones
        if(detailListener == null){
            buttonEdit.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    @OnClick(R.id.button_edit)
    public void onEditClick(){
        if(detailListener != null){
            detailListener.showForm(id);
        }
    }

    public void updateContent(){
        Contact contact = RealmDAO.getContactById(realm, id);
        itemFullname.setText(contact.getName() + " " + contact.getSurname());
        itemPhone.setText(contact.getPhone());
    }
}
