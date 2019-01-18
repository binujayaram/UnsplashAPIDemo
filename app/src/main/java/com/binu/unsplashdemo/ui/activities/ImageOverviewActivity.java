package com.binu.unsplashdemo.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.binu.unsplashdemo.R;
import com.binu.unsplashdemo.api.models.PhotoResponse;
import com.binu.unsplashdemo.ui.adapters.ImageAdapter;
import com.binu.unsplashdemo.ui.presenters.ImageOverviewPresenter;
import com.binu.unsplashdemo.ui.views.ImageOverviewView;
import com.binu.unsplashdemo.utils.InfiniteScrollListener;
import com.binu.unsplashdemo.utils.ItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageOverviewActivity extends AppCompatActivity implements ImageOverviewView {

    public static final String TAG = ImageOverviewActivity.class.getSimpleName();
    private static final int COLUMNS = 2;

    @BindView(R.id.cl_overview) CoordinatorLayout cl_overview;
    @BindView(R.id.rv_main_image_overview) RecyclerView rv_main_image_overview;
    //@BindView(R.id.image_detail_iv) RecyclerView image_detail_iv;


    private ImageOverviewPresenter presenter;
    private GridLayoutManager gridLayoutManager;
    private ImageAdapter adapter;
    private InfiniteScrollListener infiniteScrollListener;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_overview);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();
        setupPresenter();

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupAdapter() {
        adapter = new ImageAdapter(this, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.cardviewItemClicked(view, position);
            }
        });
    }

    private void setupPresenter() {
        presenter = new ImageOverviewPresenter();
        presenter.attachView(this);
        presenter.subscribe();
        presenter.updatePhotos(1);  //TODO: make this dynamic
    }

    private void setupRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this, COLUMNS);

        // TODO: Handle orientation change -- landscape layout

        rv_main_image_overview.hasFixedSize();
        rv_main_image_overview.setLayoutManager(gridLayoutManager);

        infiniteScrollListener = new InfiniteScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.updatePhotos(page);
            }
        };
        rv_main_image_overview.addOnScrollListener(infiniteScrollListener);
        Log.d(TAG, "--> set layout manager");
    }

    @Override
    public void showImageList() {
        rv_main_image_overview.setAdapter(adapter);
        Log.d(TAG, "--> show image list");
    }

    @Override
    public void showAddedImages(List<PhotoResponse> images) {
        adapter.addPhotos(images);
        Log.d(TAG, "--> show added images");
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "--> Loading...");
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "--> ...done loading");
    }

    @Override
    public void showError(int errorMessage) {
        Log.d(TAG, "--> Error");
        Snackbar snackbar = Snackbar.make(cl_overview, errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.updatePhotos(1);
                    }
                });
        snackbar.getView().setBackgroundResource(R.color.red_error_message);
        snackbar.setActionTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }

    @Override
    public void navigateToDetailScreen(View view, int position) {
  /*      Intent intent = new Intent(this, ImageDetailActivity.class);
        List<PhotoResponse> photoList = adapter.getPhotos();
        intent.putExtra(ImageDetailActivity.KEY_USER_NAME, photoList.get(position).getUser().getName());
        intent.putExtra(ImageDetailActivity.KEY_USER_LOCATION, photoList.get(position).getUser().getLocation());
        intent.putExtra(ImageDetailActivity.KEY_PHOTO_URL, photoList.get(position).getUrls().getImage_regular());
        Log.d(TAG, "send username details --> " + photoList.get(position).getUser().getName());
        Log.d(TAG, "send url details --> " + photoList.get(position).getUrls().getImage_regular());
        startActivity(intent);
      *//*  Animation zoomInAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        Animation zoomOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);*//*
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);*/


     /*   List<PhotoResponse> photoList = adapter.getPhotos();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.image_detail_view, null);
        dialogBuilder.setView(dialogView);
        ImageView iv_detail_hero_image = (ImageView)dialogView.findViewById(R.id.iv_detail_hero_image);
        // set the custom dialog components - text, image and button
        Glide.with(this)
                .load( photoList.get(position).getUrls().getImage_regular())
                .centerCrop()
                .into(iv_detail_hero_image);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();*/
        List<PhotoResponse> photoList = adapter.getPhotos();
        if(expandedImageView!=null && expandedImageView.getVisibility() == View.VISIBLE){
            expandedImageView.performClick();
        }
        zoomImageFromThumb(view, photoList.get(position).getUrls().getImage_regular());

    }

    @Override
    public void onBackPressed() {
        if(expandedImageView!=null && expandedImageView.getVisibility() == View.VISIBLE){
            expandedImageView.performClick();
        }
        else
            finish();
    }
    ImageView expandedImageView;
    private void zoomImageFromThumb(final View thumbView, String url/*, int imageResId*/) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.

     //   expandedImageView.setImageResource(imageResId);

        expandedImageView = (ImageView)findViewById(R.id.image_detail_iv) ;
        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.content_image_overview).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        //modify the final rect to match actual size of the image
        int imageHeight = expandedImageView.getHeight();
        int imageWidth = expandedImageView.getWidth();
        int horizontalMargin = 0,  verticalMargin = 0;
        if (imageHeight < finalBounds.height()) {
            verticalMargin = (finalBounds.height() / 2) - (imageHeight / 2);
        }
        if (imageWidth < finalBounds.width()) {
            horizontalMargin = (finalBounds.width() / 2) - (imageWidth / 2);
        }
        finalBounds.set(finalBounds.left + horizontalMargin, finalBounds.top + verticalMargin, finalBounds.right - horizontalMargin, finalBounds.bottom - verticalMargin);


        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}
