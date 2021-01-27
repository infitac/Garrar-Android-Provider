package com.garrar.driver.app.ui.activity.main;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.SettingsResponse;
import com.garrar.driver.app.data.network.model.TripResponse;
import com.garrar.driver.app.data.network.model.UserResponse;

public interface MainIView extends MvpView {
    void onSuccess(UserResponse user);

    void onError(Throwable e);

    void onSuccessLogout(Object object);

    void onSuccess(TripResponse tripResponse);

    void onSuccess(SettingsResponse response);

    void onSettingError(Throwable e);

    void onSuccessProviderAvailable(Object object);

    void onSuccessFCM(Object object);

    void onSuccessLocationUpdate(TripResponse tripResponse);

}
