package com.garrar.driver.app.ui.activity.regsiter;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.SettingsResponse;
import com.garrar.driver.app.data.network.model.User;

public interface RegisterIView extends MvpView {

    void onSuccess(User user);

    void onSuccess(Object verifyEmail);

    void onSuccess(SettingsResponse response);

    void onError(Throwable e);

    void onSuccessPhoneNumber(Object object);

    void onVerifyPhoneNumberError(Throwable e);

    void onVerifyEmailError(Throwable e);

}
