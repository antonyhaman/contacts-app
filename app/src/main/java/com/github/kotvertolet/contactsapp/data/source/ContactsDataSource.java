package com.github.kotvertolet.contactsapp.data.source;

import android.support.annotation.NonNull;

import com.github.kotvertolet.contactsapp.data.pojo.ContactGroupItem;

import java.util.List;

public interface ContactsDataSource {

    void getContacts(@NonNull LoadContactsCallback callback);

    void saveContacts(List<ContactGroupItem> contactGroupItems);

    interface LoadContactsCallback {

        void onContactsLoaded(List<ContactGroupItem> contactGroupList);

        void onDataNotAvailable();
    }


}
