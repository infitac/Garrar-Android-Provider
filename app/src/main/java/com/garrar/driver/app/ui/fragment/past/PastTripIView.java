package com.garrar.driver.app.ui.fragment.past;


import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.HistoryList;

import java.util.List;

public interface PastTripIView extends MvpView {

    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
