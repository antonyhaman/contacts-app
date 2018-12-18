package com.github.kotvertolet.contactsapp.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.github.kotvertolet.contactsapp.R;
import com.github.kotvertolet.contactsapp.custom.CustomExpandableRecyclerViewAdapter;
import com.github.kotvertolet.contactsapp.data.pojo.GroupsItem;
import com.github.kotvertolet.contactsapp.data.pojo.PeopleItem;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactsAdapter extends CustomExpandableRecyclerViewAdapter<ContactGroupViewHolder, ContactViewHolder> implements Filterable {

    private List<GroupsItem> mCachedGroups;

    public ContactsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
        mCachedGroups = (List<GroupsItem>) groups;
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
        PeopleItem peopleItem = ((GroupsItem) group).getPeople().get(childIndex);
        holder.onBind(peopleItem);
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
                List<GroupsItem> contactListFiltered;
                if (charString.isEmpty()) {
                    contactListFiltered = mCachedGroups;
                } else {
                    List<GroupsItem> filteredList = new ArrayList<>();
                    List<PeopleItem> list;
                    for (GroupsItem group : mCachedGroups) {

                        list = new ArrayList<>();
                        for (PeopleItem people : group.getPeople()) {
                            if (people.getFirstName().toLowerCase().contains(charString)
                                    || people.getLastName().toLowerCase().contains(charString)) {
                                list.add(people);
                            }
                        }
                        if (list.size() > 0) {
                            List<PeopleItem> tempList = new ArrayList<>(list);
                            filteredList.add(new GroupsItem(group.getTitle(), tempList));
                            list.clear();
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
                replaceData1((List<GroupsItem>) results.values);
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

    public void replaceData(List<GroupsItem> groupsItems) {
        mCachedGroups = groupsItems;
        getGroups().clear();
        ((List<GroupsItem>) getGroups()).addAll(groupsItems);
        notifyGroupDataChanged();
        notifyDataSetChanged();
        expandAll();
    }

    public void replaceData1(List<GroupsItem> groupsItems) {
        getGroups().clear();
        ((List<GroupsItem>) getGroups()).addAll(groupsItems);
        notifyGroupDataChanged();
        notifyDataSetChanged();
        expandAll();
    }
}
