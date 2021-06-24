package com.ipast.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author gang.cheng
 * @description :
 * @date :2021/6/1
 */
public class TabButton extends LinearLayout {
    private float mScale;
    private int mDrawable;
    private int mUnSelectedDrawable;
    private String mText;
    private int mIconSize;
    private int mTextSize;
    private int mTextSizeUnSelected;
    private int mTextColor;
    private int mTextColorUnSelected;
    private int mDrawableMargin;
    private boolean mSelected;
    private ImageView mImageView;
    private TextView mTextView;
    private boolean reverse;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScale = context.getResources().getDisplayMetrics().density;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabButton);
        mDrawable = ta.getResourceId(R.styleable.TabButton_tab_icon, mDrawable);
        mUnSelectedDrawable = ta.getResourceId(R.styleable.TabButton_tab_icon_unselected, mDrawable);
        mText = ta.getString(R.styleable.TabButton_tab_text);
        mIconSize = (int) ta.getDimension(R.styleable.TabButton_tab_icon_size, 20);
        mTextSize = (int) ta.getDimension(R.styleable.TabButton_tab_text_size, 13);
        mTextSizeUnSelected = (int) ta.getDimension(R.styleable.TabButton_tab_text_size_unselected, mTextSize);
        mTextColor = (int) ta.getColor(R.styleable.TabButton_tab_text_color, Color.BLACK);
        mTextColorUnSelected = (int) ta.getColor(R.styleable.TabButton_tab_text_color_unselected, mTextColor);
        mDrawableMargin = (int) ta.getDimension(R.styleable.TabButton_tab_drawable_margin, 3);
        mSelected = ta.getBoolean(R.styleable.TabButton_tab_selected, false);
        reverse = ta.getBoolean(R.styleable.TabButton_tab_reverse, false);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(getOrientation());

        LayoutParams params1 = new LayoutParams(mIconSize, mIconSize);
        LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = dp2px(mDrawableMargin);

        if (getOrientation() == VERTICAL) {
            if (!reverse) {
                params1.setMargins(0, 0, 0, margin);
                params2.setMargins(0, 0, 0, 0);
            } else {
                params1.setMargins(0, 0, 0, 0);
                params2.setMargins(0, 0, 0, margin);
            }
        } else {
            if (!reverse) {
                params1.setMargins(0, 0, 0, 0);
                params2.setMargins(margin, 0, 0, 0);
            } else {
                params1.setMargins(margin, 0, 0, 0);
                params2.setMargins(0, 0, 0, 0);
            }

        }
        mImageView = new ImageView(getContext());
        mImageView.setLayoutParams(params1);

        mTextView = new TextView(getContext());
        mTextView.setLayoutParams(params2);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        TextPaint tp = mTextView.getPaint();
        tp.setFakeBoldText(true);
        mTextView.setText(mText);

        if (mSelected) {
            mImageView.setImageResource(mDrawable);
            mTextView.setTextColor(mTextColor);
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        } else {
            mImageView.setImageResource(mUnSelectedDrawable);
            mTextView.setTextColor(mTextColorUnSelected);
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeUnSelected);
        }
        if (!reverse) {
            addView(mImageView);
            addView(mTextView);
        } else {
            addView(mTextView);
            addView(mImageView);
        }

    }

    public void setSelected(boolean selected) {
        if (selected) {
            mImageView.setImageResource(mDrawable);
            mTextView.setTextColor(mTextColor);
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        } else {
            mImageView.setImageResource(mUnSelectedDrawable);
            mTextView.setTextColor(mTextColorUnSelected);
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeUnSelected);
        }
    }

    private int dp2px(int dpVal) {
        return (int) (mScale * dpVal + 0.5f);
    }

}
