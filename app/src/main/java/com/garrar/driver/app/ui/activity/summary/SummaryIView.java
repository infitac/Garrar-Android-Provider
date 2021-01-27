package com.garrar.driver.app.ui.activity.summary;


import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.Summary;

public interface SummaryIView extends MvpView {

    void onSuccess(Summary object);

    void onError(Throwable e);
}
