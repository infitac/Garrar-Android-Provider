package com.garrar.driver.app.ui.activity.sociallogin;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.Token;

public interface SocialLoginIView extends MvpView {

    void onSuccess(Token token);
    void onError(Throwable e);
}
