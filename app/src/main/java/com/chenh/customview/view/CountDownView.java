package com.chenh.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.chenh.customview.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jay on 2017/10/3.
 */

public class CountDownView extends View {

    //绘制内圆的画笔对象
    private Paint mInCirclePaint = new Paint();
    //绘制文字的画笔对象
    private Paint mTxtPaint = new Paint();
    //绘制圆弧的画笔对象
    private Paint mArcPaint = new Paint();

    //计时类
    private Timer mTimer = null;
    //外部圆当前绘制的弧度
    private int currentAngle = 360;
    //外部圆最终绘制的弧度
    private int progress = 0;
    //当前的描述，这里默认为4秒，不可修改
    private int currentMillon = 4;

    //外部圆的背景颜色
    private int arcCircleColor;
    //内部圆的背景颜色
    private int inCircleColor;
    //文字的颜色
    private int txtTimeColor;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownView,
                defStyleAttr, 0);
        arcCircleColor = a.getColor(R.styleable.CountDownView_arc_circle_color, Color.RED);
        inCircleColor = a.getColor(R.styleable.CountDownView_in_circle_color, Color.parseColor
                ("#FFB7B6B6"));
        txtTimeColor = a.getColor(R.styleable.CountDownView_txt_time_color, Color.WHITE);
        a.recycle();
        init();
    }

    private void init() {
        mTimer = new Timer();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            if (getLayoutParams().height == WindowManager.LayoutParams.WRAP_CONTENT) {
                height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            } else {
                height = heightSize;
            }
        }

        if (width <= height) {
            setMeasuredDimension(width, width);
        } else {
            setMeasuredDimension(height, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取屏幕的宽度
        int width = getMeasuredWidth();
        //获取屏幕的高度
        int height = getMeasuredHeight();
        //绘制内部的圆
        mInCirclePaint.setStyle(Paint.Style.FILL);
        mInCirclePaint.setAntiAlias(true);
        mInCirclePaint.setColor(inCircleColor);
        canvas.drawCircle(width / 2, height / 2, width / 2 - 10, mInCirclePaint);

        //绘制文字
        int mTxtSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics());
        mTxtPaint.setTextSize(mTxtSize);
        Rect mBound = new Rect();
        mTxtPaint.getTextBounds(String.valueOf(currentMillon), 0, String.valueOf(currentMillon)
                .length(), mBound);
        mTxtPaint.setColor(txtTimeColor);
        canvas.drawText(String.valueOf(currentMillon), width / 2 - mBound.width() / 2, height / 2
                + mBound.height() / 2, mTxtPaint);

        // 绘制圆弧
        mArcPaint.setStrokeWidth(10);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(arcCircleColor);
        RectF rect = new RectF(10, 10, width - 10, height - 10);
        canvas.drawArc(rect, -90, currentAngle, false, mArcPaint);
    }

    /**
     * 动画开始
     */
    public void start() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
                if (currentAngle <= progress) {
                    //到这里，自动执行的任务已经结束，在这里我们可以定义回调接口来进行特定的处理
                    mTimer.cancel();
                    if (mCallback != null) {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.onComplete();
                            }
                        });
                    }
                } else {
                    currentAngle -= 5;
                }
                if (currentAngle % 90 == 0 && currentMillon > 0) {
                    currentMillon--;
                }
            }
        }, 50, 50);
    }

    /**
     * 重载方法，增加动画完成的监听
     *
     * @param callback
     */
    public void start(final OnCountDownCallback callback) {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
                if (currentAngle <= progress) {
                    //到这里，自动执行的任务已经结束，在这里我们可以定义回调接口来进行特定的处理
                    mTimer.cancel();
                    if (callback != null) {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onComplete();
                            }
                        });
                    }
                } else {
                    currentAngle -= 5;
                }
                if (currentAngle % 90 == 0 && currentMillon > 0) {
                    currentMillon--;
                }
            }
        }, 50, 50);
    }

    private OnCountDownCallback mCallback;

    public void setOnCountDownCallback(OnCountDownCallback callback) {
        mCallback = callback;
    }

    public interface OnCountDownCallback {
        public void onComplete();
    }
}
