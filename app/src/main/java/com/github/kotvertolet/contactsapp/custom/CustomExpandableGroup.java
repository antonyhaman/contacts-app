package com.github.kotvertolet.contactsapp.custom;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CustomExpandableGroup<T extends Parcelable> extends ExpandableGroup implements Parcelable {

    private String title;
    private List<T> items;

    public CustomExpandableGroup(String title, List<T> items) {
        super(title, items);
        this.title = title;
        this.items = items;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (items == null) {
            dest.writeByte((byte) (0x00));
            dest.writeInt(0);
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(items.size());
            if (items.size() > 0) {
                final Class<?> objectsType = items.get(0).getClass();
                dest.writeSerializable(objectsType);
            }
            dest.writeList(items);
        }
    }
}
