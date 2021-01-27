package com.garrar.driver.app.ui.activity.change_password;

import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;

public interface ChangePasswordIPresenter<V extends ChangePasswordIView> extends MvpPresenter<V> {

    void changePassword(HashMap<String, Object> obj);
}
