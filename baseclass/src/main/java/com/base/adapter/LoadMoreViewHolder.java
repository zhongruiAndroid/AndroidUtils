package com.base.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/15.
 */

public class LoadMoreViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;//集合类，layout里包含的View,以view的id作为key，value是view对象
    private Context mContext;//上下文对象
    BottomView bottomView;
    public LoadMoreViewHolder(Context ctx, View itemView) {
        super(itemView);
        mContext = ctx;
        mViews = new SparseArray<View>();
        if(itemView instanceof BottomView){
            bottomView= (BottomView) itemView;
        }
    }
    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    public <V extends View> V getViewByType(Class<V> a, int viewId) {
        return (V) getView(viewId);
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }

    public LoadMoreViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public LoadMoreViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public LoadMoreViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    public static class BottomView extends LinearLayout {
        public BottomView(Context context) {
            super(context);
        }
        public BottomView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }
        public BottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
    }

}
