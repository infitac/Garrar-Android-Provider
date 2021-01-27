package com.garrar.driver.app.ui.activity.past_detail;


import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.HistoryDetail;

public interface PastTripDetailIView extends MvpView {

    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
