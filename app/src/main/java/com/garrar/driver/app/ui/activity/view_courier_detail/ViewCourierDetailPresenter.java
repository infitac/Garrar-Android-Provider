package com.garrar.driver.app.ui.activity.view_courier_detail;


import com.garrar.driver.app.base.BasePresenter;
import com.garrar.driver.app.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ViewCourierDetailPresenter<V extends ViewCourierDetailIView> extends BasePresenter<V> implements ViewCourierDetailIPresenter<V> {

    @Override
    public void getCourierdetail(HashMap<String, Object> obj) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getCourierDetail(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::OnError));
    }
}
