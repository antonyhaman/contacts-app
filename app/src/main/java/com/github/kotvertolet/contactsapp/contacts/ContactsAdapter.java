package com.github.kotvertolet.contactsapp.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.github.kotvertolet.contactsapp.R;
import com.github.kotvertolet.contactsapp.custom.CustomExpandableRecyclerViewAdapter;
import com.github.kotvertolet.contactsapp.data.pojo.ContactGroupItem;
import com.github.kotvertolet.contactsapp.data.pojo.ContactItem;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactsAdapter extends CustomExpandableRecyclerViewAdapter<ContactGroupViewHolder, ContactViewHolder> implements Filterable {

    private List<ContactGroupItem> mCachedGroups;

    public ContactsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
        mCachedGroups = (List<ContactGroupItem>) groups;
    }

    @Override
    public ContactGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ContactGroupViewHolder(view);
    }

    @Override
    public ContactViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ContactViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        ContactItem contactItem = ((ContactGroupItem) group).getPeople().get(childIndex);
        holder.onBind(contactItem);
    }

    @Override
    public void onBindGroupViewHolder(ContactGroupViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupTitle(group);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase();
                List<ContactGroupItem> contactListFiltered;
                if (charString.isEmpty()) {
                    contactListFiltered = mCachedGroups;
                } else {
                    List<ContactGroupItem> filteredList = new ArrayList<>();
                    List<ContactItem> tempList;
                    for (ContactGroupItem group : mCachedGroups) {

                        tempList = new ArrayList<>();
                        for (ContactItem people : group.getPeople()) {
                            if (people.getFirstName().toLowerCase().contains(charString)
                                    || people.getLastName().toLowerCase().contains(charString)) {
                                tempList.add(people);
                            }
                        }
                        if (tempList.size() > 0) {
                            filteredList.add(new ContactGroupItem(group.getTitle(), new ArrayList<>(tempList)));
                            tempList.clear();
                        }
                    }
                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                replaceData((List<ContactGroupItem>) results.values, false);
            }
        };
    }

    private void expandAll() {
        for (int i = 0; i < getItemCount(); i++) {
            if (!isGroupExpanded(i)) {
                toggleGroup(i);
            }
        }
    }

    public void replaceData(List<ContactGroupItem> groupsItems, boolean replaceCache) {
        if (replaceCache) {
            mCachedGroups = groupsItems;
        }
        getGroups().clear();
        ((List<ContactGroupItem>) getGroups()).addAll(groupsItems);
        notifyGroupDataChanged();
        notifyDataSetChanged();
        expandAll();
    }
}
