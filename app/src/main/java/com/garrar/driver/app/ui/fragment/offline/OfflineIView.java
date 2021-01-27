package com.garrar.driver.app.ui.fragment.offline;

import com.garrar.driver.app.base.MvpView;

public interface OfflineIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
