package com.garrar.driver.app.ui.fragment.dispute;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.DisputeResponse;

import java.util.List;

public interface DisputeIView extends MvpView {

    void onSuccessDispute(List<DisputeResponse> responseList);

    void onSuccess(Object object);

    void onError(Throwable e);
}
