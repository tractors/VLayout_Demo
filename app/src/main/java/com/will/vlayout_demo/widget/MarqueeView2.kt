package com.will.vlayout_demo.widget

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.annotation.AnimRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.sunfusheng.marqueeview.IMarqueeItem
import com.sunfusheng.marqueeview.MarqueeView
import com.sunfusheng.marqueeview.Utils
import com.will.vlayout_demo.R

/**
 * 重新定义MarqueeView 使其支持kotlin
 */
class MarqueeView2<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): ViewFlipper(context, attrs) {
    private var interval = 3000
    private var hasSetAnimDuration = false
    private var animDuration = 1000
    private var textSize = 14
    private var textColor = -0x1000000
    private var singleLine = false
    private var gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
    private var direction = DIRECTION_BOTTOM_TO_TOP
    private var typeface: Typeface? = null

    @AnimRes
    private var inAnimResId = R.anim.anim_bottom_in

    @AnimRes
    private var outAnimResId = R.anim.anim_top_out
    private var position = 0
    private var messages: MutableList<T> = ArrayList()
    private var onItemClickListener: MarqueeView.OnItemClickListener? = null
    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0)
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval)
        hasSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration)
        animDuration =
            typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration)
        singleLine = typedArray.getBoolean(R.styleable.MarqueeViewStyle_mvSingleLine, false)
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = typedArray.getDimension(
                R.styleable.MarqueeViewStyle_mvTextSize,
                textSize.toFloat()
            ).toInt()
            textSize = Utils.px2sp(context, textSize.toFloat())
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor)
        @FontRes val fontRes = typedArray.getResourceId(R.styleable.MarqueeViewStyle_mvFont, 0)
        if (fontRes != 0) {
            typeface = ResourcesCompat.getFont(context, fontRes)
        }
        val gravityType = typedArray.getInt(
            R.styleable.MarqueeViewStyle_mvGravity,
            GRAVITY_LEFT
        )
        when (gravityType) {
            GRAVITY_LEFT -> gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
            GRAVITY_CENTER -> gravity = Gravity.CENTER
            GRAVITY_RIGHT -> gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        }
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvDirection)) {
            direction = typedArray.getInt(R.styleable.MarqueeViewStyle_mvDirection, direction)
            when (direction) {
                DIRECTION_BOTTOM_TO_TOP -> {
                    inAnimResId = R.anim.anim_bottom_in
                    outAnimResId = R.anim.anim_top_out
                }
                DIRECTION_TOP_TO_BOTTOM -> {
                    inAnimResId = R.anim.anim_top_in
                    outAnimResId = R.anim.anim_bottom_out
                }
                DIRECTION_RIGHT_TO_LEFT -> {
                    inAnimResId = R.anim.anim_right_in
                    outAnimResId = R.anim.anim_left_out
                }
                DIRECTION_LEFT_TO_RIGHT -> {
                    inAnimResId = R.anim.anim_left_in
                    outAnimResId = R.anim.anim_right_out
                }
            }
        } else {
            inAnimResId = R.anim.anim_bottom_in
            outAnimResId = R.anim.anim_top_out
        }
        typedArray.recycle()
        flipInterval = interval
    }
    /**
     * 根据字符串，启动翻页公告
     *
     * @param notice       字符串
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    /**
     * 根据字符串，启动翻页公告
     *
     * @param notice 字符串
     */
    @JvmOverloads
    fun startWithText(
        notice: String,
        @AnimRes inAnimResId: Int = this.inAnimResId,
        @AnimRes outAnimResID: Int = outAnimResId
    ) {
        if (TextUtils.isEmpty(notice)) return
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                startWithFixedWidth(notice, inAnimResId, outAnimResID)
            }
        })
    }

    /**
     * 根据字符串和宽度，启动翻页公告
     *
     * @param notice 字符串
     */
    private fun startWithFixedWidth(
        notice: String,
        @AnimRes inAnimResId: Int,
        @AnimRes outAnimResID: Int
    ) {
        val noticeLength = notice.length
        val width = Utils.px2dip(context, width.toFloat())
        if (width == 0) {
            throw RuntimeException("Please set the width of MarqueeView !")
        }
        val limit = width / textSize
        val list: MutableList<T> = arrayListOf()
        if (noticeLength <= limit) {
            list.add(notice as T)
        } else {
            val size = noticeLength / limit + if (noticeLength % limit != 0) 1 else 0
            for (i in 0 until size) {
                val startIndex = i * limit
                val endIndex =
                    if ((i + 1) * limit >= noticeLength) noticeLength else (i + 1) * limit
                list.add(notice.substring(startIndex, endIndex) as T)
            }
        }
        if (messages == null) messages = arrayListOf()
        messages.clear()
        messages.addAll(list)
        postStart(inAnimResId, outAnimResID)
    }
    /**
     * 根据字符串列表，启动翻页公告
     *
     * @param messages      字符串列表
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    /**
     * 根据字符串列表，启动翻页公告
     *
     * @param messages 字符串列表
     */
    @JvmOverloads
    fun startWithList(
        messages: MutableList<T>?,
        @AnimRes inAnimResId: Int = this.inAnimResId,
        @AnimRes outAnimResID: Int = outAnimResId
    ) {
        if (Utils.isEmpty(messages)) return
        setMessages(messages)
        postStart(inAnimResId, outAnimResID)
    }

    private fun postStart(@AnimRes inAnimResId: Int, @AnimRes outAnimResID: Int) {
        post { start(inAnimResId, outAnimResID) }
    }

    private var isAnimStart = false
    private fun start(@AnimRes inAnimResId: Int, @AnimRes outAnimResID: Int) {
        removeAllViews()
        clearAnimation()
        // 检测数据源
        if (messages.isEmpty()) {
            throw RuntimeException("The messages cannot be empty!")
        }
        position = 0
        addView(createTextView(messages[position]))
        if (messages.size > 1) {
            setInAndOutAnimation(inAnimResId, outAnimResID)
            startFlipping()
        }
        if (inAnimation != null) {
            inAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    if (isAnimStart) {
                        animation.cancel()
                    }
                    isAnimStart = true
                }

                override fun onAnimationEnd(animation: Animation) {
                    position++
                    if (position >= messages.size) {
                        position = 0
                    }
                    val view: View = createTextView(messages.get(position))
                    if (view.parent == null) {
                        addView(view)
                    }
                    isAnimStart = false
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    private fun createTextView(marqueeItem: T): TextView {

        val view = getChildAt((displayedChild+1) % 3)
        var textView: TextView? = null
        if (view  != null){
            textView = view as TextView
        } else {
            textView = TextView(context)
            textView.gravity = gravity or Gravity.CENTER_VERTICAL
            textView.setTextColor(textColor)
            textView.textSize = textSize.toFloat()
            textView.includeFontPadding = true
            textView.isSingleLine = singleLine
            if (singleLine) {
                textView.maxLines = 1
                textView.ellipsize = TextUtils.TruncateAt.END
            }
            if (typeface != null) {
                textView.typeface = typeface
            }
            textView.setOnClickListener { v ->
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(getPosition(), v as TextView)
                }
            }
        }
        var message: CharSequence? = ""
        if (marqueeItem is CharSequence) {
            message = marqueeItem
        } else if (marqueeItem is IMarqueeItem) {
            message = (marqueeItem as IMarqueeItem).marqueeMessage()
        }
        textView.text = message
        textView.tag = position
        return textView
    }

    fun getPosition(): Int {
        return currentView.tag as Int
    }

    fun getMessages(): List<T>? {
        return messages
    }

    fun setMessages(messages: MutableList<T>?) {
        if (messages != null) {
            this.messages = messages
        }
    }

    fun setOnItemClickListener(onItemClickListener: MarqueeView.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, textView: TextView)
    }

    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private fun setInAndOutAnimation(
        @AnimRes inAnimResId: Int,
        @AnimRes outAnimResID: Int
    ) {
        val inAnim =
            AnimationUtils.loadAnimation(context, inAnimResId)
        if (hasSetAnimDuration) inAnim.duration = animDuration.toLong()
        inAnimation = inAnim
        val outAnim =
            AnimationUtils.loadAnimation(context, outAnimResID)
        if (hasSetAnimDuration) outAnim.duration = animDuration.toLong()
        outAnimation = outAnim
    }

    fun setTypeface(typeface: Typeface?) {
        this.typeface = typeface
    }

    companion object {
        private const val GRAVITY_LEFT = 0
        private const val GRAVITY_CENTER = 1
        private const val GRAVITY_RIGHT = 2
        private const val DIRECTION_BOTTOM_TO_TOP = 0
        private const val DIRECTION_TOP_TO_BOTTOM = 1
        private const val DIRECTION_RIGHT_TO_LEFT = 2
        private const val DIRECTION_LEFT_TO_RIGHT = 3
    }

    init {
        init(context, attrs, 0)
    }
}