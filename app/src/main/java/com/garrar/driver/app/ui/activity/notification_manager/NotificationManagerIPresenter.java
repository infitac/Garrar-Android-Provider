package com.garrar.driver.app.ui.activity.notification_manager;

import com.garrar.driver.app.base.MvpPresenter;

public interface NotificationManagerIPresenter<V extends NotificationManagerIView> extends MvpPresenter<V> {
    void getNotificationManager();
}
