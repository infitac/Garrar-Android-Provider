package com.garrar.driver.app.ui.activity.setting;

import com.garrar.driver.app.base.MvpPresenter;

public interface SettingsIPresenter<V extends SettingsIView> extends MvpPresenter<V> {
    void changeLanguage(String languageID);
}
