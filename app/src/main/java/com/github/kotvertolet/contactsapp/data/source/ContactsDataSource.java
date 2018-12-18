package com.github.kotvertolet.contactsapp.data.source;

import android.support.annotation.NonNull;

import com.github.kotvertolet.contactsapp.data.pojo.ContactsResponse;

public interface ContactsDataSource {

    void getContacts(@NonNull LoadContactsCallback callback);

    interface LoadContactsCallback {

        void onContactsLoaded(ContactsResponse contactGroupList);

        void onDataNotAvailable();
    }
}
