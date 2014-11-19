package com.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by IntelliJ IDEA.
 * User: keith.
 * Date: 14-10-30.
 * Time: 15:57.
 */
public class FloatingEditText extends EditText {

    private static final int ANIMATION_DURATION = 150;

    private int mTextColor;
    private int mTextSize;
    private int mNormalColor;
    private int mHighlightedColor;
    private int mUnderlineHeight;
    private int mUnderlineHighlightedHeight;

    public FloatingEditText(Context context) {
        this(context, null);
    }

    public FloatingEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FloatingEditText);
        mHighlightedColor = attributes.getColor(R.styleable.FloatingEditText_floating_edit_text_highlighted_color,
                getResources().getColor(R.color.floating_edit_text_highlighted_color));
        mUnderlineHeight = attributes.getDimensionPixelSize(R.styleable.FloatingEditText_floating_edit_text_underline_height,
                getResources().getDimensionPixelSize(R.dimen.floating_edit_text_underline_height));
        mUnderlineHighlightedHeight = attributes.getDimensionPixelSize(R.styleable.FloatingEditText_floating_edit_text_underline_highlighted_height,
                getResources().getDimensionPixelSize(R.dimen.floating_edit_text_underline_highlighted_height));

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused)
        {

        }
        else {

        }
    }
}
