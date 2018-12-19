package com.github.kotvertolet.contactsapp.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.github.kotvertolet.contactsapp.data.db.dto.ContactDto;

import java.util.List;

@Dao
public interface PeopleItemDao {

    @Query("SELECT * FROM people_contacts WHERE id = :id")
    List<ContactDto> getById(long id);

    @Query("SELECT * FROM people_contacts WHERE groupId = :id")
    List<ContactDto> getAllByGroupId(long id);

    @Query("SELECT * FROM people_contacts")
    List<ContactDto> getAll();

    @Insert
    long insert(ContactDto contactItem);

    @Insert
    long[] insertAll(List<ContactDto> contactItems);

    @Update
    void update(ContactDto contactItem);

    @Delete
    void delete(ContactDto contactItem);

    @Query("DELETE FROM people_contacts")
    void deleteAll();
}
