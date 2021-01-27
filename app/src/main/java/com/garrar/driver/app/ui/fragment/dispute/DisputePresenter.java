package com.garrar.driver.app.ui.fragment.dispute;

import com.garrar.driver.app.base.BasePresenter;
import com.garrar.driver.app.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DisputePresenter<V extends DisputeIView> extends BasePresenter<V> implements DisputeIPresenter<V> {

    @Override
    public void dispute(HashMap<String, Object> obj) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .dispute(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void getDispute() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getDispute("Provider")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccessDispute, getMvpView()::onError));
    }
}
