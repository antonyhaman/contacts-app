package com.github.kotvertolet.contactsapp.data.source.local;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.github.kotvertolet.contactsapp.App;
import com.github.kotvertolet.contactsapp.data.db.AppDatabase;
import com.github.kotvertolet.contactsapp.data.db.dao.GroupItemDao;
import com.github.kotvertolet.contactsapp.data.db.dao.PeopleItemDao;
import com.github.kotvertolet.contactsapp.data.db.dto.ContactDto;
import com.github.kotvertolet.contactsapp.data.db.dto.ContactGroupDto;
import com.github.kotvertolet.contactsapp.data.db.dto.GroupWithPeople;
import com.github.kotvertolet.contactsapp.data.pojo.ContactGroupItem;
import com.github.kotvertolet.contactsapp.data.source.ContactsDataSource;

import java.util.ArrayList;
import java.util.List;

public class LocalContactsDataSource implements ContactsDataSource {

    private static volatile LocalContactsDataSource sInstance;
    private GroupItemDao mGroupItemDao;
    private PeopleItemDao mPeopleItemDao;
    private AppDatabase mDatabase;

    private LocalContactsDataSource() {
        mDatabase = App.getDatabase();
        mGroupItemDao = mDatabase.groupItemDao();
        mPeopleItemDao = mDatabase.peopleItemDao();
    }

    public static synchronized LocalContactsDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new LocalContactsDataSource();
        }
        return sInstance;
    }

    @Override
    public void getContacts(@NonNull final LoadContactsCallback callback) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<GroupWithPeople> groupWithPeopleList = mDatabase.groupItemDao().getGroupWithPeople();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (groupWithPeopleList.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            List<ContactGroupItem> convertedGroups = new ArrayList<>();
                            for (GroupWithPeople gwp : groupWithPeopleList) {
                                ContactGroupItem contactGroupItem = gwp.toContactGroupItem();
                                convertedGroups.add(contactGroupItem);
                            }
                            callback.onContactsLoaded(convertedGroups);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void saveContacts(final List<ContactGroupItem> contactGroupItems) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mGroupItemDao.deleteAll();
                for (ContactGroupItem cgi : contactGroupItems) {
                    ContactGroupDto contactGroupDto = cgi.toContactGroupDto();
                    long groupId = mGroupItemDao.insert(contactGroupDto);
                    for (ContactDto cd : contactGroupDto.getPeople()) {
                        cd.setGroupId(groupId);
                    }
                    mPeopleItemDao.insertAll(contactGroupDto.getPeople());
                }
            }
        });
    }
}
