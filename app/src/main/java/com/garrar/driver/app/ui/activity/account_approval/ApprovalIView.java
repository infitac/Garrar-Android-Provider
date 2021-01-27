package com.garrar.driver.app.ui.activity.account_approval;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.Approval;
import com.garrar.driver.app.data.network.model.Help;
import com.garrar.driver.app.data.network.model.ReminderMessage;

public interface ApprovalIView extends MvpView {

    void onSuccess(Approval object);

    void onSuccess(ReminderMessage response);

    void onError(Throwable e);

    void onSuccess(Help help);
}
