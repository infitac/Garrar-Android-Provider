package com.garrar.driver.app.ui.activity.invite_friend;

import com.garrar.driver.app.base.BasePresenter;
import com.garrar.driver.app.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InviteFriendPresenter<V extends InviteFriendIView> extends BasePresenter<V> implements InviteFriendIPresenter<V> {
    @Override
    public void profile() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
