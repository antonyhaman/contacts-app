package com.github.kotvertolet.contactsapp.contacts;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kotvertolet.contactsapp.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import org.apache.commons.text.WordUtils;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class ContactGroupViewHolder extends GroupViewHolder {

    private TextView mTextViewGroupTitle;
    private ImageView mImageViewArrow;

    public ContactGroupViewHolder(View itemView) {
        super(itemView);
        mTextViewGroupTitle = itemView.findViewById(R.id.text_group_title);
        mImageViewArrow = itemView.findViewById(R.id.image_chevron);
    }

    public void setGroupTitle(ExpandableGroup group) {
        mTextViewGroupTitle.setText(WordUtils.capitalize(group.getTitle()));
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(0, 90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(400);
        rotate.setFillAfter(true);
        mImageViewArrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(90, 0, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setFillAfter(true);
        mImageViewArrow.setAnimation(rotate);
    }
}
