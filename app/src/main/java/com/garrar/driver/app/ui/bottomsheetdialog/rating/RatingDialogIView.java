package com.garrar.driver.app.ui.bottomsheetdialog.rating;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.Rating;

public interface RatingDialogIView extends MvpView {

    void onSuccess(Rating rating);
    void onError(Throwable e);
}
