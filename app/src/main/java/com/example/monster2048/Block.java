package com.example.monster2048;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class Block extends androidx.appcompat.widget.AppCompatTextView {
    public Block(Context context) {
        super(context);
    }

    public Block(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Block(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    public void setTextBlock(int value) {
        if (value < 100) {
            setTextSize(40);
        } else if (value < 1000) {
            setTextSize(35);
        } else {
            setTextSize(30);
        }

        if (value < 8) {
            setTextColor(Color.BLACK);
        } else {
            setTextColor(Color.WHITE);
        }

        GradientDrawable gradientDrawable = (GradientDrawable) this.getBackground();
        gradientDrawable.setColor(Data.getData().colorValue(value));
        setBackground(gradientDrawable);

        if (value == 0) {
            setText(" ");
        } else {
            setText(value + "");
        }
    }
}
