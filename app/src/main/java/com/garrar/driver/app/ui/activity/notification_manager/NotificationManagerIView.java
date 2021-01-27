package com.garrar.driver.app.ui.activity.notification_manager;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.NotificationManager;

import java.util.List;

public interface NotificationManagerIView extends MvpView {

    void onSuccess(List<NotificationManager> managers);

    void onError(Throwable e);

}