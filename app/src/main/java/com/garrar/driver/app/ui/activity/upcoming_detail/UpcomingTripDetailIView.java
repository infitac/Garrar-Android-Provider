package com.garrar.driver.app.ui.activity.upcoming_detail;


import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.HistoryDetail;

public interface UpcomingTripDetailIView extends MvpView {

    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
