package com.garrar.driver.app.ui.fragment.incoming_request;

import com.garrar.driver.app.base.MvpPresenter;

public interface IncomingRequestIPresenter<V extends IncomingRequestIView> extends MvpPresenter<V> {

    void accept(Integer id);
    void cancel(Integer id);
}
