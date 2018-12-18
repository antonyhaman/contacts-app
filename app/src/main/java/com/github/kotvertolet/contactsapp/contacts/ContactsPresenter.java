package com.github.kotvertolet.contactsapp.contacts;

import android.support.annotation.NonNull;

import com.github.kotvertolet.contactsapp.data.pojo.ContactsResponse;
import com.github.kotvertolet.contactsapp.data.source.ContactsDataSource;
import com.github.kotvertolet.contactsapp.data.source.ContactsRepository;

public class ContactsPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;
    private ContactsRepository mContactRepository;

    public ContactsPresenter(@NonNull ContactsContract.View view, @NonNull ContactsRepository contactsRepository) {
        mView = view;
        mContactRepository = contactsRepository;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks(false);
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        mView.setLoadingIndicator(true);

        if (forceUpdate) {
            //TODO: Refresh cache
        }

        mContactRepository.getContacts(new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(ContactsResponse contactsResponse) {
                mView.setLoadingIndicator(false);
                mView.showContacts(contactsResponse.getGroups());
            }

            @Override
            public void onDataNotAvailable() {
                mView.showLoadingContactsError();
            }
        });
    }
}
