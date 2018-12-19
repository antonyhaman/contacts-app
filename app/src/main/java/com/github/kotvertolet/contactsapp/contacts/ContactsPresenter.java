package com.github.kotvertolet.contactsapp.contacts;

import android.support.annotation.NonNull;

import com.github.kotvertolet.contactsapp.data.pojo.ContactGroupItem;
import com.github.kotvertolet.contactsapp.data.source.ContactsDataSource;

import java.util.List;

public class ContactsPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;
    private ContactsDataSource mRemoteDataSource;
    private ContactsDataSource mLocalDataSource;

    public ContactsPresenter(@NonNull ContactsContract.View view, @NonNull ContactsDataSource remoteDataSource, @NonNull ContactsDataSource localDataSource) {
        mView = view;
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadContacts(false);
    }

    @Override
    public void loadContacts(final boolean forceUpdate) {
        mView.setLoadingIndicator(true);

        if (forceUpdate) {
            mRemoteDataSource.getContacts(new ContactsDataSource.LoadContactsCallback() {
                @Override
                public void onContactsLoaded(List<ContactGroupItem> contactGroupList) {
                    mView.setLoadingIndicator(false);
                    mView.showContacts(contactGroupList);
                    mLocalDataSource.saveContacts(contactGroupList);
                }

                @Override
                public void onDataNotAvailable() {
                    mView.setLoadingIndicator(false);
                    mView.showLoadingContactsError();
                }
            });
        } else {
            mLocalDataSource.getContacts(new ContactsDataSource.LoadContactsCallback() {
                @Override
                public void onContactsLoaded(List<ContactGroupItem> contactGroupList) {
                    mView.setLoadingIndicator(false);
                    mView.showContacts(contactGroupList);
                }

                @Override
                public void onDataNotAvailable() {
                    loadContacts(true);
                }
            });

        }
    }
}
