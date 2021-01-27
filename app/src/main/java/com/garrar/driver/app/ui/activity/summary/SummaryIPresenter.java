package com.garrar.driver.app.ui.activity.summary;


import com.garrar.driver.app.base.MvpPresenter;

public interface SummaryIPresenter<V extends SummaryIView> extends MvpPresenter<V> {

    void getSummary();
}
