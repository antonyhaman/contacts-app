package com.github.kotvertolet.contactsapp.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.kotvertolet.contactsapp.App;
import com.github.kotvertolet.contactsapp.data.pojo.ContactsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsRepository implements ContactsDataSource {

    private static ContactsRepository sInstance;

    private ContactsRepository() {
    }

    public static synchronized ContactsRepository getInstance() {
        if (sInstance == null) {
            sInstance = new ContactsRepository();
        }
        return sInstance;
    }

    @Override
    public void getContacts(@NonNull final LoadContactsCallback callback) {
        App.getWowApi().listContacts().enqueue(new Callback<ContactsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ContactsResponse> call, @NonNull Response<ContactsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onContactsLoaded(response.body());
                } else {
                    //TODO: Loading cached result
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ContactsResponse> call, Throwable t) {
                Log.d(getClass().getSimpleName(), t.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }
}
