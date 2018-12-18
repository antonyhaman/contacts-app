package com.github.kotvertolet.contactsapp.networking;

import com.github.kotvertolet.contactsapp.data.pojo.ContactsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WowApi {

    @GET("owncloud/index.php/s/F5WttwCODi1z3oo/download?path=%2F&files=contacts.json")
    Call<ContactsResponse> listContacts();
}
