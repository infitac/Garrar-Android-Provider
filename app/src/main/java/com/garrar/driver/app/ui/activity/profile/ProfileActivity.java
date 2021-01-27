package com.garrar.driver.app.ui.activity.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.garrar.driver.app.common.AppConstant;
import com.garrar.driver.app.base.BaseActivity;
import com.garrar.driver.app.common.SharedHelper;
import com.garrar.driver.app.common.Utilities;
import com.garrar.driver.app.data.network.model.UserResponse;
import com.garrar.driver.app.ui.activity.change_password.ChangePasswordActivtiy;
import com.garrar.driver.app.ui.countrypicker.Country;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.garrar.driver.app.BuildConfig;
import com.garrar.driver.app.MvpApplication;
import com.garrar.driver.app.R;
import com.garrar.driver.app.ui.activity.main.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ProfileActivity extends BaseActivity implements ProfileIView {

    private ProfilePresenter presenter = new ProfilePresenter();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtFirstName)
    EditText txtFirstName;
    @BindView(R.id.txtLastName)
    EditText txtLastName;
    @BindView(R.id.txtPhoneNumber)
    TextView txtPhoneNumber;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtService)
    EditText txtService;
    @BindView(R.id.txtModel)
    EditText txtModel;
    @BindView(R.id.txtNumber)
    EditText txtNumber;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.lblChangePassword)
    TextView lblChangePassword;
    @BindView(R.id.qr_scan)
    ImageView ivQrScan;

    private File imgFile = null;
    private String countryDialCode = AppConstant.DEFAULT_COUNTRY_DIAL_CODE;
    private String countryCode = AppConstant.DEFAULT_COUNTRY_CODE;
    private String qrCodeUrl;

    private AlertDialog mDialog;

    FirebaseAuth mAuth;
    private String codeSend;
    private Dialog dialog;
    private String oldMobileNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);

        showLoading();
        presenter.getProfile();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.profile));

        getUserCountryInfo();

        mAuth = FirebaseAuth.getInstance();
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

    @OnClick({R.id.btnSave, R.id.lblChangePassword, R.id.imgProfile, R.id.qr_scan, R.id.txtPhoneNumber})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                profileUpdate();
                break;
            case R.id.lblChangePassword:
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivtiy.class));
                break;
            case R.id.imgProfile:
                MultiplePermissionTask();
                break;
            case R.id.qr_scan:
                if (!TextUtils.isEmpty(qrCodeUrl)) showQRCodePopup();
                break;
            case R.id.txtPhoneNumber:
