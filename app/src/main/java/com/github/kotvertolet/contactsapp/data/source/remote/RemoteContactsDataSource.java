package com.github.kotvertolet.contactsapp.data.source.remote;

import android.support.annotation.NonNull;

import com.github.kotvertolet.contactsapp.App;
import com.github.kotvertolet.contactsapp.data.pojo.ContactsResponse;
import com.github.kotvertolet.contactsapp.data.source.ContactsDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteContactsDataSource implements ContactsDataSource {

    private static RemoteContactsDataSource sInstance;

    private RemoteContactsDataSource() {
    }

    public static synchronized RemoteContactsDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new RemoteContactsDataSource();
        }
        return sInstance;
    }

    @Override
    public void getContacts(@NonNull final LoadContactsCallback callback) {
        App.getWowApi().listContacts().enqueue(new Callback<ContactsResponse>() {
            @Override
            public void onResponse(Call<ContactsResponse> call, Response<ContactsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onContactsLoaded(response.body());
                } else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<ContactsResponse> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}
