package com.will.vlayout_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

/**
 * 基类适配器
 */
public abstract class BaseDelegateAdap extends DelegateAdapter.Adapter<BaseViewHolder> {
    private LayoutHelper mLayoutHelper;
    private int mCount = -1;
    private int mLayoutId = -1;
    private Context mContext;
    private int mViewTypeItem = -1;

    public BaseDelegateAdap(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        this.mContext = context;
        this.mCount = count;
        this.mLayoutHelper = layoutHelper;
        this.mLayoutId = layoutId;
        this.mViewTypeItem = viewTypeItem;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == mViewTypeItem){
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId,parent,false));
        }
        return null;
    }

    @Override
    public abstract void onBindViewHolder(@NonNull BaseViewHolder holder, int position);

    /**
     * 必须重写不然会出现滑动不流畅的情况
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mViewTypeItem;
    }

    //条目数量
    @Override
    public int getItemCount() {
        return mCount;
    }
}