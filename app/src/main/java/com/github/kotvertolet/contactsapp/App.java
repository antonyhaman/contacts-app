package com.github.kotvertolet.contactsapp;

import android.app.Application;

import com.github.kotvertolet.contactsapp.data.db.AppDatabase;
import com.github.kotvertolet.contactsapp.networking.WowApi;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class App extends Application {

    private static final String BASE_URL = "https://file.wowapp.me";
    private static volatile AppDatabase database;
    private static volatile WowApi wowApi;

    public static WowApi getWowApi() {
        return wowApi;
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = AppDatabase.getInstance(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        wowApi = retrofit.create(WowApi.class);
    }
}
