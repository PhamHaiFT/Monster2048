package com.example.monster2048;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonsterFragment extends Fragment {

    @BindView(R.id.gvGame)
    GridView gvGame;
    @BindView(R.id.tvScore)
    TextView tvScore;

    private BlockAdapter blockAdapter;

    private View.OnTouchListener onTouchListener;
    private float x, y;

    private static final String TAG = "MonsterFragment";

    public MonsterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monster, container, false);
        ButterKnife.bind(this, view);

        initData();
        init();

        return view;
    }

    public void initData() {
        tvScore.setText("0");
        Data.getData().init(getContext());
        onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getX() - x) > Math.abs(event.getY() - y)) {
                            if (event.getX() > x) {
                                Log.d(TAG, "onTouch: Vuốt sang phải");
                                if (Data.getData().checkCanMove()) {
                                    Data.getData().swipeRight();
                                    blockAdapter.notifyDataSetChanged();
                                    tvScore.setText(Data.getData().getScore() + "");
                                } else {
                                    Toasty.error(getContext(), "GAME OVER", 1000).show();
                                }
                            } else {
                                Log.d(TAG, "onTouch: Vuốt sang trái");
                                if (Data.getData().checkCanMove()) {
                                    Data.getData().swipeLeft();
                                    blockAdapter.notifyDataSetChanged();
                                    tvScore.setText(Data.getData().getScore() + "");
                                } else {
                                    Toasty.error(getContext(), "GAME OVER", 1000).show();
                                }
                            }
                        } else if (Math.abs(event.getX() - x) < Math.abs(event.getY() - y)) {
                            if (event.getY() > y) {
                                Log.d(TAG, "onTouch: Vuốt xuống dưới");
                                if (Data.getData().checkCanMove()) {
                                    Data.getData().swipeDown();
                                    blockAdapter.notifyDataSetChanged();
                                    tvScore.setText(Data.getData().getScore() + "");
                                } else {
                                    Toasty.error(getContext(), "GAME OVER", 1000).show();
                                }
                            } else {
                                Log.d(TAG, "onTouch: Vuốt lên trên");
                                if (Data.getData().checkCanMove()) {
                                    Data.getData().swipeUp();
                                    blockAdapter.notifyDataSetChanged();
                                    tvScore.setText(Data.getData().getScore() + "");
                                } else {
                                    Toasty.error(getContext(), "GAME OVER", 1000).show();
                                }

                            }
                        }
                        break;
                }
                return true;
            }
        }

        ;
    }

    public void init() {
        blockAdapter = new BlockAdapter(getContext(), R.layout.fragment_monster, Data.getData().getListNumber());
        gvGame.setAdapter(blockAdapter);
//        gvGame.setEnabled(false);
        blockAdapter.notifyDataSetChanged();
        gvGame.setOnTouchListener(onTouchListener);
    }
}
