package com.garrar.driver.app.ui.activity.wallet_detail;

import com.garrar.driver.app.base.BasePresenter;
import com.garrar.driver.app.data.network.model.Transaction;

import java.util.ArrayList;

public class WalletDetailPresenter<V extends WalletDetailIView> extends BasePresenter<V> implements WalletDetailIPresenter<V> {
    @Override
    public void setAdapter(ArrayList<Transaction> myList) {
        getMvpView().setAdapter(myList);
    }
}
