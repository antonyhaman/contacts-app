package com.github.kotvertolet.contactsapp.data.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public enum ContactStatus {

    @JsonProperty("online")
    ONLINE("online"),
    @JsonProperty("offline")
    OFFLINE("offline"),
    @JsonProperty("busy")
    BUSY("busy"),
    @JsonProperty("away")
    AWAY("away"),
    @JsonProperty("callforwarding")
    CALL_FORWARDING("callforwarding"),
    @JsonProperty("pending")
    PENDING("pending");

    private final String status;

    ContactStatus(final String status) {
        this.status = status;
    }

    public static ContactStatus fromString(String text) {
        for (ContactStatus cs : ContactStatus.values()) {
            if (cs.status.equalsIgnoreCase(text)) {
                return cs;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return status;
    }
}
