package com.garrar.driver.app.ui.activity.help;


import com.garrar.driver.app.base.MvpPresenter;

public interface HelpIPresenter<V extends HelpIView> extends MvpPresenter<V> {

    void getHelp();
}
