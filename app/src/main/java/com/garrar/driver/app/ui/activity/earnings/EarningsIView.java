package com.garrar.driver.app.ui.activity.earnings;


import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.EarningsList;

public interface EarningsIView extends MvpView {

    void onSuccess(EarningsList earningsLists);

    void onError(Throwable e);
}
