package com.garrar.driver.app.ui.activity.splash;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.CheckVersion;

public interface SplashIView extends MvpView {

    void verifyAppInstalled();

    void onSuccess(Object user);

    void onSuccess(CheckVersion user);

    void onError(Throwable e);

    void onCheckVersionError(Throwable e);
}
