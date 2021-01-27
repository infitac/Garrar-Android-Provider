package com.garrar.driver.app.ui.activity.earnings;


import com.garrar.driver.app.base.MvpPresenter;

public interface EarningsIPresenter<V extends EarningsIView> extends MvpPresenter<V> {

    void getEarnings();
}
