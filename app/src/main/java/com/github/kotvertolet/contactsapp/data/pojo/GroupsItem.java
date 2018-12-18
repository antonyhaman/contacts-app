package com.github.kotvertolet.contactsapp.data.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kotvertolet.contactsapp.custom.CustomExpandableGroup;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupsItem extends CustomExpandableGroup implements Serializable {

    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("people")
    private List<PeopleItem> people;

    @JsonCreator
    public GroupsItem(@JsonProperty("groupName") String title, @JsonProperty("people") List<PeopleItem> items) {
        super(title, items);
        groupName = title;
        people = items;
    }
}