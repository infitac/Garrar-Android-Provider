package com.garrar.driver.app.ui.activity.wallet_detail;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.Transaction;

import java.util.ArrayList;

public interface WalletDetailIView extends MvpView {
    void setAdapter(ArrayList<Transaction> myList);
}
