package com.binu.unsplashdemo.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binu.unsplashdemo.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;

/**
 * Created by $(USER) on $(DATE).
 */
public class ImageDetailDialogFragment  extends DialogFragment{
    @BindView(R.id.iv_detail_hero_image) ImageView iv_detail_hero_image;
    private String photoUrl_regular;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Bundle bundle=getArguments();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Set a theme on the dialog builder constructor!
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.image_detail_view);

        // set the custom dialog components - text, image and button
        Glide.with(this)
                .load(photoUrl_regular)
                .centerCrop()
                .into(iv_detail_hero_image);

       // dialog.show();
        return dialog;
    }
}
