package com.garrar.driver.app.ui.bottomsheetdialog.invoice_flow;

import com.garrar.driver.app.base.MvpView;

public interface InvoiceDialogIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
