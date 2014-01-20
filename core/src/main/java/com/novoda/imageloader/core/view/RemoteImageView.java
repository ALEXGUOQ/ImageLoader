package com.novoda.imageloader.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class RemoteImageView extends FrameLayout {

    private ImageView imageView;

    public RemoteImageView(Context context) {
        super(context);
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(context, attrs));
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutParams(new LayoutParams(context, attrs));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addImageView();
    }

    private void addImageView() {
        imageView = new ImageView(getContext());
        addView(imageView, getLayoutParams());
    }

}
