package com.garrar.driver.app.ui.activity.document;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.DriverDocumentResponse;

public interface DocumentIView extends MvpView {

    void onSuccess(DriverDocumentResponse response);

    void onDocumentSuccess(DriverDocumentResponse response);

    void onError(Throwable e);

    void onSuccessLogout(Object object);

}
