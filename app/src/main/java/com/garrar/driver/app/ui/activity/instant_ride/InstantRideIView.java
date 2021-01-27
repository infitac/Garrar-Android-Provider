package com.garrar.driver.app.ui.activity.instant_ride;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.EstimateFare;
import com.garrar.driver.app.data.network.model.EstimateFareNew;
import com.garrar.driver.app.data.network.model.TripResponse;

public interface InstantRideIView extends MvpView {

    void onSuccess(EstimateFareNew estimateFare);

    void onSuccess(TripResponse response);

    void onError(Throwable e);

}
