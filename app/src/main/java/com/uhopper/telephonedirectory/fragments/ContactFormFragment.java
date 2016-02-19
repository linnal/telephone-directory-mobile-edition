/*
 * Copyright 2016 Erinda Jaupaj
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.uhopper.telephonedirectory.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.activities.ContactListActivity;
import com.uhopper.telephonedirectory.data.Contact;
import com.uhopper.telephonedirectory.data.RealmDAO;
import com.uhopper.telephonedirectory.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private ContactListActivity contactListActivity = null;

    @Bind(R.id.contact_name)    EditText contactName;
    @Bind(R.id.contact_surname) EditText contactSurname;
    @Bind(R.id.contact_phone)   EditText contactPhone;
    @Bind(R.id.button_save)     Button buttonSave;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFormFragment() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id contact id.
     * @param mTwoPane true on large screens.
     * @return A new instance of fragment BlankFragment.
     */
    public static ContactFormFragment newInstance(int id, boolean mTwoPane) {
        ContactFormFragment fragment = new ContactFormFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_ITEM_ID, id);
        args.putBoolean(Constants.ARG_TWO_PANE, mTwoPane);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getInstance(this.getContext());

        if (getArguments().containsKey(Constants.ARG_ITEM_ID)) {
            int id = getArguments().getInt(Constants.ARG_ITEM_ID);
            if(id != -1) {
                contact = RealmDAO.getContactById(realm, id);
            }
        }

        if (getArguments().getBoolean(Constants.ARG_TWO_PANE, false)) {
            contactListActivity = (ContactListActivity) this.getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_form, container, false);
        ButterKnife.bind(this, rootView);

        if(contact != null) {
            contactName.setText(contact.getName());
            contactSurname.setText(contact.getSurname());
            contactPhone.setText(contact.getPhone());

            buttonSave.setText(R.string.update);
        }

        return rootView;
    }


    public boolean validateFields(){
        String name     = contactName.getText().toString().trim();
        String surname  = contactSurname.getText().toString().trim();
        String number   = contactPhone.getText().toString().trim();


        if(name.isEmpty()){
            Toast.makeText(this.getContext(), "Name not valid", Toast.LENGTH_SHORT).show();
            return false;
        }else if(surname.isEmpty()){
            Toast.makeText(this.getContext(), "Surname not valid", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            String numberExpression = "\\+\\d+ \\d+ \\d{6,}";
            Pattern pattern = Pattern.compile(numberExpression);
            Matcher matcher = pattern.matcher(number);
            if(!matcher.matches()){
                Toast.makeText(this.getContext(), "Phone number format not valid", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    @OnClick(R.id.button_save)
    public void onSave(){

        if(validateFields()) {
            if (contact != null) {
                RealmDAO.updateContact(realm, contact,  contactName.getText().toString(),
                                                        contactSurname.getText().toString(),
                                                        contactPhone.getText().toString());
            } else {
                contact = new Contact(  contactName.getText().toString(),
                                        contactSurname.getText().toString(),
                                        contactPhone.getText().toString());

                RealmDAO.saveContact(realm, contact);
            }

            Toast.makeText(this.getActivity(), "New contact added", Toast.LENGTH_LONG).show();

            if (contactListActivity != null) {
                contactListActivity.showDetail(contact.getId());
            } else {
                this.getActivity().setResult(Constants.ARG_RESPONSE_CODE_FORM);
                this.getActivity().finish();
            }

        }
    }

    @OnClick(R.id.button_rubrica)
    public void importFromRubrica(){

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this.getContext(), "No permission granted for this functionality", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(data != null && data.getData() != null) {

            Uri uriContact = data.getData();

            retrieveContactName(uriContact);
            retrieveContactNumber(uriContact);
        }
    }


    private void retrieveContactName(Uri uriContact){
        Cursor cursor = this.getActivity().getContentResolver().query(uriContact, null, null, null, null);
        String displayName = null;
        if (cursor.moveToFirst()) {
            displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
        cursor.close();

        String[] arr = displayName.split(" ");
        if(arr.length > 1){
            contactName.setText(arr[0]);
            contactSurname.setText(arr[1]);
        }else{
            contactName.setText(arr[0]);
        }

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

        if(contactNumber != null){
            contactPhone.setText(contactNumber);
        }
    }

}
