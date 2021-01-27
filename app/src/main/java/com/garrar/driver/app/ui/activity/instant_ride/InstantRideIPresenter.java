package com.garrar.driver.app.ui.activity.instant_ride;

import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public interface InstantRideIPresenter<V extends InstantRideIView> extends MvpPresenter<V> {

    void estimateFare(Map<String, Object> params);

    void requestInstantRide(HashMap<String, RequestBody> obj, @Part MultipartBody.Part filename);

}
