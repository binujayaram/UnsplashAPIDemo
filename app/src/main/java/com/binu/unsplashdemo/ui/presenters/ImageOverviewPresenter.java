package com.binu.unsplashdemo.ui.presenters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.binu.unsplashdemo.R;
import com.binu.unsplashdemo.api.UnsplashApi;
import com.binu.unsplashdemo.api.models.PhotoResponse;
import com.binu.unsplashdemo.ui.views.ImageOverviewView;
import com.bumptech.glide.Glide;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nmillward on 3/24/17.
 */

public class ImageOverviewPresenter implements Presenter<ImageOverviewView> {

    private static final int PER_PAGE_LIMIT = 30;

    private ImageOverviewView view;
    private Set<Call<List<PhotoResponse>>> photoCalls;

    @Override
    public void attachView(ImageOverviewView view) {
        this.view = view;
        this.photoCalls = new HashSet<>();
    }

    public void subscribe() {
        view.showImageList();
    }

    public void unsubscribe() {
        for (Call<List<PhotoResponse>> call : photoCalls) {
            if (call != null && call.isExecuted() && !call.isCanceled()) {
                call.cancel();
            }
        }
        photoCalls.clear();
    }

    public void updatePhotos(int page) {
        view.showLoading();

        final Call<List<PhotoResponse>> photoCall = UnsplashApi.getInstance()
                .getService()
                .getPhotos(page, PER_PAGE_LIMIT);

        photoCalls.add(photoCall);
        photoCall.enqueue(new Callback<List<PhotoResponse>>() {
            @Override
            public void onResponse(Call<List<PhotoResponse>> call, Response<List<PhotoResponse>> response) {

                if (response.isSuccessful()) {
                    List<PhotoResponse> photos = response.body();
                    Log.d("Presenter", "ResponseBody --> " + photos);
                    view.hideLoading();
                    view.showAddedImages(photos);
                }
            }

            @Override
            public void onFailure(Call<List<PhotoResponse>> call, Throwable t) {
                view.showError(R.string.uploadPhotoErrorMsg);
            }
        });
    }

    public void cardviewItemClicked(View v, int position) {
        view.navigateToDetailScreen(v, position);
    }

   /* public void showImageDetailDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.image_detail_view);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        Glide.with(this)
                .load(photoUrl_regular)
                .centerCrop()
                .into(iv_detail_hero_image);

        dialog.show();
    }*/

}
