package com.garrar.driver.app.ui.activity.add_card;

import com.garrar.driver.app.base.MvpView;

public interface AddCardIView extends MvpView {

    void onSuccess(Object card);

    void onError(Throwable e);
}
