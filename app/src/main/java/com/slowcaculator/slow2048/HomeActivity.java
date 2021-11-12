package com.slowcaculator.slow2048;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.monster2048.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity implements PlayViewListener, View.OnClickListener {

    private static final String TAG = "HomeActivity";
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    @BindView(R.id.btnStart)
    ConstraintLayout btnStart;
    @BindView(R.id.btnResume)
    ConstraintLayout btnResume;
    @BindView(R.id.btnCafe)
    ConstraintLayout btnCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
    }

    private void initView() {
        showMainView();
        btnStart.setOnClickListener(this);
        btnResume.setOnClickListener(this);
        btnCafe.setOnClickListener(this);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_slide_in_up);
        imgIcon.startAnimation(animation);
    }

    private void gotoPlayFragment(boolean isResume) {
        Slow2048Fragment slow2048Fragment = new Slow2048Fragment(this);
        slow2048Fragment.setResume(isResume);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, slow2048Fragment).addToBackStack(Global.backStackName).commit();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            showDialogExitGame();
        }
    }

    public void showDialogExitGame() {
        Dialog dialog = new Dialog(HomeActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.dialog_exit_game, null);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button btnYes = view.findViewById(R.id.btnYes);
        final Button btnNo = view.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> System.exit(0));

        btnNo.setOnClickListener(v -> dialog.dismiss());

        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void showMainView() {
        imgIcon.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnCafe.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_in_right);
        btnStart.startAnimation(animation);
        btnCafe.startAnimation(animation);

        if (Global.getResumeState(getApplicationContext()) == true) {
            btnResume.setVisibility(View.VISIBLE);
            btnResume.startAnimation(animation);
        } else {
            btnResume.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideMainView() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_out_left);
        btnStart.startAnimation(animation);
        btnCafe.startAnimation(animation);

        imgIcon.setVisibility(View.GONE);
        btnStart.setVisibility(View.GONE);
        btnCafe.setVisibility(View.GONE);

        if (Global.getResumeState(getApplicationContext()) == true) {
            btnResume.setVisibility(View.GONE);
            btnResume.startAnimation(animation);
        }
    }

    @Override
    public void stopGame() {
        Global.saveResumeState(getApplicationContext(),false);
        Global.saveCurrentScore(getApplicationContext(),0);
        Global.saveCurrentData(getApplicationContext(),"");
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                gotoPlayFragment(false);
                break;
            case R.id.btnResume:
                loadCurrentData();
                break;
            case R.id.btnCafe:
                showDonate();
                break;
        }
    }

    private void loadCurrentData() {
        if (Global.getResumeState(getApplicationContext())) {
            String resumeData = Global.getCurrentData(getApplicationContext());
            Log.d(TAG, "loadCurrentData: " + resumeData);
            gotoPlayFragment(true);
        } else {
            btnResume.setVisibility(View.GONE);
        }
    }

    private void showDonate() {
        Toasty.success(getApplicationContext(), "Thanks for donate ! ", 500).show();
    }
}
