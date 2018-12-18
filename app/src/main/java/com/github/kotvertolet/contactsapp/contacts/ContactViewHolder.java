package com.github.kotvertolet.contactsapp.contacts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kotvertolet.contactsapp.R;
import com.github.kotvertolet.contactsapp.data.pojo.PeopleItem;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import org.apache.commons.text.WordUtils;

public class ContactViewHolder extends ChildViewHolder {

    private ImageView mImageViewStatusIcon;
    private TextView mTextViewContactTitle;
    private TextView mTextViewContactStatus;
    private ImageView mImageViewProfileIcon;

    public ContactViewHolder(View itemView) {
        super(itemView);
        mImageViewStatusIcon = itemView.findViewById(R.id.image_status_icon);
        mTextViewContactTitle = itemView.findViewById(R.id.text_contact_title);
        mTextViewContactStatus = itemView.findViewById(R.id.text_contact_status_title);
        mImageViewProfileIcon = itemView.findViewById(R.id.image_profile_icon);
    }

    public void onBind(PeopleItem peopleItem) {
        setStatusIcon(peopleItem);
        String contactTitle = String.format("%s %s", peopleItem.getFirstName(), peopleItem.getLastName());
        mTextViewContactTitle.setText(WordUtils.capitalizeFully(contactTitle));
        mTextViewContactStatus.setText(peopleItem.getStatusMessage());
        //TODO: mImageViewProfileIcon
    }

    private void setStatusIcon(PeopleItem peopleItem) {
        switch (peopleItem.getStatusIcon()) {
            case ONLINE:
                mImageViewStatusIcon.setImageResource(R.drawable.ic_contacts_list_status_online);
                break;
            case OFFLINE:
                mImageViewStatusIcon.setImageResource(R.drawable.ic_contacts_list_status_offline);
                break;
            case AWAY:
                mImageViewStatusIcon.setImageResource(R.drawable.ic_contacts_list_status_away);
                break;
            case BUSY:
                mImageViewStatusIcon.setImageResource(R.drawable.ic_contacts_list_status_busy);
                break;
            case CALL_FORWARDING:
                mImageViewStatusIcon.setImageResource(R.drawable.ic_contacts_list_call_forward);
                break;
            case PENDING:
                mImageViewStatusIcon.setImageResource(R.drawable.ic_contacts_list_status_pending);
                break;
        }
    }


}
