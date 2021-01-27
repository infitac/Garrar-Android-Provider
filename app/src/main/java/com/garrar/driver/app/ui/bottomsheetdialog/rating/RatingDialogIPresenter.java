package com.garrar.driver.app.ui.bottomsheetdialog.rating;

import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;

public interface RatingDialogIPresenter<V extends RatingDialogIView> extends MvpPresenter<V> {

    void rate(HashMap<String, Object> obj, Integer id);
}