//                fbOtpVerify(countryCode, countryDialCode, "");
                showPhoneVerificationDialog();
                break;
        }
    }


    private void getUserCountryInfo() {
        Country country = getDeviceCountry(ProfileActivity.this);
        countryDialCode = country.getDialCode();
        countryCode = country.getCode();
    }

    private void showQRCodePopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_qrcode_image, null);

        ImageView qr_image = view.findViewById(R.id.qr_image);
        ImageView close = view.findViewById(R.id.ivClose);

        Glide.with(ProfileActivity.this)
                .load(qrCodeUrl)
                .apply(RequestOptions
                        .placeholderOf(R.drawable.ic_qr_code)
                        .dontAnimate().error(R.drawable.ic_qr_code))
                .into(qr_image);

        builder.setView(view);
        mDialog = builder.create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        close.setOnClickListener(view1 -> mDialog.dismiss());

        mDialog.show();
    }

    void profileUpdate() {
        if (txtFirstName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_first_name), Toast.LENGTH_SHORT).show();
        } else if (txtLastName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_last_name), Toast.LENGTH_SHORT).show();
        } else if (txtPhoneNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_phone_number), Toast.LENGTH_SHORT).show();
        } else if (txtEmail.getText().toString().isEmpty()
                || !Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
//        } else if (!phoneNumber.equalsIgnoreCase(txtPhoneNumber.getText().toString())) {
            //Mobile number having both country code or not so we simply sending empty here
//            fbOtpVerify(countryCode, countryDialCode, "");
        } else updateDetails();
    }

    private void updateDetails() {
        Map<String, RequestBody> map = new HashMap<>();

        map.put("first_name", toRequestBody(txtFirstName.getText().toString()));
        map.put("last_name", toRequestBody(txtLastName.getText().toString()));
        map.put("email", toRequestBody(txtEmail.getText().toString()));
 //       map.put("mobile", toRequestBody(txtPhoneNumber.getText().toString()));
        if((oldMobileNumber == null && !txtPhoneNumber.getText().toString().isEmpty()) || !txtPhoneNumber.getText().toString().equals(oldMobileNumber)) {
            map.put("mobile", RequestBody.create(MediaType.parse("text/plain"), txtPhoneNumber.getText().toString()));
            map.put("country_code", RequestBody.create(MediaType.parse("text/plain"), SharedHelper.getKey(ProfileActivity.this, "country_code")));
        }

        MultipartBody.Part filePart = null;
        if (imgFile != null)
            try {
                File compressedImageFile = new Compressor(this).compressToFile(imgFile);
                filePart = MultipartBody.Part.createFormData("avatar", compressedImageFile.getName(),
                        RequestBody.create(MediaType.parse("image*//*"), compressedImageFile));
            } catch (IOException e) {
                e.printStackTrace();
            }

        Utilities.printV("Params ===> 2", map.toString());
        showLoading();
        presenter.profileUpdate(map, filePart);
    }

    @Override
    public void onSuccess(UserResponse user) {
        hideLoading();
        Utilities.printV("User===>", user.getFirstName() + user.getLastName());
        Utilities.printV("TOKEN===>", SharedHelper.getKey(MvpApplication.getInstance(),
                AppConstant.SharedPref.ACCESS_TOKEN, ""));

        String loginBy = user.getLoginBy();

        if (loginBy.equalsIgnoreCase("facebook") || loginBy.equalsIgnoreCase("google"))
            lblChangePassword.setVisibility(View.INVISIBLE);
        else lblChangePassword.setVisibility(View.VISIBLE);

        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtPhoneNumber.setText(String.valueOf(user.getMobile()));
        txtNumber.setText(user.getService().getServiceNumber());
        txtModel.setText(user.getService().getServiceModel());

        txtEmail.setText(user.getEmail());
        SharedHelper.putKey(this, AppConstant.SharedPref.STRIPE_PUBLISHABLE_KEY, user.getStripePublishableKey());
        if (user.getService() != null)
            txtService.setText((user.getService().getServiceType() != null)
                    ? user.getService().getServiceType().getName() : "");
        Glide.with(activity())
                .load(BuildConfig.BASE_IMAGE_URL + user.getAvatar())
                .apply(RequestOptions
                        .placeholderOf(R.drawable.ic_user_placeholder)
                        .dontAnimate()
                        .error(R.drawable.ic_user_placeholder))
                .into(imgProfile);
        AppConstant.showOTP = user.getRide_otp().equals("1");
        qrCodeUrl = !TextUtils.isEmpty(user.getQrcode_url()) ? BuildConfig.BASE_URL + user.getQrcode_url() : null;
        ivQrScan.setVisibility(TextUtils.isEmpty(qrCodeUrl) ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onSuccessUpdate(UserResponse object) {
        hideLoading();
        Intent profileIntent = new Intent(this, MainActivity.class);
        profileIntent.putExtra("avatar", BuildConfig.BASE_IMAGE_URL + object.getAvatar());
        startActivity(profileIntent);
        Toasty.success(this, getString(R.string.profile_updated), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, ProfileActivity.this,
                new DefaultCallback() {
                    @Override
                    public void onImagesPicked(@NonNull List<File> imageFiles,
                                               EasyImage.ImageSource source, int type) {
                        imgFile = imageFiles.get(0);
                        Glide.with(activity())
                                .load(Uri.fromFile(imgFile))
                                .apply(RequestOptions
                                        .placeholderOf(R.drawable.ic_user_placeholder)
                                        .dontAnimate()
                                        .error(R.drawable.ic_user_placeholder))
                                .into(imgProfile);
                    }
                });

        if (requestCode == AppConstant.APP_REQUEST_CODE && data != null) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (!loginResult.wasCancelled())
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(Account account) {
                        Log.d("AccountKit", "onSuccess: Account Kit" + Objects.requireNonNull(AccountKit.getCurrentAccessToken()).getToken());
                        if (Objects.requireNonNull(AccountKit.getCurrentAccessToken()).getToken() != null) {
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            SharedHelper.putKey(ProfileActivity.this, AppConstant.SharedPref.DIAL_CODE, "+" + phoneNumber.getCountryCode());
                            SharedHelper.putKey(ProfileActivity.this, AppConstant.SharedPref.MOBILE, phoneNumber.getPhoneNumber());
                            txtPhoneNumber.setText(phoneNumber.getPhoneNumber());
                            presenter.verifyCredentials("+" + phoneNumber.getCountryCode(), phoneNumber.getPhoneNumber());
                        }
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
                        Log.e("AccountKit", "onError: Account Kit" + accountKitError);
                    }
                });
        }
    }

    private boolean hasMultiplePermission() {
        return EasyPermissions.hasPermissions(this, AppConstant.MULTIPLE_PERMISSION);
    }

    @AfterPermissionGranted(AppConstant.RC_MULTIPLE_PERMISSION_CODE)
    void MultiplePermissionTask() {
        if (hasMultiplePermission()) pickImage();
        else EasyPermissions.requestPermissions(
                this, getString(R.string.please_accept_permission),
                AppConstant.RC_MULTIPLE_PERMISSION_CODE,
                AppConstant.MULTIPLE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onSuccessPhoneNumber(Object object) {
        updateDetails();
    }

    @Override
    public void onVerifyPhoneNumberError(Throwable e) {
        Toasty.error(this, getString(R.string.phone_number_already_exists), Toast.LENGTH_SHORT).show();
        txtPhoneNumber.requestFocus();
    }


    private void showPhoneVerificationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_phone_number, null);

        TextView verifyText = view.findViewById(R.id.verify_your_phone_text);
        AppCompatButton btnSendOTP = view.findViewById(R.id.btnSendOTP);
        EditText etPhoneNumber = view.findViewById(R.id.etPhoneNumber);

        verifyText.setVisibility(View.GONE);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
                    Toast.makeText(ProfileActivity.this, "Phone number required .", Toast.LENGTH_SHORT).show();
                } else if (etPhoneNumber.getText().toString().length() < 10) {
                    Toast.makeText(ProfileActivity.this, "Insert valid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    sendVerificationCode(etPhoneNumber.getText().toString());
                }
            }
        });

        builder.setView(view);
        mDialog = builder.create();

        mDialog.show();
    }

    private void sendVerificationCode(String phoneNumber) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        showAlertDialog();


    }

    private void showAlertDialog() {


        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.verify_number_dialog);


        Button dialogButton = dialog.findViewById(R.id.btnVerify);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        EditText edtVerification_code = dialog.findViewById(R.id.edtVerification_code);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtVerification_code.getText().toString())) {

                    Toast.makeText(ProfileActivity.this, "Enter Verification code .", Toast.LENGTH_SHORT).show();

                } else {
                    verifyPhoneNumber(edtVerification_code.getText().toString());

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSend = s;


            Toast.makeText(ProfileActivity.this, "Check your phone for OTP code ", Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyPhoneNumber(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSend, code);

        signInWithPhoneAuthCredential(credential);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();

                            System.out.println("Phone_num" + user.getPhoneNumber());

                            Toast.makeText(ProfileActivity.this, "Your number is verified .", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            mDialog.dismiss();

                            txtPhoneNumber.setText(user.getPhoneNumber());


                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(ProfileActivity.this, "Verification failed .", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                mDialog.dismiss();

                            }
                        }


                        if (!task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




}
