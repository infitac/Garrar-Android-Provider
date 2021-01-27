package com.garrar.driver.app.ui.activity.account_approval;


import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;


public interface ApprovalIPresenter<V extends ApprovalIView> extends MvpPresenter<V> {

    void ApprovalHelp();

    void sendApprovalReminder(HashMap<String, Object> obj);
}
