package com.garrar.driver.app.ui.fragment.upcoming;


import com.garrar.driver.app.base.MvpPresenter;

public interface UpcomingTripIPresenter<V extends UpcomingTripIView> extends MvpPresenter<V> {

    void getUpcoming();

}
