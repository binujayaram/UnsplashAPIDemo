package com.binu.unsplashdemo.ui.presenters;

import com.binu.unsplashdemo.ui.views.ImageDetailView;

/**
 * Created by nmillward on 3/28/17.
 */

public class ImageDetailPresenter implements Presenter<ImageDetailView> {

    private ImageDetailView view;

    @Override
    public void attachView(ImageDetailView view) {
        this.view = view;
    }
}
