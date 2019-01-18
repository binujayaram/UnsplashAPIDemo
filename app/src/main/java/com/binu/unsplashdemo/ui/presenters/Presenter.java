package com.binu.unsplashdemo.ui.presenters;

import com.binu.unsplashdemo.ui.views.View;

/**
 * Created by nmillward on 3/24/17.
 */

public interface Presenter<T extends View> {

    void attachView(T view);

}
