package com.novoda;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.novoda.imageloader.R;

public class RemoteImageView extends FrameLayout {

    private ImageView imageView;
    private String url;

    public RemoteImageView(Context context) {
        super(context);
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setLayoutParams(new LayoutParams(context, attrs));
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutParams(new LayoutParams(context, attrs));
        obtainStyledAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addImageView();

        Toast.makeText(getContext(), "url: " + url, Toast.LENGTH_SHORT).show();
    }

    private void addImageView() {
        imageView = new ImageView(getContext());
        addView(imageView, getLayoutParams());
        imageView.setBackgroundColor(getResources().getColor(android.R.color.black));
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RemoteImageView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.RemoteImageView_url:
                    url = typedArray.getString(attr);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
    }

}
