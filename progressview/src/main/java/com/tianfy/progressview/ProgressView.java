package com.tianfy.progressview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


/**
 * @Package: com.tianfy.progressview
 * @ClassName: ProgressView
 * @Description: 自定义进度动画
 * @Author:tianfy
 * @CreateDate: 2020/11/25 14:06
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/25 14:06
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ProgressView extends View {
    //圆角值
    private float radius = 0;
    //动画持续时间 默认=1s
    private long duration = 1000;
    //stroke画笔
    private Paint strokePain;
    //solid画笔
    private Paint solidPaint;
    //ObjectAnimator
    private ObjectAnimator animator;
    //默认边框颜色
    private int storkeColor = 0xffffff;
    //默认填充颜色
    private int solidColor = 0x000000;
    //默认动画移动的值
    private float animatedValue = 0;
    //默认padding值
    private float padding = 4;

    public ProgressView(Context context) {
        super(context);
        init(context, null, -1);
    }


    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);

    }


    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0);

        storkeColor = typedArray.getColor(R.styleable.ProgressView_progressView_storkeColor, storkeColor);
        //填充颜色
        solidColor = typedArray.getColor(R.styleable.ProgressView_progressView_solidColor, solidColor);
        //圆角值
        float mRadius = typedArray.getDimension(R.styleable.ProgressView_progressView_radius, radius);
        //动画持续时间
        duration = (long) typedArray.getFloat(R.styleable.ProgressView_progressView_duration, this.duration);
        //默认padding
        float mPadding = (long) typedArray.getDimension(R.styleable.ProgressView_progressView_padding, this.padding);

        typedArray.recycle();
        /* dp转px */
        radius = dp2px(mRadius);

        padding = dp2px(mPadding);

        if (padding < 0) padding = 4;

        /* 初始化边框色的画笔 */
        initStrokePain(storkeColor);
        /* 初始化填充色的画笔 */
        initSloidPain(solidColor);
        /* 初始化动画 */
        initAnimator();
    }

    private void initStrokePain(int storkeColor) {
        strokePain = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePain.setAntiAlias(true);                  //设置画笔为无锯齿
        strokePain.setStyle(Paint.Style.STROKE);
        strokePain.setColor(storkeColor);                    //设置画笔颜色

    }

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 初始化画笔
     *
     * @param solidColor 填充颜色
     */
    private void initSloidPain(int solidColor) {
        solidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        solidPaint.setAntiAlias(true);                  //设置画笔为无锯齿
        solidPaint.setStyle(Paint.Style.FILL);
        solidPaint.setColor(solidColor);                    //设置画笔颜色
    }

    /**
     * 初始化动画
     */
    private void initAnimator() {
        animator = new ObjectAnimator();
        //设置动画属性
        animator.setPropertyName("progress");
        //设置执行动画的View
        animator.setTarget(this);
    }

    /**
     * 重写onMeasure方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //处理wrap_contentde情况
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(600, 60);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(600, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 60);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* 绘制边框 */
        RectF rectStrokeF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectStrokeF, radius, radius, strokePain);

        float left;
        float top;
        float right;
        float bottom;

        /* 绘制填充的圆角矩形 */
        if (animatedValue == 0) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        } else {
            left = padding;
            top = padding;
            right = animatedValue - padding;
            bottom = getHeight() - padding;
        }
        RectF rectSolidF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectSolidF, radius, radius, solidPaint);

    }


    /**
     * 设置填充色
     *
     * @param color
     */
    public void setSloidColor(int color) {
        this.solidColor = color;
        initSloidPain(solidColor);
    }

    /**
     * 设置边框的颜色
     *
     * @param color
     */
    public void setStorkeColor(int color) {
        this.storkeColor = color;
        initStrokePain(solidColor);
    }


    /**
     * 设置圆角值
     *
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = dp2px(radius);
    }

    /**
     * 设置动画持续时间
     *
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 设置边框和填充之间的间距
     *
     * @param padding
     */
    public void setPadding(long padding) {
        if (padding < 0) return;
        this.padding = dp2px(padding);
    }


    /**
     * 返回属性动画实例，可用于动画进度监听做后续操作
     *
     * @return
     */
    public ObjectAnimator getAnimator() {
        return animator;
    }


    //开始动画
    @SuppressLint("WrongConstant")
    public void startAnim() {
        //view.post方法，确保能正确获取到当前view自身的宽和高
        post(new Runnable() {
            @Override
            public void run() {
                if (animator.isRunning()) animator.end();
                //设置进度数组，  0 - max
                int width = getWidth();
                animator.setFloatValues(0, width);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
                //设置动画时间
                animator.setDuration(duration);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        animatedValue = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                //动画开启
                animator.start();
            }
        });
    }

    /**
     * 取消动画
     * 动画在那个位置取消，就停在那个位置
     */
    public void cancelAnim() {
        if (animator.isRunning()) animator.cancel();
    }

    /**
     * 结束动画
     * 动画回到最初的位置，并停止动画
     */
    public void endAnim() {
        if (animator.isRunning()) animator.end();
        animatedValue = 0;
        invalidate();
    }


}