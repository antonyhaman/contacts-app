package com.github.kotvertolet.contactsapp;

import android.app.Application;

import com.github.kotvertolet.contactsapp.networking.WowApi;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class App extends Application {

    private static WowApi wowApi;
    private Retrofit retrofit;

    public static WowApi getWowApi() {
        return wowApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://file.wowapp.me")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        wowApi = retrofit.create(WowApi.class);
    }
}
