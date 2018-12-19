package com.github.kotvertolet.contactsapp.data.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kotvertolet.contactsapp.custom.CustomExpandableGroup;
import com.github.kotvertolet.contactsapp.data.db.dto.ContactDto;
import com.github.kotvertolet.contactsapp.data.db.dto.ContactGroupDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactGroupItem extends CustomExpandableGroup implements Serializable {

    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("people")
    private List<ContactItem> people;

    @JsonCreator
    public ContactGroupItem(@JsonProperty("groupName") String title, @JsonProperty("people") List<ContactItem> items) {
        super(title, items);
        groupName = title;
        people = items;
    }

    public ContactGroupDto toContactGroupDto() {
        ContactGroupDto contactGroupDto = new ContactGroupDto();
        contactGroupDto.setGroupName(groupName);
        List<ContactDto> contactDtos = new ArrayList<>();
        for (ContactItem ci : people) {
            contactDtos.add(ci.toContactDto());
        }
        contactGroupDto.setPeople(contactDtos);
        return contactGroupDto;
    }
}