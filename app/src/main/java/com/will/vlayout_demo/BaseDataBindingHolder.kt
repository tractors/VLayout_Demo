package com.will.vlayout_demo

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 方便 DataBinding 的使用
 */
open class BaseDataBindingHolder<BD : ViewDataBinding>(view: View) : BaseViewHolder(view) {

    val dataBinding = DataBindingUtil.bind<BD>(view)
}