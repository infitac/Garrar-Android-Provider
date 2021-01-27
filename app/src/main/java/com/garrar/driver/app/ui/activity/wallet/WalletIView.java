package com.garrar.driver.app.ui.activity.wallet;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.WalletMoneyAddedResponse;
import com.garrar.driver.app.data.network.model.WalletResponse;

public interface WalletIView extends MvpView {

    void onSuccess(WalletResponse response);

    void onSuccess(WalletMoneyAddedResponse response);

    void onError(Throwable e);
}
