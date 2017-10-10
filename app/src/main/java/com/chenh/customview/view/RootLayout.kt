package com.chenh.customview.view

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chenh.customview.R

/**
 * 定制化根布局
 * @author chenh
 */
class RootLayout: ConstraintLayout {

    private var mContext: Context? = null
    //提示信息
    private var mTipTextView: TextView? = null
    //提示图表
    private var mTipImageView: ImageView? = null

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
    }

    /**
     * 当布局加载完毕
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        //如果没有在布局中设置id，则在这里提供磨人设置
        if (id == -1) {
            id = R.id.root_layout
        }
        init()
    }

    /**
     * 初始化
     */
    private fun init() {
        mTipTextView = TextView(mContext)
        mTipTextView?.id = R.id.tip_textview
        mTipTextView?.textSize = mConfig.txtSize
        mTipTextView?.setTextColor(mConfig.txtColor)

        mTipImageView = ImageView(mContext)
        mTipImageView?.id = R.id.tip_imageview
        mTipImageView?.scaleType = ImageView.ScaleType.CENTER_CROP
        mTipImageView?.visibility = View.VISIBLE
        val lpImg = ConstraintLayout.LayoutParams(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40F, mContext?.resources?.displayMetrics).toInt(),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40F, mContext?.resources?.displayMetrics).toInt())
        lpImg.leftToLeft = id
        lpImg.rightToRight = id
        lpImg.topToTop = id
        lpImg.bottomToBottom = id

        val lpTxt = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lpTxt.topToBottom = mTipImageView?.id!!
        lpTxt.leftToLeft = id
        lpTxt.rightToRight = id
        lpTxt.topMargin = 10

        addView(mTipImageView, lpImg)
        addView(mTipTextView, lpTxt)
    }

    private fun hide() {
        //获取组件数量
        val childCount = childCount
        var i = 0;
        while (i < childCount) {
            if ((getChildAt(i).id != mTipImageView?.id) && (getChildAt(i).id != mTipTextView?.id)) {
                getChildAt(i).visibility = View.GONE
            }
            i++;
        }
    }

    /**
     * 显示错误信息
     */
    public fun showError() {
        hide()
        mTipImageView?.setImageResource(mConfig.errorImgId)
        mTipTextView?.text = mConfig.errorMsg
    }

    /**
     * 重载方法(显示错误信息)
     */
    public fun showError(errorMsg: String) {
        hide()
        mTipImageView?.setImageResource(mConfig.errorImgId)
        mTipTextView?.text = errorMsg
    }

    /**
     * 重载方法(显示错误信息)
     */
    public fun showError(imgId: Int, errorMsg: String) {
        hide()
        mTipImageView?.setImageResource(imgId)
        mTipTextView?.text = errorMsg
    }

    /**
     * 没有数据时显示
     */
    public fun showEmpty() {
        hide()
        mTipImageView?.setImageResource(mConfig.emptyImgId)
        mTipTextView?.text = mConfig.emptyMsg
    }

    public fun showEmpty(emptyMsg: String) {
        hide()
        mTipImageView?.setImageResource(mConfig.emptyImgId)
        mTipTextView?.text = emptyMsg
    }

    public fun showEmpty(imgId: Int, emptyMsg: String) {
        hide()
        mTipImageView?.setImageResource(imgId)
        mTipTextView?.text = emptyMsg
    }

    /**
     * 没有网络时显示
     */
    public fun showNetwork() {
        hide()
        mTipImageView?.setImageResource(mConfig.networkImgId)
        mTipTextView?.text = mConfig.networkMsg
    }

    public fun showNetwork(metworkMsg: String) {
        hide()
        mTipImageView?.setImageResource(mConfig.networkImgId)
        mTipTextView?.text = metworkMsg
    }

    public fun showNetwork(imgId: Int, metworkMsg: String) {
        hide()
        mTipImageView?.setImageResource(imgId)
        mTipTextView?.text = metworkMsg
    }

    /**
     * 设置字体大小
     */
    public fun setTextSize(value: Float): RootLayout {
        mTipTextView?.textSize = value
        return this
    }

    /**
     * 设置字体颜色
     */
    public fun setTextColor(color: Int): RootLayout {
        mTipTextView?.setTextColor(color)
        return this
    }

    companion object {

        private val mConfig = Config()

        /**
         * 获取配置类
         */
        public fun getConfig(): Config = mConfig

        class Config {

            var errorMsg: String = "请求错误"
                private set
            var emptyMsg = "数据为空"
                private set
            var networkMsg = "无法链接网络"
                private set
            var errorImgId = R.mipmap.error
                private set
            var emptyImgId = R.mipmap.empty
                private set
            var networkImgId = R.mipmap.no_network
                private set
            var txtSize = 14F
                private set
            var txtColor = Color.parseColor("#999999")
                private set

            public fun setTextSize(value: Float): Config {
                txtSize = value
                return this
            }

            public fun setTextColor(color: Int): Config {
                txtColor = color
                return this
            }

            public fun setErrorTipMsg(errorMsg: String): Config {
                this.errorMsg = errorMsg
                return this
            }

            public fun setEmptyTipMsg(emptyMsg: String): Config {
                this.emptyMsg = emptyMsg
                return this
            }

            public fun setNetworkTipMsg(networkMsg: String): Config {
                this.networkMsg = networkMsg
                return this
            }

            public fun setErrorTipIcon(iconId: Int): Config {
                errorImgId = iconId
                return this
            }

            public fun setEmptyTipIcon(iconId: Int): Config {
                emptyImgId = iconId
                return this
            }

            public fun setNetworkTipIcon(iconId: Int): Config {
                networkImgId = iconId
                return this
            }

        }
    }
}