package com.garrar.driver.app.ui.activity.setting;

import com.garrar.driver.app.base.MvpView;

public interface SettingsIView extends MvpView {

    void onSuccess(Object o);

    void onError(Throwable e);

}
