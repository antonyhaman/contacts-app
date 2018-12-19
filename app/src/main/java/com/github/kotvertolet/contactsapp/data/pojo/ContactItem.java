package com.github.kotvertolet.contactsapp.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kotvertolet.contactsapp.data.db.dto.ContactDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactItem implements Parcelable {

    public static final Creator<ContactItem> CREATOR = new Creator<ContactItem>() {
        @Override
        public ContactItem createFromParcel(Parcel in) {
            return new ContactItem(in);
        }

        @Override
        public ContactItem[] newArray(int size) {
            return new ContactItem[size];
        }
    };
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("statusIcon")
    private ContactStatus statusIcon;
    @JsonProperty("statusMessage")
    private String statusMessage;

    protected ContactItem(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        statusMessage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(statusMessage);
        dest.writeSerializable(statusIcon);
    }

    public ContactDto toContactDto() {
        return new ContactDto(firstName, lastName, statusIcon, statusMessage);
    }
}