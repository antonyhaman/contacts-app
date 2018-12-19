package com.github.kotvertolet.contactsapp.data.db.dto;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.github.kotvertolet.contactsapp.data.pojo.ContactGroupItem;
import com.github.kotvertolet.contactsapp.data.pojo.ContactItem;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupWithPeople {

    @Embedded
    private ContactGroupDto group;

    @Relation(parentColumn = "id", entityColumn = "groupId")
    private List<ContactDto> contactItems;

    public ContactGroupItem toContactGroupItem() {
        List<ContactItem> convertedContactItems = new ArrayList<>();
        if (contactItems != null && !contactItems.isEmpty()) {
            for (ContactDto contactDto : contactItems) {
                convertedContactItems.add(contactDto.toContactItem());
            }
        }
        return new ContactGroupItem(group.getGroupName(), convertedContactItems);
    }
}
