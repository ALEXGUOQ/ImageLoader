package com.novoda;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.novoda.imageloader.R;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.model.ImageTagFactory;

public class RemoteImageView extends FrameLayout {

    private final ImageView imageView;

    private String url;

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(context, attrs));
        obtainStyledAttributes(attrs);

        this.imageView = new ImageView(context);
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutParams(new LayoutParams(context, attrs));
        obtainStyledAttributes(attrs);

        this.imageView = new ImageView(context);
    }

    @Override
    protected void onFinishInflate() {
        addView(imageView, getLayoutParams());
        imageView.setBackgroundColor(getResources().getColor(android.R.color.black));
        load();
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

    private void load(){
        if (TextUtils.isEmpty(url)) {
            return;
        }

        LoaderSettings settings = new LoaderSettings.SettingsBuilder().build(getContext());
        ImageManager imageLoader = new ImageManager(settings);
        ImageTagFactory tagFactory = ImageTagFactory.newInstance();
        tagFactory.setWidth(100);
        tagFactory.setHeight(100);
        imageView.setTag(tagFactory.build(url, getContext()));

        imageLoader.getLoader().load(imageView);
    }

}
