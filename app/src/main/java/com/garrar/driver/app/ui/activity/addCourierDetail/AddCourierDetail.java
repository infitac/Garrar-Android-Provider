package com.garrar.driver.app.ui.activity.addCourierDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.garrar.driver.app.MvpApplication;
import com.garrar.driver.app.R;
import com.garrar.driver.app.base.BaseActivity;
import com.garrar.driver.app.data.network.model.EstimateFare;
import com.garrar.driver.app.data.network.model.PackageType;
import com.garrar.driver.app.data.network.model.TripResponse;
import com.garrar.driver.app.ui.activity.instant_ride.InstantRideActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.garrar.driver.app.MvpApplication.instantRide;

public class AddCourierDetail extends BaseActivity implements AddCourierDetailIView, AdapterView.OnItemSelectedListener {
    @BindView(R.id.sp_package_type)
    Spinner sp_package_type;
    @BindView(R.id.edt_receiver_name)
    EditText edt_receiver_name;
    @BindView(R.id.edt_receiver_phone)
    EditText edt_receiver_phone;
    @BindView(R.id.edt_pickupinstruction)
    EditText edt_pickupinstruction;
    @BindView(R.id.edt_delivery_instruction)
    EditText edt_delivery_instruction;
    @BindView(R.id.edt_no_of_box)
    EditText edt_no_of_box;
    @BindView(R.id.edt_box_height)
    EditText edt_box_height;
    @BindView(R.id.edt_box_width)
    EditText edt_box_width;
    @BindView(R.id.edt_box_weight)
    EditText edt_box_weight;
    @BindView(R.id.picture)
    ImageView img_package_image;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    Unbinder unbinder;
    ArrayList<PackageType.Datum> packagetypearr = new ArrayList<>();
    ArrayList<String> packagelist = new ArrayList<>();
    String package_id, package_name;
    public static final int REQUEST_PERMISSION = 100;
    double walletAmount;
    String serviceName;
    String type;
    String packageid;
    Context mContext = AddCourierDetail.this;
    private AddCourierDetailPresenter<AddCourierDetail> presenter = new AddCourierDetailPresenter<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_courier_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.get_package_type();
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");
        }
        if(type.equalsIgnoreCase("1")){
            edt_receiver_name.setText(MvpApplication.PACKAGE_DETAIL1.get("receiver_name"));
            edt_receiver_phone.setText(MvpApplication.PACKAGE_DETAIL1.get("receiver_phone"));
            edt_pickupinstruction.setText(MvpApplication.PACKAGE_DETAIL1.get("pickup_instructions"));
            edt_delivery_instruction.setText(MvpApplication.PACKAGE_DETAIL1.get("delivery_instructions"));
            edt_no_of_box.setText(MvpApplication.PACKAGE_DETAIL1.get("no_of_box"));
            edt_box_height.setText(MvpApplication.PACKAGE_DETAIL1.get("box_height"));
            edt_box_weight.setText(MvpApplication.PACKAGE_DETAIL1.get("box_weight"));
            edt_box_width.setText(MvpApplication.PACKAGE_DETAIL1.get("box_width"));
            Glide.with(mContext)
                    .load(Uri.fromFile(MvpApplication.imgFile))
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.ic_package)
                            .dontAnimate()
                            .error(R.drawable.ic_package))
                    .into(img_package_image);
            packageid = MvpApplication.PACKAGE_DETAIL1.get("package_type");
        }
    }

    @Override
    public void onSuccess(PackageType packageType) {
        packagetypearr.addAll(packageType.getData());
        for(int i =0; i<packageType.getData().size(); i++) {
            packagelist.add(packageType.getData().get(i).getPackageName());
        }
        sp_package_type.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.sppinner_adapter_layout,packagelist);
        /*sp_task.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                task_hash_id = allTaskListarr[position].hash_id
            }
        }*/
        sp_package_type.setAdapter(adapter);

        for(int i=0; i< packageType.getData().size(); i++){
            if(packageid.equalsIgnoreCase(packageType.getData().get(i).getId().toString())){
                sp_package_type.setSelection(i);
            }
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        package_id = packagetypearr.get(i).getId().toString();
        package_name = packagetypearr.get(i).getPackageName();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick({R.id.picture, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.picture:
                if (hasPermission(android.Manifest.permission.CAMERA) && hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    pickImage();
                else
                    requestPermissionsSafely(new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                break;
            case R.id.btn_submit:
                updatedetail();
                break;
        }
    }

    protected boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, requestCode);
    }

    private void updatedetail() {
        if(edt_receiver_name.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_receiver_name), Toast.LENGTH_SHORT).show();
        }else if(edt_receiver_phone.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_receiver_phone), Toast.LENGTH_SHORT).show();
        }else if(edt_pickupinstruction.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_receiver_phone), Toast.LENGTH_SHORT).show();
        }else if(edt_delivery_instruction.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_delivery_ins), Toast.LENGTH_SHORT).show();
        }else if(package_id.isEmpty()){
            Toast.makeText(mContext, getString(R.string.selectpackage_type), Toast.LENGTH_SHORT).show();
        }else if(edt_no_of_box.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_no_of_box), Toast.LENGTH_SHORT).show();
        }else if(edt_box_height.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_box_height), Toast.LENGTH_SHORT).show();
        }else if(edt_box_width.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_box_Width), Toast.LENGTH_SHORT).show();
        }else if(edt_box_weight.getText().toString().isEmpty()){
            Toast.makeText(mContext, getString(R.string.please_enter_box_Weight), Toast.LENGTH_SHORT).show();
        }else if(MvpApplication.imgFile == null){
            Toast.makeText(mContext, getString(R.string.selectimage), Toast.LENGTH_SHORT).show();
        }else {
            savedata();
        }
    }

    private void savedata() {
        MvpApplication.PACKAGE_DETAIL.put("receiver_name", RequestBody.create(MediaType.parse("text/plain"), edt_receiver_name.getText().toString()));
        MvpApplication.PACKAGE_DETAIL.put("receiver_phone", RequestBody.create(MediaType.parse("text/plain"), edt_receiver_phone.getText().toString()));
        MvpApplication.PACKAGE_DETAIL.put("pickup_instructions", RequestBody.create(MediaType.parse("text/plain"), edt_pickupinstruction.getText().toString()));
        MvpApplication.PACKAGE_DETAIL.put("delivery_instructions", RequestBody.create(MediaType.parse("text/plain"), edt_delivery_instruction.getText().toString()));
        MvpApplication.PACKAGE_DETAIL.put("package_type", RequestBody.create(MediaType.parse("text/plain"), package_name));
        MvpApplication.PACKAGE_DETAIL.put("no_of_box", RequestBody.create(MediaType.parse("text/plain"), edt_no_of_box.getText().toString()));
        MvpApplication.PACKAGE_DETAIL.put("box_height", RequestBody.create(MediaType.parse("text/plain"), edt_box_height.getText().toString()));
        MvpApplication.PACKAGE_DETAIL.put("box_width", RequestBody.create(MediaType.parse("text/plain"), edt_box_width.getText().toString()));
        MvpApplication.PACKAGE_DETAIL.put("box_weight", RequestBody.create(MediaType.parse("text/plain"), edt_box_weight.getText().toString()));
        MvpApplication.PACKAGE_DETAIL1.put("receiver_name", edt_receiver_name.getText().toString());
        MvpApplication.PACKAGE_DETAIL1.put("receiver_phone", edt_receiver_phone.getText().toString());
        MvpApplication.PACKAGE_DETAIL1.put("pickup_instructions", edt_pickupinstruction.getText().toString());
        MvpApplication.PACKAGE_DETAIL1.put("delivery_instructions", edt_delivery_instruction.getText().toString());
        MvpApplication.PACKAGE_DETAIL1.put("package_type", package_id);
        MvpApplication.PACKAGE_DETAIL1.put("package_name", package_name);
        MvpApplication.PACKAGE_DETAIL1.put("no_of_box", edt_no_of_box.getText().toString());
        MvpApplication.PACKAGE_DETAIL1.put("box_height", edt_box_height.getText().toString());
        MvpApplication.PACKAGE_DETAIL1.put("box_width", edt_box_width.getText().toString());
        MvpApplication.PACKAGE_DETAIL1.put("box_weight", edt_box_weight.getText().toString());
        /*showLoading();
        presenter.estimateFare(instantRide);*/
        Intent intent = new Intent();
        intent.putExtra("type",type);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSuccess(EstimateFare estimateFare) {
        hideLoading();
        showConfirmationDialog(estimateFare.getEstimatedFare(), instantRide);
    }

    @Override
    public void onSuccess(TripResponse response) {
        hideLoading();
        mDialog.dismiss();
    }

    private AlertDialog mDialog;

    private void showConfirmationDialog(double estimatedFare, Map<String, Object> params) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_instant_ride, null);

        TextView tvPickUp = view.findViewById(R.id.tvPickUp);
        TextView tvDrop = view.findViewById(R.id.tvDrop);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvFare = view.findViewById(R.id.tvFare);

        tvPickUp.setText(Objects.requireNonNull(params.get("s_address")).toString());
        tvDrop.setText(Objects.requireNonNull(params.get("d_address")).toString());
        tvPhone.setText(Objects.requireNonNull(params.get("mobile")).toString());
        tvFare.setText(String.valueOf(estimatedFare));

        builder.setView(view);
        mDialog = builder.create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        view.findViewById(R.id.tvSubmit).setOnClickListener(view1 -> {
            showLoading();
            presenter.requestInstantRide(params);
        });

        view.findViewById(R.id.tvCancel).setOnClickListener(view1 ->{
            mDialog.dismiss();
        });
        mDialog.show();
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        if (throwable != null)
            onErrorBase(throwable);
    }

    @Override
    public void onUpdateSuccess(JSONObject jsonObject) {
        Toast.makeText(mContext, jsonObject.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateFailure(Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, AddCourierDetail.this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                MvpApplication.imgFile = imageFiles.get(0);
                Glide.with(mContext)
                        .load(Uri.fromFile(MvpApplication.imgFile))
                        .apply(RequestOptions
                                .placeholderOf(R.drawable.ic_package)
                                .dontAnimate()
                                .error(R.drawable.ic_package))
                        .into(img_package_image);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    boolean permission1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean permission2 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (permission1 && permission2) pickImage();
                    else
                        Toast.makeText(mContext.getApplicationContext(), R.string.please_give_permission, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}