package com.github.kotvertolet.contactsapp.data.db.dto;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.github.kotvertolet.contactsapp.data.pojo.ContactGroupItem;
import com.github.kotvertolet.contactsapp.data.pojo.ContactItem;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = "contacts_groups")
public class ContactGroupDto {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String groupName;
    @Ignore
    private List<ContactDto> people;

    public ContactGroupItem toContactGroupItem() {
        List<ContactItem> convertedContactItems = new ArrayList<>();
        if (people != null && !people.isEmpty()) {
            for (ContactDto contactDto : people) {
                convertedContactItems.add(contactDto.toContactItem());
            }
        }
        return new ContactGroupItem(groupName, convertedContactItems);
    }
}
