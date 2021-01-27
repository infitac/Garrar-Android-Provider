package com.garrar.driver.app.ui.activity.account_approval;

import com.garrar.driver.app.base.BasePresenter;
import com.garrar.driver.app.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApprovalPresenter<V extends ApprovalIView> extends BasePresenter<V> implements ApprovalIPresenter<V> {
    @Override
    public void ApprovalHelp() {
        getCompositeDisposable().add(
                APIClient
                        .getAPIClient()
                        .getHelp()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getMvpView()::onSuccess, getMvpView()::onError));

    }

    @Override
    public void sendApprovalReminder(HashMap<String, Object> obj) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .sendReminderMessage(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }


}
