package com.garrar.driver.app.ui.activity.addCourierDetail;


import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.EstimateFare;
import com.garrar.driver.app.data.network.model.PackageType;
import com.garrar.driver.app.data.network.model.TripResponse;

import org.json.JSONObject;

public interface AddCourierDetailIView extends MvpView {

    void onError(Throwable e);

    void onSuccess(PackageType packageType);

    void onUpdateSuccess(JSONObject jsonObject);

    void onUpdateFailure(Throwable throwable);

    void onSuccess(EstimateFare estimateFare);

    void onSuccess(TripResponse response);

}
