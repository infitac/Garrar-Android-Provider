package com.garrar.driver.app.ui.activity.reset_password;

import com.garrar.driver.app.base.MvpView;

public interface ResetIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
