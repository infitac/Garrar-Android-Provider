package com.garrar.driver.app.ui.activity.change_password;

import com.garrar.driver.app.base.MvpView;

public interface ChangePasswordIView extends MvpView {


    void onSuccess(Object object);
    void onError(Throwable e);
}
