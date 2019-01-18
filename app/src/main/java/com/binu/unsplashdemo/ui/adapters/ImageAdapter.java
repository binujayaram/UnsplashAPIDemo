package com.binu.unsplashdemo.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.binu.unsplashdemo.R;
import com.binu.unsplashdemo.api.models.PhotoResponse;
import com.binu.unsplashdemo.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nmillward on 3/24/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    ItemClickListener itemClickListener;

    private Context context;
    private List<PhotoResponse> photos = new ArrayList<>();

    public ImageAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_overview, parent, false);

        final ImageViewHolder imageViewHolder = new ImageViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, imageViewHolder.getAdapterPosition());
            }
        });

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        PhotoResponse photo = photos.get(position);

        holder.userName.setText(photo.getUser().getName());
        holder.userLocation.setText(photo.getUser().getLocation());

        Glide.with(context)
                .load(photo.getUrls().getImage_regular())
                .placeholder(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(holder.image);

        Log.d("Adapter", "user name #" + position + " : --> " + photo.getUser().getName());
        Log.d("Adapter", "user location #" + position + " : --> " + photo.getUser().getLocation());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addPhoto(PhotoResponse photo) {
        photos.add(photo);
        notifyItemChanged(getItemCount() - 1);
    }

    public void addPhotos(List<PhotoResponse> newPhotos) {
        photos.addAll(newPhotos);
        notifyDataSetChanged();
    }

    public void replaceAllPhotos(List<PhotoResponse> newPhotos) {
        photos.clear();
        photos.addAll(newPhotos);
        notifyDataSetChanged();
    }

    public List<PhotoResponse> getPhotos() {
        return photos;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_overview_item_image) ImageView image;
        @BindView(R.id.tv_overview_item_banner_username) TextView userName;
        @BindView(R.id.tv_overview_item_banner_location) TextView userLocation;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
