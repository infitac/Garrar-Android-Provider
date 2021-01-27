package com.garrar.driver.app.ui.activity.view_courier_detail;



import com.garrar.driver.app.base.MvpPresenter;

import java.util.HashMap;

public interface ViewCourierDetailIPresenter<V extends ViewCourierDetailIView> extends MvpPresenter<V> {

void getCourierdetail(HashMap<String, Object> obj);
}
