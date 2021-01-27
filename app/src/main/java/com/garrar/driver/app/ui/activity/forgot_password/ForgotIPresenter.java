package com.garrar.driver.app.ui.activity.forgot_password;

import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;

public interface ForgotIPresenter<V extends ForgotIView> extends MvpPresenter<V> {

    void forgot(HashMap<String, Object> obj);

}
