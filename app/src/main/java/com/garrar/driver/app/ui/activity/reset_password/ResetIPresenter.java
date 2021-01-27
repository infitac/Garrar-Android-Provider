package com.garrar.driver.app.ui.activity.reset_password;

import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;

public interface ResetIPresenter<V extends ResetIView> extends MvpPresenter<V> {

    void reset(HashMap<String, Object> obj);

}
