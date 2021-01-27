package com.garrar.driver.app.ui.fragment.view_courier_detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.garrar.driver.app.R;
import com.garrar.driver.app.base.BaseFragment;
import com.garrar.driver.app.data.network.model.CourierDetail;
import com.garrar.driver.app.data.network.model.Request_;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.garrar.driver.app.MvpApplication.DATUM;

public class ViewCourierDetailFragment extends BaseFragment implements ViewCourierDetailIView {

    @BindView(R.id.img_package)
    ImageView img_package;
    @BindView(R.id.txt_receiver_name)
    TextView txt_receiver_name;
    @BindView(R.id.txt_receiver_phone)
    TextView txt_receiver_phone;
    @BindView(R.id.txt_pickup_ins)
    TextView txt_pickup_ins;
    @BindView(R.id.txt_delivey_ins)
    TextView txt_delivey_ins;
    @BindView(R.id.txt_package_type)
    TextView txt_package_type;
    @BindView(R.id.txt_no_box)
    TextView txt_no_box;
    @BindView(R.id.txt_box_height)
    TextView txt_box_height;
    @BindView(R.id.txt_box_width)
    TextView txt_box_width;
    @BindView(R.id.txt_box_weight)
    TextView txt_box_weight;
    @BindView(R.id.btn_cancel)
    TextView btn_cancel;

    private ViewCourierDetailPresenter presenter = new ViewCourierDetailPresenter<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_view_courier_detail;
    }

    @Override
    public View initView(View view) {
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        HashMap<String, Object> map = new HashMap<>();
        Request_ datum = DATUM;
        map.put("user_request_id", datum.getId());
//        showLoading();
        presenter.getCourierdetail(map);
       /* Glide.with(baseActivity())
                .load(Uri.fromFile(MvpApplication.imgFile))
                .apply(RequestOptions
                        .placeholderOf(R.drawable.ic_package)
                        .dontAnimate()
                        .error(R.drawable.ic_package))
                .into(img_package);
        txt_receiver_name.setText(MvpApplication.PACKAGE_DETAIL1.get("receiver_name"));
        txt_receiver_phone.setText(MvpApplication.PACKAGE_DETAIL1.get("receiver_phone"));
        txt_pickup_ins.setText(MvpApplication.PACKAGE_DETAIL1.get("pickup_instructions"));
        txt_delivey_ins.setText(MvpApplication.PACKAGE_DETAIL1.get("delivery_instructions"));
        txt_package_type.setText(MvpApplication.PACKAGE_DETAIL1.get("package_name"));
        txt_no_box.setText(MvpApplication.PACKAGE_DETAIL1.get("no_of_box"));
        txt_box_height.setText(MvpApplication.PACKAGE_DETAIL1.get("box_height"));
        txt_box_weight.setText(MvpApplication.PACKAGE_DETAIL1.get("box_weight"));
        txt_box_width.setText(MvpApplication.PACKAGE_DETAIL1.get("box_width"));*/
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
               // getActivity().onBackPressed();
                onDestroy();
                break;
        }
    }


    @Override
    public void onSuccess(CourierDetail courierDetail) {
        //hideLoading();
        /*Glide.with(baseActivity())
                .load(Uri.fromFile(MvpApplication.imgFile))
                .apply(RequestOptions
                        .placeholderOf(R.drawable.ic_package)
                        .dontAnimate()
                        .error(R.drawable.ic_package))
                .into(img_package);*/
        txt_receiver_name.setText(courierDetail.getReceiverinfo().getReceiverName());
        txt_receiver_phone.setText(courierDetail.getReceiverinfo().getReceiverPhone());
        txt_pickup_ins.setText(courierDetail.getReceiverinfo().getPickupInstructions());
        txt_delivey_ins.setText(courierDetail.getReceiverinfo().getDeliveryInstructions());
        txt_package_type.setText(courierDetail.getReceiverinfo().getDeliveryInstructions());
        txt_no_box.setText(courierDetail.getPackageinfo().get(0).getNoOfBox());
        txt_box_height.setText(courierDetail.getPackageinfo().get(0).getBoxHeight());
        txt_box_weight.setText(courierDetail.getPackageinfo().get(0).getBoxWeight());
        txt_box_width.setText(courierDetail.getPackageinfo().get(0).getBoxWidth());
    }

    @Override
    public void OnError(Throwable e) {
        try {
            if (e != null)
                onErrorBase(e);
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
    }
}