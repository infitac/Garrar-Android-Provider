package com.garrar.driver.app.ui.activity.add_card;

import com.garrar.driver.app.base.MvpPresenter;

public interface AddCardIPresenter<V extends AddCardIView> extends MvpPresenter<V> {

    void addCard(String stripeToken);
}
