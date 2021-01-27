package com.garrar.driver.app.ui.activity.addCourierDetail;



import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public interface AddCourierDetailIPresenter<V extends AddCourierDetailIView> extends MvpPresenter<V> {
 void get_package_type();

 void update(HashMap<String, RequestBody> obj, @Part MultipartBody.Part filename);

 void estimateFare(Map<String, Object> params);

 void requestInstantRide(Map<String, Object> params);
}
