package com.github.kotvertolet.contactsapp.data.source.local;

import android.support.annotation.NonNull;

import com.github.kotvertolet.contactsapp.data.source.ContactsDataSource;

import org.apache.commons.lang3.NotImplementedException;

//TODO: Implement db layer
public class LocalContactsDataSource implements ContactsDataSource {

    @Override
    public void getContacts(@NonNull LoadContactsCallback callback) {
        throw new NotImplementedException(getClass().getSimpleName());
    }
}
