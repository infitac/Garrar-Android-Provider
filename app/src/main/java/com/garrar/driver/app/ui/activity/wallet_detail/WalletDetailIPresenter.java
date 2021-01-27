package com.garrar.driver.app.ui.activity.wallet_detail;

import com.garrar.driver.app.base.MvpPresenter;
import com.garrar.driver.app.data.network.model.Transaction;

import java.util.ArrayList;

public interface WalletDetailIPresenter<V extends WalletDetailIView> extends MvpPresenter<V> {
    void setAdapter(ArrayList<Transaction> myList);
}
