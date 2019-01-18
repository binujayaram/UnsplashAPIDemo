package com.binu.unsplashdemo.ui.views;

import com.binu.unsplashdemo.api.models.PhotoResponse;

import java.util.List;

/**
 * Created by nmillward on 3/24/17.
 */

public interface ImageOverviewView extends View {
    void showImageList();
    void showAddedImages(List<PhotoResponse> images);
    void showLoading();
    void hideLoading();
    void showError(int message);
    void navigateToDetailScreen(android.view.View view, int position);
}
