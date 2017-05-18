package com.rain.wr_weixin_60;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created  on 2017/5/18.
 *
 * @author Rain
 */

public class ChangeColorIconWithText extends View {

    private int mColor = 0xff45c01a;
    private Bitmap mIconBitmap;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,
            getResources().getDisplayMetrics());//默认12sp

    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private Rect mIconRect;
    private Rect mTextBounds;
    private float mAlpha ;//测试用
    private Paint mTextPaint;
    public ChangeColorIconWithText(Context context) {
        this(context,null);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * 获取自定义属性的值
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);
        mTextSize = (int) a.getDimension(R.styleable.ChangeColorIconWithText_text_size,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,
                getResources().getDisplayMetrics()));
        mColor = a.getColor(R.styleable.ChangeColorIconWithText_color,0xff45c01a);
        mText = a.getString(R.styleable.ChangeColorIconWithText_text);
        mIconBitmap =((BitmapDrawable) a.getDrawable(R.styleable.ChangeColorIconWithText_icon)).getBitmap();
        a.recycle();

        mTextBounds = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff555555);

        mTextPaint.getTextBounds(mText,0,mText.length(),mTextBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
                getMeasuredHeight() - getPaddingTop()-getPaddingBottom()-mTextBounds.height());
        int left = getMeasuredWidth()/2 - iconWidth/2;
        int top = (getMeasuredHeight()-mTextBounds.height()-iconWidth)/2;
        mIconRect = new Rect(left,top,left+iconWidth,top+iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap,null,mIconRect,null);
        int alpha = (int) Math.ceil(255*mAlpha);
        //内存去准备mBitmap  setalpha  纯色  xfermode  图标
        setupTargetBitmap(alpha);

        //绘制原文本  绘制变色的文本

        drawSourceText(canvas,alpha);

        drawTargetText(canvas,alpha);
        canvas.drawBitmap(mBitmap,0,0,null);
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth()/2 - mTextBounds.width()/2;
        int y = mIconRect.bottom + mTextBounds.height();
        canvas.drawText(mText,x,y,mTextPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255-alpha);
        int x = getMeasuredWidth()/2 - mTextBounds.width()/2;
        int y = mIconRect.bottom + mTextBounds.height();
        canvas.drawText(mText,x,y,mTextPaint);
    }

    private void setupTargetBitmap(int alpha) {

        //绘制和icon一样大的纯色区域
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setAlpha(alpha);
        mPaint.setDither(true);
        mCanvas.drawRect(mIconRect,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap,null,mIconRect,mPaint);
        Log.d("maii","setupTargetBitmap");
    }

    public void setIconAlpha(float alpha){
        this.mAlpha = alpha;
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if(Looper.getMainLooper() == Looper.myLooper()){
            invalidate();
        }else{
            postInvalidate();
        }
    }

    private static final String INSTANCE_STAUS = "instance_staus";
    private static final String STATUS_ALPHA = "status_alpha";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STAUS,super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA,mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STAUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
