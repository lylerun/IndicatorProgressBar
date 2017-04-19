# IndicatorProgressBar
自定义的带进度百分比显示的IndicatorProgressBar以及一个demo

## demo的效果:
![](http://i.imgur.com/IkLzTcR.png)

##IndicatorProgressBar源码要点;

1.在drawable中重新定义ProgressBar的背景及进度条的显示效果progress,在布局中直接引入即可 android:progressDrawable="@drawable/progress"

	<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
	
	    <item android:id="@android:id/background">
	        <shape>
	            <corners android:radius="2dp"/>
	            <solid android:color="#ccc"/>
	        </shape>
	    </item>
	
	    <item android:id="@android:id/secondaryProgress">
	        <clip>
	            <shape>
	                <corners android:radius="2dp"/>
	                <solid android:color="#77f"/>
	            </shape>
	        </clip>
	    </item>
	
	    <item android:id="@android:id/progress">
	        <clip>
	            <shape>
	                <corners android:radius="2dp"/>
	                <gradient
	                    android:angle="90.0"
	                    android:centerColor="#056CEB"
	                    android:endColor="#12509C"
	                    android:startColor="#12509C"/>
	            </shape>
	        </clip>
	    </item>
	
	</layer-list>

2.自定义IndicatorProgressBar继承ProgressBar,重写onMeasure(),onMeasure()方法中先测量控件给定的高度,高度小于默认高度时使用默认高度作为控件高度,否则直接使用控件高度. 

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

2.重写onDraw()方法中使用super.onDraw(canvas)之外,再在进度条最右侧绘制一个显示实时进度的文本,文本高度默认为进度条高度的3/4,文本内容为进度百分比,定义一个方法获取当前进度百分比即可.

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

3.用到了2个小方法
	
	/**
     * @return ProgressBar进度的百分比
     */
    public String getProgressPercent() {
        int percent = (int) (getProgress() * 100f / getMax() + 0.5f);
        return percent + "%";
    }

    /**
     * dp转px的小方法
     */
    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

