package com.garrar.driver.app.ui.fragment.incoming_request;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.garrar.driver.app.base.BaseFragment;
import com.garrar.driver.app.common.Utilities;
import com.garrar.driver.app.data.network.model.Request_;
import com.garrar.driver.app.ui.activity.view_courier_detail.ViewCourierDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.garrar.driver.app.BuildConfig;
import com.garrar.driver.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import es.dmoral.toasty.Toasty;

import static com.garrar.driver.app.MvpApplication.DATUM;
import static com.garrar.driver.app.MvpApplication.time_to_left;

public class IncomingRequestFragment extends BaseFragment implements IncomingRequestIView, VerticalStepperForm {

    @BindView(R.id.btnReject)
    Button btnReject;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    Unbinder unbinder;
    @BindView(R.id.lblCount)
    TextView lblCount;
    @BindView(R.id.imgUser)
    CircleImageView imgUser;
    @BindView(R.id.lblUserName)
    TextView lblUserName;
    @BindView(R.id.ratingUser)
    AppCompatRatingBar ratingUser;
    @BindView(R.id.lblLocationName)
    TextView lblLocationName;
    @BindView(R.id.lblDrop)
    TextView lblDrop;
    @BindView(R.id.lblScheduleDate)
    TextView lblScheduleDate;
    @BindView(R.id.con_detail)
    ConstraintLayout con_detail;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_add1)
    TextView tv_add1;
    @BindView(R.id.tv_add2)
    TextView tv_add2;
    @BindView(R.id.tv_add_dest)
    TextView tv_add_dest;
    @BindView(R.id.ll_add1)
    LinearLayout ll_add1;
    @BindView(R.id.ll_add2)
    LinearLayout ll_add2;

    /*@BindView(R.id.vertical_stepper_form)
    VerticalStepperFormLayout verticalStepperForm;*/

    private IncomingRequestPresenter presenter = new IncomingRequestPresenter();
    private Context context;
    public static CountDownTimer countDownTimer;
    public static MediaPlayer mPlayer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_incoming_request;
    }

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        presenter.attachView(this);
        mPlayer = MediaPlayer.create(context, R.raw.alert_tone);
        /*String[] mySteps = {"Name", "Email", "Phone Number"};
        int colorPrimary = ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimaryDark);
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, getActivity())
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();*/
        init();
        return view;
    }

    @SuppressLint("SetTextI18n")
    void init() {
        Request_ data = DATUM;
        if (data != null) {
            lblUserName.setText(String.format("%s %s", data.getUser().getFirstName(),
                    data.getUser().getLastName()));
            ratingUser.setRating(Float.parseFloat(data.getUser().getRating()));
            if (data.getSAddress() != null && !data.getSAddress().isEmpty()
                    || data.getDAddress() != null && !data.getDAddress().isEmpty()) {
                lblLocationName.setText(data.getSAddress());
                lblDrop.setText(data.getDAddress());
            }
            tv_add.setText(data.getPackageInfos().get(0).getPAddress());
            tv_add_dest.setText(data.getDAddress());
            if(data.getPackageInfos().size() == 3){
                tv_add1.setText(data.getPackageInfos().get(1).getPAddress());
                tv_add2.setText(data.getPackageInfos().get(2).getPAddress());
                ll_add1.setVisibility(View.VISIBLE);
                ll_add2.setVisibility(View.VISIBLE);
            }else if(data.getPackageInfos().size() == 2){
                tv_add1.setText(data.getPackageInfos().get(1).getPAddress());
                ll_add1.setVisibility(View.VISIBLE);
            }
            if (data.getUser().getPicture() != null)
                Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL + data.getUser()
                        .getPicture()).apply(RequestOptions.placeholderOf(R.drawable.ic_user_placeholder)
                        .dontAnimate().error(R.drawable.ic_user_placeholder)).into(imgUser);
        }

        String isScheduled = data.getIsScheduled();
        String scheduledAt = data.getScheduleAt();
        if (isScheduled != null && isScheduled.equalsIgnoreCase("NO")) {
            lblScheduleDate.setVisibility(View.INVISIBLE);
        } else {
            if (scheduledAt != null && isScheduled.equalsIgnoreCase("YES")) {
                StringTokenizer tk = new StringTokenizer(scheduledAt);
                String date = tk.nextToken();
                String time = tk.nextToken();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
                Date dt;
                try {
                    dt = sdf.parse(time);
                    lblScheduleDate.setVisibility(View.VISIBLE);
                    lblScheduleDate.setText(getString(R.string.schedule_for) + " " +
                            Utilities.convertDate(scheduledAt) + " " + sdfs.format(dt));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        countDownTimer = new CountDownTimer(time_to_left * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                lblCount.setText(String.valueOf(millisUntilFinished / 1000));
                setTvZoomInOutAnimation(lblCount);
            }

            public void onFinish() {
                try {
                    context.sendBroadcast(new Intent("INTENT_FILTER"));
                    mPlayer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        countDownTimer.start();
    }

    private void setTvZoomInOutAnimation(final TextView textView) {
        final float startSize = 20;
        final float endSize = 13;
        final int animationDuration = 900; // Animation duration in ms

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (Float) valueAnimator.getAnimatedValue();
            textView.setTextSize(animatedValue);
        });
        //animator.setRepeatCount(ValueAnimator.INFINITE);  // Use this line for infinite animations
        animator.setRepeatCount(2);
        animator.start();
    }


    @OnClick({R.id.btnReject, R.id.btnAccept, R.id.con_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReject:
                if (DATUM != null) {
                    Request_ datum = DATUM;
                    showLoading();
                    presenter.cancel(datum.getId());
                    time_to_left = 60;
                }
                break;
            case R.id.btnAccept:
                if (DATUM != null) {
                    Request_ datum = DATUM;
                    showLoading();
                    presenter.accept(datum.getId());
                    time_to_left = 60;
                }
                break;
            case R.id.con_detail:
                startActivity(new Intent(getActivity(), ViewCourierDetailActivity.class)
                        .putExtra("request_id",String.valueOf(DATUM.getId())));
                break;
        }
    }

    @Override
    public void onSuccessAccept(Object responseBody) {
        countDownTimer.cancel();
        hideLoading();
        Toast.makeText(getContext(), getString(R.string.ride_accepted), Toast.LENGTH_SHORT).show();
        getContext().sendBroadcast(new Intent("INTENT_FILTER"));
        try {
            getActivity().getSupportFragmentManager().beginTransaction().remove(IncomingRequestFragment.this).commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessCancel(Object object) {
        countDownTimer.cancel();
        hideLoading();
        getActivity().getSupportFragmentManager().beginTransaction().remove(IncomingRequestFragment.this).commitAllowingStateLoss();
        Toasty.success(context, getString(R.string.ride_cancelled), Toast.LENGTH_SHORT, true).show();
        context.sendBroadcast(new Intent("INTENT_FILTER"));
    }

    @Override
    public void onError(Throwable e) {
        try {
            hideLoading();
            if (mPlayer.isPlaying()) mPlayer.stop();
            if (e != null)
                onErrorBase(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mPlayer.isPlaying())
            mPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer.isPlaying())
            mPlayer.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying())
            mPlayer.stop();
    }

    @Override
    public View createStepContentView(int stepNumber) {
        return null;
    }

    @Override
    public void onStepOpening(int stepNumber) {

    }

    @Override
    public void sendData() {

    }
}
