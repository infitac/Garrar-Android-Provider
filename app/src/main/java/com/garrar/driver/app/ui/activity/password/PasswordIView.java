package com.garrar.driver.app.ui.activity.password;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.ForgotResponse;
import com.garrar.driver.app.data.network.model.User;

public interface PasswordIView extends MvpView {

    void onSuccess(ForgotResponse forgotResponse);

    void onSuccess(User object);

    void onError(Throwable e);
}
