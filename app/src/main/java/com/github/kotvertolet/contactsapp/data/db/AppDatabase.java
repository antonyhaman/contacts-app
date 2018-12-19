package com.github.kotvertolet.contactsapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.github.kotvertolet.contactsapp.data.db.dao.GroupItemDao;
import com.github.kotvertolet.contactsapp.data.db.dao.PeopleItemDao;
import com.github.kotvertolet.contactsapp.data.db.dto.ContactDto;
import com.github.kotvertolet.contactsapp.data.db.dto.ContactGroupDto;

@Database(entities = {ContactGroupDto.class, ContactDto.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "Contacts.db")
                    .fallbackToDestructiveMigration().build();
        }
        return sInstance;
    }

    public abstract GroupItemDao groupItemDao();

    public abstract PeopleItemDao peopleItemDao();
}
