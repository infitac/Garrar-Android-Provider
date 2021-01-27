package com.garrar.driver.app.ui.fragment.past;


import com.garrar.driver.app.base.MvpPresenter;

public interface PastTripIPresenter<V extends PastTripIView> extends MvpPresenter<V> {

    void getHistory();

}
