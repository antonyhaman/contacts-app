package com.github.kotvertolet.contactsapp.data.db.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.github.kotvertolet.contactsapp.data.pojo.ContactItem;
import com.github.kotvertolet.contactsapp.data.pojo.ContactStatus;

import lombok.Data;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Data
@Entity(tableName = "people_contacts", foreignKeys = @ForeignKey(entity = ContactGroupDto.class, parentColumns = "id", childColumns = "groupId", onDelete = CASCADE))
public class ContactDto {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(index = true)
    private long groupId;
    private String firstName;
    private String lastName;
    @TypeConverters({ContactStatusConverter.class})
    private ContactStatus statusIcon;
    private String statusMessage;

    public ContactDto(String firstName, String lastName, ContactStatus statusIcon, String statusMessage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.statusIcon = statusIcon;
        this.statusMessage = statusMessage;
    }

    public ContactItem toContactItem() {
        return new ContactItem(firstName, lastName, statusIcon, statusMessage);
    }
}
