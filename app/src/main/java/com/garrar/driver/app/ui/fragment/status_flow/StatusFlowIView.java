package com.garrar.driver.app.ui.fragment.status_flow;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.TimerResponse;

public interface StatusFlowIView extends MvpView {

    void onSuccess(Object object);

    void onWaitingTimeSuccess(TimerResponse object);

    void onError(Throwable e);
}
