package com.github.kotvertolet.contactsapp;

import android.app.Application;

import com.github.kotvertolet.contactsapp.networking.WowApi;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class App extends Application {

    private static final String BASE_URL = "https://file.wowapp.me";
    private static WowApi wowApi;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        wowApi = retrofit.create(WowApi.class);
    }

    public static WowApi getWowApi() {
        return wowApi;
    }
}
