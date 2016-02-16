package com.uhopper.telephonedirectory.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.data.Contact;
import com.uhopper.telephonedirectory.data.RealmDAO;
import com.uhopper.telephonedirectory.interfaces.ContactDetailListener;
import com.uhopper.telephonedirectory.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by erinda on 2/16/16.
 */
public class ContactFormFragment extends Fragment {

    private Contact contact;
    private Realm realm;
    private ContactDetailListener detailListener = null;

    int id=-1;

    @Bind(R.id.contact_name) EditText contactName;
    @Bind(R.id.contact_surname) EditText contactSurname;
    @Bind(R.id.contact_phone) EditText contactPhone;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getInstance(this.getContext());

        if (getArguments().containsKey(Constants.ARG_ITEM_ID)) {
            id = getArguments().getInt(Constants.ARG_ITEM_ID);
        }

        if (getArguments().containsKey(Constants.ARG_TWO_PANE)) {
            detailListener = (ContactDetailListener) this.getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_form, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.button_save)
    public void onSave(){
        contact = new Contact();
        contact.setName(contactName.getText().toString());
        contact.setSurname(contactSurname.getText().toString());
        contact.setPhone(contactPhone.getText().toString());

        RealmDAO.saveContact(realm, contact);

        if(detailListener != null){
            detailListener.onUpdate(contact.getId());
        }else{
            this.getActivity().setResult(Constants.ARG_RESPONSE_CODE_FORM);
            this.getActivity().finish();
        }
    }

    @OnClick(R.id.button_rubrica)
    public void importFromRubrica(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Uri uriContact = data.getData();

        retriveContactName(uriContact);
        retrieveContactNumber(uriContact);
    }


    private void retriveContactName(Uri uriContact){
        Cursor cursor = this.getActivity().getContentResolver().query(uriContact, null, null, null, null);
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
        cursor.close();
        Log.d("DEBUG", contactName );
    }


    private void retrieveContactNumber( Uri uriContact) {
        String contactID = "";
        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = this.getActivity().getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d("DEBUG", "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = this.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d("DEBUG", "Contact Phone Number: " + contactNumber);
    }

}
