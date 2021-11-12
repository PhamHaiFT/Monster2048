package com.slowcaculator.slow2048;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monster2048.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import me.grantland.widget.AutofitTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Slow2048Fragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.gvGame)
    GridView gvGame;
    @BindView(R.id.tvScore)
    AutofitTextView tvScore;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvBestScore)
    AutofitTextView tvBestScore;

    private BlockAdapter blockAdapter;
    private PlayViewListener playViewListener;
    private static int bestScore;
    private View.OnTouchListener onTouchListener;
    private float x, y;
    private boolean isEndGame = false;
    private Dialog dialog;
    private boolean isResume = false;
    private static final String TAG = "MonsterFragment";

    public Slow2048Fragment(PlayViewListener playViewListener) {
        // Required empty public constructor
        this.playViewListener = playViewListener;
    }

    public boolean isResume() {
        return isResume;
    }

    public void setResume(boolean resume) {
        isResume = resume;
    }

    @Override
    public void onStart() {
        if (playViewListener != null) {
            playViewListener.hideMainView();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (playViewListener != null) {
            playViewListener.showMainView();
        }
        blockAdapter.clearData();
        DoNotRemove.getInstance().resetScore();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slow2048, container, false);
        ButterKnife.bind(this, view);

        initData();
        initView();

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initData() {
        if (isResume()) {
            updateTextView(tvScore, Global.getCurrentScore(getContext()));
            DoNotRemove.getInstance().initSaved(getContext(), getSavedData());
        } else {
            updateTextView(tvScore, 0);
            DoNotRemove.getInstance().init(getContext());
        }
        onTouchListener = (v, event) -> {
            if (isEndGame != true) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getX() - x) > Math.abs(event.getY() - y)) {
                            if (event.getX() > x) {
                                Log.d(TAG, "onTouch: move right");
                                if (DoNotRemove.getInstance().checkCanMove()) {
                                    DoNotRemove.getInstance().swipeRight();
                                    blockAdapter.notifyDataSetChanged();
                                    updateScore(DoNotRemove.getInstance().getScore());
                                } else {
                                    endGame();
                                }
                            } else {
                                Log.d(TAG, "onTouch: move left");
                                if (DoNotRemove.getInstance().checkCanMove()) {
                                    DoNotRemove.getInstance().swipeLeft();
                                    blockAdapter.notifyDataSetChanged();
                                    updateScore(DoNotRemove.getInstance().getScore());
                                } else {
                                    endGame();
                                }
                            }
                        } else if (Math.abs(event.getX() - x) < Math.abs(event.getY() - y)) {
                            if (event.getY() > y) {
                                Log.d(TAG, "onTouch: move down");
                                if (DoNotRemove.getInstance().checkCanMove()) {
                                    DoNotRemove.getInstance().swipeDown();
                                    blockAdapter.notifyDataSetChanged();
                                    updateScore(DoNotRemove.getInstance().getScore());
                                } else {
                                    endGame();
                                }
                            } else {
                                Log.d(TAG, "onTouch: move up");
                                if (DoNotRemove.getInstance().checkCanMove()) {
                                    DoNotRemove.getInstance().swipeUp();
                                    blockAdapter.notifyDataSetChanged();
                                    updateScore(DoNotRemove.getInstance().getScore());
                                } else {
                                    endGame();
                                }
                            }
                        }
                        break;
                }
                return true;
            } else {
                return false;
            }
        };
    }

    private ArrayList<Integer> getSavedData() {
        String[] str = Global.getCurrentData(getContext()).split(",");
        ArrayList<Integer> res = new ArrayList<>();
        for (String s : str) {
            res.add(Integer.valueOf(s));
        }
        return res;
    }

    private void endGame() {
        isEndGame = true;
        Log.d(TAG, "endGame: ");
        showEndgameDialog();
    }

    private void updateScore(int currentScore) {
        Log.d(TAG, "updateScore: currentScore : " + currentScore);
        updateTextView(tvScore, currentScore);
        if (currentScore > bestScore) {
            Log.d(TAG, "updateScore: new best score : " + currentScore);
            updateTextView(tvBestScore, currentScore);
            Global.saveBestScore(getContext(), currentScore);
        }
    }

    public void initView() {
        isEndGame = false;
        blockAdapter = new BlockAdapter(getContext(), R.layout.fragment_slow2048, DoNotRemove.getInstance().getListNumber());
        gvGame.setAdapter(blockAdapter);
        blockAdapter.notifyDataSetChanged();
        btnBack.setOnClickListener(this);
        gvGame.setOnTouchListener(onTouchListener);
        bestScore = Global.getBestScore(getContext());
        updateTextView(tvBestScore, bestScore);

        Animation inAni = AnimationUtils.loadAnimation(getContext(), R.anim.anim_slide_in_left);
        gvGame.setAnimation(inAni);

    }

    private void updateTextView(AutofitTextView textView, int text) {
        textView.setText(text + "");
    }

    public void showEndgameDialog() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_end_game, null);
        final Button btnHome = view.findViewById(R.id.btnHome);
        final Button btnReplay = view.findViewById(R.id.btnReplay);
        final TextView tvScoreEndGame = view.findViewById(R.id.tvScore);
        tvScoreEndGame.setText("Score : "+tvScore.getText().toString());

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                playViewListener.stopGame();
            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replay();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void replay() {
        dialog.dismiss();
        isEndGame = false;
        updateTextView(tvScore, 0);
        DoNotRemove.getInstance().resetScore();
        blockAdapter.clearData();
        initData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                showSaveGame();
                break;
        }
    }

    private void showSaveGame() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_save_game, null);
        final Button btnYes = view.findViewById(R.id.btnYes);
        final Button btnNo = view.findViewById(R.id.btnNo);
        final Button btnCancel = view.findViewById(R.id.btnCancel);
        final ConstraintLayout dialogMain = view.findViewById(R.id.dialogMain);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGame();
                dialog.dismiss();
                playViewListener.stopGame();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.saveResumeState(getContext(), false);
                dialog.dismiss();
                playViewListener.stopGame();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void saveGame() {
        ArrayList<Integer> saveData = blockAdapter.getData();
        if (saveData != null) {
            String dataString = Arrays.toString(saveData.toArray());
            Log.d(TAG, "saveData: " + dataString);
            Global.saveResumeState(getContext(), true);
            Global.saveCurrentData(getContext(), dataString);
            Global.saveCurrentScore(getContext(), Integer.parseInt(tvScore.getText().toString()));
        }
    }
}