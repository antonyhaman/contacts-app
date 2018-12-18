package com.github.kotvertolet.contactsapp.data.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public enum ContactStatus {

    @JsonProperty("online")
    ONLINE,
    @JsonProperty("offline")
    OFFLINE,
    @JsonProperty("busy")
    BUSY,
    @JsonProperty("away")
    AWAY,
    @JsonProperty("callforwarding")
    CALL_FORWARDING,
    @JsonProperty("pending")
    PENDING

}
