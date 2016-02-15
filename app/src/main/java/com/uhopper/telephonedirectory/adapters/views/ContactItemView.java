package com.uhopper.telephonedirectory.adapters.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uhopper.telephonedirectory.R;
import com.uhopper.telephonedirectory.data.Contact;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by erinda on 2/15/16.
 */
public class ContactItemView extends LinearLayout {


    @Bind(R.id.complete_name)
    TextView complete_name;


    @Bind(R.id.phone)
    TextView phone;

    public ContactItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.contact_list_row, this);
        ButterKnife.bind(this);
    }

    public void bind(Contact contact) {
        complete_name.setText(contact.getFullName());
        phone.setText(contact.getPhone());
    }
}
