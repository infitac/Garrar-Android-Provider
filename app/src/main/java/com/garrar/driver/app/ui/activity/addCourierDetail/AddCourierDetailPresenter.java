package com.garrar.driver.app.ui.activity.addCourierDetail;


import com.garrar.driver.app.base.BasePresenter;
import com.garrar.driver.app.data.network.APIClient;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class AddCourierDetailPresenter<V extends AddCourierDetailIView> extends BasePresenter<V> implements AddCourierDetailIPresenter<V> {

    @Override
    public void get_package_type() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getPackageType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void update(HashMap<String, RequestBody> obj, @Part MultipartBody.Part filename) {
        /*getCompositeDisposable().add(APIClient
                .getAPIClient()
                .updatepackagedata(obj, filename)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onUpdateSuccess, getMvpView()::onUpdateFailure));*/
    }

    @Override
    public void estimateFare(Map<String, Object> params) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .estimateFare(params)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void requestInstantRide(Map<String, Object> params) {
        /*getCompositeDisposable().add(APIClient
                .getAPIClient()
                .requestInstantRide(params)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));*/
    }
}
