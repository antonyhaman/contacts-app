package com.github.kotvertolet.contactsapp.data.db.dto;

import android.arch.persistence.room.TypeConverter;

import com.github.kotvertolet.contactsapp.data.pojo.ContactStatus;

import org.apache.commons.lang3.StringUtils;

public class ContactStatusConverter {

    @TypeConverter
    public static ContactStatus fromStringToContactStatus(String status) {
        if (StringUtils.isEmpty(status)) {
            throw new RuntimeException(String.format("Type converter wasn't able to convert %s to ContactStatus", status));
        }
        return ContactStatus.fromString(status);
    }

    @TypeConverter
    public static String fromStringToContactStatus(ContactStatus category) {
        return category.toString();
    }
}
