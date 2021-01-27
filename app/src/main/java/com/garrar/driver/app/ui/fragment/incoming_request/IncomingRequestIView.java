package com.garrar.driver.app.ui.fragment.incoming_request;

import com.garrar.driver.app.base.MvpView;

public interface IncomingRequestIView extends MvpView {

    void onSuccessAccept(Object responseBody);
    void onSuccessCancel(Object object);
    void onError(Throwable e);
}
