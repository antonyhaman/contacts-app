package com.github.kotvertolet.contactsapp.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.github.kotvertolet.contactsapp.data.db.dto.ContactGroupDto;
import com.github.kotvertolet.contactsapp.data.db.dto.GroupWithPeople;

import java.util.List;

@Dao
public interface GroupItemDao {

    @Query("SELECT * FROM contacts_groups WHERE id = :id")
    List<ContactGroupDto> getById(long id);

    @Query("SELECT * FROM contacts_groups")
    List<ContactGroupDto> getAll();

    @Insert
    long insert(ContactGroupDto contactGroupItem);

    @Update
    void update(ContactGroupDto contactGroupItem);

    @Delete
    void delete(ContactGroupDto contactGroupItem);

    @Transaction
    @Query("SELECT * FROM contacts_groups")
    List<GroupWithPeople> getGroupWithPeople();

    @Query("DELETE FROM contacts_groups")
    void deleteAll();
}
