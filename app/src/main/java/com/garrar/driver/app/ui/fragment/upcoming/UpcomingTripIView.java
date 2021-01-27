package com.garrar.driver.app.ui.fragment.upcoming;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.HistoryList;

import java.util.List;

public interface UpcomingTripIView extends MvpView {

    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
