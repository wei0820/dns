package com.app.android.app.Common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.app.android.app.R;


/**
 * 循环加载进度条
 */

public class LoadingView extends View {


    /**
     * 默认高亮颜色
     */
    private static final int DEFAULT_LIGHT_COLOR = Color.parseColor("#8BC34A");
    /**
     * 默认背景颜色
     */
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#BDBDBD");

    /**
     * 默认加载速度
     */
    private static final int DEFAULT_VELOCITY = 15;


    /**
     * 高亮色
     */
    private int mLightColor = DEFAULT_LIGHT_COLOR;
    /**
     * 背景色
     */
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;

    /**
     * 速度
     */
    private int mVelocity = DEFAULT_VELOCITY;

    private Paint mPaintLight;
    private Paint mPaintBackground;
    private boolean mStarting;
    private String mMessage = "执行中";

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    /**
     * 初始化获取自定义变量
     *
     * @param context
     * @param attrs
     */
    private void initData(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        for (int i = 0; i < typedArray.length(); i++) {
            int att = typedArray.getIndex(i);
            switch (att) {
                case R.styleable.LoadingView_lightColor:
                    mLightColor = typedArray.getColor(att, DEFAULT_LIGHT_COLOR);
                    break;
                case R.styleable.LoadingView_backgroundColor:
                    mBackgroundColor = typedArray.getColor(att, DEFAULT_BACKGROUND_COLOR);
                    break;
                case R.styleable.LoadingView_velocity:
                    mVelocity = typedArray.getInteger(att, DEFAULT_VELOCITY);
                    break;
            }
        }
        typedArray.recycle();
        mPaintLight = new Paint();
        mPaintLight.setAntiAlias(true);
        mPaintLight.setColor(mLightColor);

        mPaintBackground = new Paint();
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setColor(mBackgroundColor);
    }

    /**
     * View的总宽度
     */
    int measuredWidth;

    /**
     * View的总高度
     */
    int measuredHeight;

    /**
     * 高亮右边
     */
    int lightRight;
    /**
     * 高亮左边
     */
    int lightLeft;
    /**
     * 高亮宽度
     */
    int lightWidth;


    /**
     * true  : 向右
     * false : 向左
     */
    boolean orientation = true;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        /**
         * 高亮宽度设置为View宽的20%
         */
        lightWidth = (int) (measuredWidth * 0.2);
        lightRight = lightWidth;
        lightLeft = 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        /**
         * 先背景
         */
        RectF rectFBackground = new RectF(0, 0, measuredWidth, measuredHeight);
        canvas.drawRect(rectFBackground, mPaintBackground);


        /**
         * 再画高亮
         */
        RectF rectFLight = new RectF(lightLeft, 0, lightRight, measuredHeight);
        canvas.drawRect(rectFLight, mPaintLight);


        /**
         * 超过View宽就往回加载
         */
        if (lightRight >= measuredWidth) {
            orientation = false;
        }
        /**
         * 低于屏幕左边则往前加载
         */
        if (lightLeft <= 0) {
            orientation = true;
        }
        /**
         * 执行增加操作
         */
        if (orientation) {
            lightRight = lightRight + mVelocity;
            lightLeft = lightLeft + mVelocity;
        } else {
            lightLeft = lightLeft - mVelocity;
            lightRight = lightRight - mVelocity;

        }

        /**
         * 更新View 循环加载
         */
        if (mStarting) {
            postInvalidate();
        }
    }

    /**
     * 传进来以后 就可以用 getMessage
     * 确保每次都只是一个消息
     *
     * @param msg
     */
    public void start(String msg) {
        this.mMessage = msg;
        setVisibility(VISIBLE);
        mStarting = true;
        postInvalidate();
    }

    public void stop() {
        setVisibility(GONE);
        mStarting = false;
    }

    public boolean getStatus() {
        return mStarting;
    }


    public String getMessage() {
        return mMessage;
    }
}
