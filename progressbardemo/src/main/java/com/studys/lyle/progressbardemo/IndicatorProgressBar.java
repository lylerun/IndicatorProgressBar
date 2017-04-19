package com.studys.lyle.progressbardemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by lyle on 2017/3/26.
 */

public class IndicatorProgressBar extends ProgressBar {
    private int mHeight;
    private int mWidth;

    public IndicatorProgressBar(Context context) {
        super(context);
    }

    public IndicatorProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public IndicatorProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        int minTextHeight = dp2px(15);
        //默认进度条最小高度为15dp,以便于完整包裹text文本
        if(MeasureSpec.getSize(heightMeasureSpec)<minTextHeight){
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(minTextHeight,
                    MeasureSpec.EXACTLY);
            mWidth= minTextHeight;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * @param canvas
     * 重写onDraw方法,添加进度文本
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();

        paint.setAntiAlias(true); //设置抗锯齿
        paint.setColor(Color.BLACK);
        paint.setTextSize(mHeight * 3 / 4); //以进度条的3/4高度作为text的文字大小
        paint.setTextAlign(Paint.Align.RIGHT); //从text的右下角开始画

        float descent = paint.descent();
        float ascent = paint.ascent();

        canvas.drawText(getProgressPercent(), mWidth - 3, mHeight * 3 / 4, paint);//将百分比显示在进度条的最右侧
    }

    /**
     * @return ProgressBar进度的百分比
     */
    public String getProgressPercent() {
        int percent = (int) (getProgress() * 100f / getMax() + 0.5f);
        return percent + "%";
    }

    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
