package com.garrar.driver.app.ui.activity.forgot_password;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.ForgotResponse;

public interface ForgotIView extends MvpView {

    void onSuccess(ForgotResponse forgotResponse);
    void onError(Throwable e);
}
