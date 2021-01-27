package com.garrar.driver.app.ui.fragment.view_courier_detail;


import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.CourierDetail;

public interface ViewCourierDetailIView extends MvpView {

    void OnError(Throwable e);

    void onSuccess(CourierDetail courierDetail);
}
