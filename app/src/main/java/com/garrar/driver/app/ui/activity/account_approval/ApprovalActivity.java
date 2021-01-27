package com.garrar.driver.app.ui.activity.account_approval;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.garrar.driver.app.data.network.model.Help;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.garrar.driver.app.BuildConfig;
import com.garrar.driver.app.R;
import com.garrar.driver.app.base.BaseActivity;
import com.garrar.driver.app.data.network.model.Approval;
import com.garrar.driver.app.data.network.model.ReminderMessage;


import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalActivity extends BaseActivity implements
        ApprovalIView {


    private static final int REQUEST_CALL = 1;
    ApprovalPresenter presenter = new ApprovalPresenter();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.imgMail)
    ImageView imgMail;
    @BindView(R.id.imgWeb)
    ImageView imgWeb;
    @BindView(R.id.reminder_message)
    TextInputEditText reminderMessage;
    Approval approval;
    Help help;

    @Override
    public int getLayoutId() {
        return R.layout.account_approval;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.approval));
        presenter.ApprovalHelp();
        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(TELEPHONY_SERVICE);
        Objects.requireNonNull(telephonyManager).listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSuccess(Approval helpDetails) {
        approval = helpDetails;
    }

    @Override
    public void onSuccess(ReminderMessage response) {
        try {
            hideLoading();

            if(response.getStatus() == 200) {
                reminderMessage.setText("");
                Toast.makeText(ApprovalActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ApprovalActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("ApprovalActivity", "onSuccess: ", e);
        }
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    @Override
    public void onSuccess(Help help) {
        this.help = help;
    }


    @OnClick({R.id.imgCall, R.id.imgMail, R.id.imgWeb, R.id.send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgCall:
                makeCall();
                break;
            case R.id.imgMail:
                //String to = approval.approvalContactEmail();
                String to = help.getContactEmail();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + "-" + getString(R.string.help));
                intent.putExtra(Intent.EXTRA_TEXT, "Hello team");
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;
            case R.id.imgWeb:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BASE_URL));
                startActivity(browserIntent);
                break;

            case R.id.send_message:
                if (reminderMessage != null && reminderMessage.getText().toString().length() > 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("message", reminderMessage.getText().toString());
                    showLoading();
                    presenter.sendApprovalReminder(map);
                } else {
                    Toast.makeText(ApprovalActivity.this, "Please input message.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void makeCall() {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(approval.approvalContactNumber());
        } else {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall(help.getContactNumber());
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_CALL);
            }
        }
    }

    private void phoneCall(String contactNumber) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contactNumber));
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissions.length == 0) {
            return;
        }
        boolean allPermissionsGranted = true;
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //denied
                    Log.e("denied", permission);
                } else {
                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);
                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Permissions Required")
                        .setMessage("You have forcefully denied some of the required permissions " +
                                "for this action. Please open settings, go to permissions and allow them.")
                        .setPositiveButton("Settings", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        } else {
            switch (requestCode) {
                case REQUEST_CALL:
                    allPermissionsGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
            }
            if (allPermissionsGranted) {
                phoneCall(approval.approvalContactNumber());
            } else {
                Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {
        String LOG_TAG = "LOGGING 123";
        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }
            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // ACTIVE
                Log.i(LOG_TAG, "OFFHOOK");
                isPhoneCalling = true;
            }
            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");
                if (isPhoneCalling) {
                    Log.i(LOG_TAG, "restart app");
                    isPhoneCalling = false;
                }
            }
        }
    }
}
