package com.garrar.driver.app.ui.activity.setting;

import android.content.Intent;

import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.garrar.driver.app.R;
import com.garrar.driver.app.base.BaseActivity;
import com.garrar.driver.app.common.AppConstant;
import com.garrar.driver.app.common.LocaleHelper;
import com.garrar.driver.app.ui.activity.document.DocumentActivity;
import com.garrar.driver.app.ui.activity.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsActivity extends BaseActivity implements SettingsIView {

    @BindView(R.id.english)
    RadioButton english;
    @BindView(R.id.arabic)
    RadioButton arabic;

    @BindView(R.id.choose_language)
    RadioGroup chooseLanguage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String setting;

    private SettingsPresenter presenter = new SettingsPresenter();
    private String language;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setting = getIntent().getStringExtra("setting");

        getSupportActionBar().setTitle(getString(R.string.settings));
        languageReset();

        chooseLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            showLoading();
            switch (checkedId) {
                case R.id.english:
                    language = AppConstant.LANGUAGE_ENGLISH;
                    AppConstant.SELECTED_LANGUAGE = AppConstant.LANGUAGE_ENGLISH;
                    break;
                case R.id.arabic:
                    language = AppConstant.LANGUAGE_arabic;
                    AppConstant.SELECTED_LANGUAGE = AppConstant.LANGUAGE_arabic;
                    break;
                default:
                    break;
            }
            AppConstant.setCurrentLanguage(getApplicationContext(), language);
            presenter.changeLanguage(language);
        });
    }

    private void languageReset() {
        String dd = LocaleHelper.getLanguage(this);
        switch (dd) {
            case AppConstant.LANGUAGE_ENGLISH:
                english.setChecked(true);
                break;
            case AppConstant.LANGUAGE_arabic:
                arabic.setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccess(Object o) {
        hideLoading();

        AppConstant.setCurrentLanguage(getApplicationContext(), language);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK));
        this.overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null) {
            onErrorBase(e);
        }

        LocaleHelper.setLocale(getApplicationContext(), language);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK));
        this.overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);
    }

    @OnClick(R.id.tvChangeDoc)
    public void onViewClicked() {
        Intent intent = new Intent(this, DocumentActivity.class);
        intent.putExtra("isFromSettings", true);
        intent.putExtra("setting", setting);
        startActivity(intent);
    }
}
