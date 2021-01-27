package com.garrar.driver.app.ui.bottomsheetdialog.cancel;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.CancelResponse;

import java.util.List;

public interface CancelDialogIView extends MvpView {

    void onSuccessCancel(Object object);
    void onError(Throwable e);
    void onSuccess(List<CancelResponse> response);
    void onReasonError(Throwable e);
}
