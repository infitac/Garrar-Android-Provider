package com.garrar.driver.app.ui.activity.profile;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.UserResponse;

public interface ProfileIView extends MvpView {

    void onSuccess(UserResponse user);

    void onSuccessUpdate(UserResponse object);

    void onError(Throwable e);

    void onSuccessPhoneNumber(Object object);

    void onVerifyPhoneNumberError(Throwable e);

}
