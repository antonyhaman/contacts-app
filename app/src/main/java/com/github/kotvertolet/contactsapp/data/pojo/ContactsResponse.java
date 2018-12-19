package com.github.kotvertolet.contactsapp.data.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

@Data
public class ContactsResponse {

    @JsonProperty("groups")
    private List<ContactGroupItem> groups;
}