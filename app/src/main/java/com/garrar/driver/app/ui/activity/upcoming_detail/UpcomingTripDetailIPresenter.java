package com.garrar.driver.app.ui.activity.upcoming_detail;


import com.garrar.driver.app.base.MvpPresenter;

public interface UpcomingTripDetailIPresenter<V extends UpcomingTripDetailIView> extends MvpPresenter<V> {

    void getUpcomingDetail(String request_id);

}
