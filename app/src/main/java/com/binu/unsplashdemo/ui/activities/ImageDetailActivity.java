package com.binu.unsplashdemo.ui.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.binu.unsplashdemo.R;
import com.binu.unsplashdemo.ui.presenters.ImageDetailPresenter;
import com.binu.unsplashdemo.ui.views.ImageDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDetailActivity extends AppCompatActivity implements ImageDetailView {

    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_LOCATION = "userLocation";
    public static final String KEY_PHOTO_URL = "photoUrl";

    @BindView(R.id.iv_detail_hero_image) ImageView iv_detail_hero_image;
    @BindView(R.id.tv_detailview_username) TextView tv_detailview_username;
    @BindView(R.id.tv_detailview_location) TextView tv_detailview_location;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private ImageDetailPresenter presenter;

    private Window window;
    private String userName;
    private String userLocation;
    private String photoUrl_regular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);

        setupPresenter();
       // setupActionBar();
        setupIntentExtras();
        setupViewsWithExtras();
    }

    private void setupWindowStyle() {
        window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.trans_black));
    }

    private void setupIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString(KEY_USER_NAME);
            userLocation = extras.getString(KEY_USER_LOCATION);
            photoUrl_regular = extras.getString(KEY_PHOTO_URL);
        }
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupPresenter() {
        presenter = new ImageDetailPresenter();
        presenter.attachView(this);
    }

    private void setupViewsWithExtras() {
        tv_detailview_username.setText(userName);
        tv_detailview_location.setText(userLocation);
        Glide.with(this)
                .load(photoUrl_regular)
                .centerCrop()
                .into(iv_detail_hero_image);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}
