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
        complete_name.setText(contact.getName() + " " + contact.getSurname());
        phone.setText(contact.getPhone());
    }
}
