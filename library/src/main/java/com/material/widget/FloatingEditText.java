package com.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by IntelliJ IDEA.
 * User: keith.
 * Date: 14-10-30.
 * Time: 15:57.
 */
public class FloatingEditText extends EditText {

    private static final long ANIMATION_DURATION = 120;
    private static final int StateHintNormal = 0;
    private static final int StateHintZoomIn = 1;
    private static final int StateHintZoomOut = 2;

    private int mState = StateHintNormal;
    private long mStartTime;
    private int mTextColor;
    private int mTextSize;
    private int mColor;
    private int mHighlightedColor;
    private int mUnderlineHeight;
    private int mUnderlineHighlightedHeight;
    private boolean mTextEmpty;
    private float mHintScale = 0.5f;
    private Rect lineRect = new Rect();

    private Paint mHintPaint;

    public FloatingEditText(Context context) {
        this(context, null);
    }

    public FloatingEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public FloatingEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, android.R.attr.editTextStyle);
        setBackgroundColor(Color.TRANSPARENT);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FloatingEditText);
        mColor = attributes.getColor(R.styleable.FloatingEditText_floating_edit_text_color,
                getResources().getColor(R.color.floating_edit_text_color));
        mHighlightedColor = attributes.getColor(R.styleable.FloatingEditText_floating_edit_text_highlighted_color,
                getResources().getColor(R.color.floating_edit_text_highlighted_color));
        mUnderlineHeight = attributes.getDimensionPixelSize(R.styleable.FloatingEditText_floating_edit_text_underline_height,
                getResources().getDimensionPixelSize(R.dimen.floating_edit_text_underline_height));
        mUnderlineHighlightedHeight = attributes.getDimensionPixelSize(R.styleable.FloatingEditText_floating_edit_text_underline_highlighted_height,
                getResources().getDimensionPixelSize(R.dimen.floating_edit_text_underline_highlighted_height));
        setHintTextColor(Color.TRANSPARENT);
        mTextEmpty = TextUtils.isEmpty(getText());
        mHintPaint = new Paint();
        mHintPaint.setAntiAlias(true);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        boolean isEmpty = TextUtils.isEmpty(getText());
        if (mTextEmpty != isEmpty) {
            mTextEmpty = isEmpty;
            if (isEmpty && isShown()) {
                mStartTime = System.currentTimeMillis();
                mState = StateHintZoomIn;
            } else {
                mStartTime = System.currentTimeMillis();
                mState = StateHintZoomOut;
            }
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(getHint())) {
            mHintPaint.set(getPaint());
            float maxTextSize = getTextSize();
            float minTextSize = getTextSize() * mHintScale;
            float maxHintY = getBaseline();
            float minHintY = getBaseline() + getPaint().getFontMetricsInt().top + getScrollY();
            float textSize;
            float hintY;
            float hintX = getCompoundPaddingLeft() + getScrollX();
            long elapsed = System.currentTimeMillis() - mStartTime;
            switch (mState) {
                case StateHintNormal: {
                    textSize = maxTextSize;
                    hintY = maxHintY;
                    mHintPaint.setColor(mColor);
                    mHintPaint.setTextSize(textSize);
                    canvas.drawText(getHint().toString(), hintX, hintY, mHintPaint);
                }
                break;
                case StateHintZoomIn: {
                    if (elapsed < ANIMATION_DURATION) {
                        textSize = ((maxTextSize - minTextSize) * elapsed) / ANIMATION_DURATION + minTextSize;
                        hintY = ((maxHintY - minHintY) * elapsed) / ANIMATION_DURATION + minHintY;
                        mHintPaint.setColor(mHighlightedColor);
                        mHintPaint.setTextSize(textSize);
                        canvas.drawText(getHint().toString(), hintX, hintY, mHintPaint);
                        postInvalidate();
                    } else {
                        textSize = maxTextSize;
                        hintY = maxHintY;
                        mHintPaint.setColor(mColor);
                        mHintPaint.setTextSize(textSize);
                        canvas.drawText(getHint().toString(), hintX, hintY, mHintPaint);
                    }
                }
                break;
                case StateHintZoomOut: {
                    if (elapsed < ANIMATION_DURATION) {
                        textSize = maxTextSize - ((maxTextSize - minTextSize) * elapsed) / ANIMATION_DURATION;
                        hintY = maxHintY - ((maxHintY - minHintY) * elapsed) / ANIMATION_DURATION;
                        mHintPaint.setColor(mHighlightedColor);
                        mHintPaint.setTextSize(textSize);
                        canvas.drawText(getHint().toString(), hintX, hintY, mHintPaint);
                        postInvalidate();
                    } else {
                        textSize = minTextSize;
                        hintY = minHintY;
                        mHintPaint.setColor(mHighlightedColor);
                        mHintPaint.setTextSize(textSize);
                        canvas.drawText(getHint().toString(), hintX, hintY, mHintPaint);
                    }
                }
                break;
            }
        }
        if (isFocused()) {
            lineRect.left = 0;
            lineRect.top = getHeight() - mUnderlineHighlightedHeight;
            lineRect.right = getWidth();
            lineRect.bottom = getHeight();
            mHintPaint.setColor(mHighlightedColor);
            canvas.drawRect(lineRect, mHintPaint);
        } else {
            lineRect.left = 0;
            lineRect.top = getHeight() - mUnderlineHeight;
            lineRect.right = getWidth();
            lineRect.bottom = getHeight();
            mHintPaint.setColor(mColor);
            canvas.drawRect(lineRect, mHintPaint);
        }
    }
}
