package com.garrar.driver.app.ui.bottomsheetdialog.cancel;

import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;

public interface CancelDialogIPresenter<V extends CancelDialogIView> extends MvpPresenter<V> {

    void cancelRequest(HashMap<String, Object> obj);
    void getReasons();
}
