package com.garrar.driver.app.ui.activity.card;

import com.garrar.driver.app.base.MvpView;
import com.garrar.driver.app.data.network.model.Card;

import java.util.List;

public interface CardIView extends MvpView {

    void onSuccess(Object card);

    void onSuccess(List<Card> cards);

    void onError(Throwable e);

    void onSuccessChangeCard(Object card);
}
