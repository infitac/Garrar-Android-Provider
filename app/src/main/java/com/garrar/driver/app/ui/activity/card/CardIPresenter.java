package com.garrar.driver.app.ui.activity.card;

import com.garrar.driver.app.base.MvpPresenter;

public interface CardIPresenter<V extends CardIView> extends MvpPresenter<V> {

    void deleteCard(String cardId);

    void card();

    void changeCard(String cardId);
}
