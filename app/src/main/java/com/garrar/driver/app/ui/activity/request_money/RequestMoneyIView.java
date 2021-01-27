package com.garrar.driver.app.ui.activity.request_money;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.RequestDataResponse;

public interface RequestMoneyIView extends MvpView {

    void onSuccess(RequestDataResponse response);
    void onSuccess(Object response);
    void onError(Throwable e);

}
