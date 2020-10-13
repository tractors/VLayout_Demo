package com.will.vlayout_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper

/**
 * 基类适配器
 */
abstract class BaseDelegateAdapter(private val context: Context,private val layoutHelper: LayoutHelper,layoutId:Int,count:Int,viewTypeItem:Int) :
    DelegateAdapter.Adapter<BaseViewHolder>() {

    private val mLayoutHelper: LayoutHelper
    private var mCount = -1
    private var mLayoutId = -1
    private var mViewTypeItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return (if (viewType == mViewTypeItem){
            BaseViewHolder(LayoutInflater.from(context).inflate(mLayoutId,parent,false))
        } else null)!!
    }

    /**
     * 条目数量
     */
    override fun getItemCount(): Int {
        return mCount
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return mLayoutHelper
    }

    abstract override fun  onBindViewHolder(holder: BaseViewHolder, position: Int)

    /**
     * 必须重写不然会出现滑动不流畅的情况
     */
    override fun getItemViewType(position: Int): Int {
        return mViewTypeItem
    }

    init {
        mCount = count
        mLayoutHelper = layoutHelper
        mLayoutId = layoutId
        mViewTypeItem = viewTypeItem
    }
}