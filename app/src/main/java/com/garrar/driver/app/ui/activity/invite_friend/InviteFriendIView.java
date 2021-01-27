package com.garrar.driver.app.ui.activity.invite_friend;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.UserResponse;

public interface InviteFriendIView extends MvpView {

    void onSuccess(UserResponse response);
    void onError(Throwable e);

}
