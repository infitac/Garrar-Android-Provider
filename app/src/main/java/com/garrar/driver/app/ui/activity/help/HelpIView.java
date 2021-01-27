package com.garrar.driver.app.ui.activity.help;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.Help;

public interface HelpIView extends MvpView {

    void onSuccess(Help object);

    void onError(Throwable e);
}
