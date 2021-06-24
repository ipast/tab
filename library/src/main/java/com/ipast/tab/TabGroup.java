package com.ipast.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gang.cheng
 * @description :
 * @date :2021/6/1
 */
public class TabGroup extends LinearLayout implements View.OnClickListener {
    private List<TabButton> mList;
    private OnTabChangedListener mOnTabChangedListener;
    private int currentItem = 0;
    private int mSelectedColor;
    private int mUnSelectedColor;

    public TabGroup(Context context) {
        this(context, null);
    }

    public TabGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mList = new ArrayList<>();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabGroup);
        mSelectedColor = (int) ta.getColor(R.styleable.TabGroup_tab_selected_color, 0);
        mUnSelectedColor = (int) ta.getColor(R.styleable.TabGroup_tab_unselected_color, 0);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View v = getChildAt(i);
            v.setTag(i);
            v.setOnClickListener(this);
            TabButton tb = (TabButton) v;
            if (i == currentItem) {
                tb.setSelected(true);
                if (mSelectedColor != 0) {
                    tb.setBackgroundColor(mSelectedColor);
                }
            } else {
                tb.setSelected(false);
                if (mUnSelectedColor != 0) {
                    tb.setBackgroundColor(mUnSelectedColor);
                }
            }
            mList.add(tb);
        }
    }

    public void setCurrentItem(int currentItem) {
        if (this.currentItem == currentItem) {
            return;
        }
        this.currentItem = currentItem;
        TabButton tb;
        for (int i = 0; i < mList.size(); i++) {
            tb = mList.get(i);
            if (i == currentItem) {
                tb.setSelected(true);
                if (mSelectedColor != 0) {
                    tb.setBackgroundColor(mSelectedColor);
                }
            } else {
                mList.get(i).setSelected(false);
                if (mUnSelectedColor != 0) {
                    tb.setBackgroundColor(mUnSelectedColor);
                }
            }


        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null) {
            int position = (int) tag;
            if (currentItem == position) {
                return;
            }
            setCurrentItem(position);
            if (mOnTabChangedListener != null) {
                mOnTabChangedListener.onTabChanged(v,position);
            }
        }
    }

    @FunctionalInterface
    public interface OnTabChangedListener {
        void onTabChanged(View view,int position);
    }

    public void setOnTabChangedListener(OnTabChangedListener mOnTabChangedListener) {
        this.mOnTabChangedListener = mOnTabChangedListener;
    }

}
