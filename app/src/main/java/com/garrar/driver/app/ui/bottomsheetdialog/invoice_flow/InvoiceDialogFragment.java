package com.garrar.driver.app.ui.bottomsheetdialog.invoice_flow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garrar.driver.app.MvpApplication;
import com.garrar.driver.app.R;
import com.garrar.driver.app.base.BaseFragment;
import com.garrar.driver.app.common.AppConstant;
import com.garrar.driver.app.data.network.model.Request_;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.garrar.driver.app.MvpApplication.DATUM;
import static com.garrar.driver.app.MvpApplication.RIDE_REQUEST;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_ADD1;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_ADD2;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LAT1;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LAT2;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LONG1;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LONG2;

public class InvoiceDialogFragment extends BaseFragment implements InvoiceDialogIView {

    @BindView(R.id.promotion_amount)
    TextView promotionAmount;
    @BindView(R.id.wallet_amount)
    TextView walletAmount;
    @BindView(R.id.booking_id)
    TextView bookingId;
    @BindView(R.id.total_amount)
    TextView totalAmount;
    @BindView(R.id.payable_amount)
    TextView payableAmount;
    @BindView(R.id.payment_mode_img)
    ImageView paymentModeImg;
    @BindView(R.id.payment_mode_layout)
    LinearLayout paymentModeLayout;
    @BindView(R.id.llAmountToBePaid)
    LinearLayout llAmountToBePaid;
    Unbinder unbinder;
    @BindView(R.id.btnConfirmPayment)
    Button btnConfirmPayment;

    InvoiceDialogPresenter presenter;
    @BindView(R.id.lblPaymentType)
    TextView lblPaymentType;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invoice_dialog;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter = new InvoiceDialogPresenter();
        presenter.attachView(this);
        // setCancelable(false);
        if (DATUM != null) {
            Request_ datum = DATUM;
            bookingId.setText(datum.getBookingId());
            if (datum.getPayment() != null) {
                if (datum.getPayment().getTotal() > 0)
                    totalAmount.setText(AppConstant.Currency + " " +
                            MvpApplication.getInstance().getNewNumberFormat(Double.parseDouble(datum.getPayment().getTotal() + "")));
                if (datum.getPayment().getPayable() > 0) {
                    llAmountToBePaid.setVisibility(View.VISIBLE);
                    payableAmount.setText(AppConstant.Currency + " " +
                            MvpApplication.getInstance().getNewNumberFormat(Double.parseDouble(datum.getPayment().getPayable() + "")));
                } else llAmountToBePaid.setVisibility(View.GONE);
            }
        }
        return view;
    }

    @Override
    public void onSuccess(Object object) {
        hideLoading();
        activity().sendBroadcast(new Intent("INTENT_FILTER"));
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        try {
            if (e != null)
                onErrorBase(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @OnClick(R.id.btnConfirmPayment)
    public void onViewClicked() {
        RIDE_REQUEST.remove(DEST_LAT1);
        RIDE_REQUEST.remove(DEST_LAT2);
        RIDE_REQUEST.remove(DEST_LONG1);
        RIDE_REQUEST.remove(DEST_LONG2);
        RIDE_REQUEST.remove(DEST_ADD1);
        RIDE_REQUEST.remove(DEST_ADD2);
        if (DATUM != null) {
            Request_ datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", AppConstant.checkStatus.COMPLETED);
            map.put("_method", AppConstant.checkStatus.PATCH);
            showLoading();
            presenter.statusUpdate(map, datum.getId());
        }
    }

}
