package com.garrar.driver.app.ui.activity.past_detail;


import com.garrar.driver.app.base.MvpPresenter;

public interface PastTripDetailIPresenter<V extends PastTripDetailIView> extends MvpPresenter<V> {

    void getPastTripDetail(String request_id);
}
