package com.github.kotvertolet.contactsapp.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PeopleItem implements Parcelable {

    public static final Creator<PeopleItem> CREATOR = new Creator<PeopleItem>() {
        @Override
        public PeopleItem createFromParcel(Parcel in) {
            return new PeopleItem(in);
        }

        @Override
        public PeopleItem[] newArray(int size) {
            return new PeopleItem[size];
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

    protected PeopleItem(Parcel in) {
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
}