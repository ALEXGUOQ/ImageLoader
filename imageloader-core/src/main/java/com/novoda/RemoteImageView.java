package com.novoda;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.novoda.imageloader.R;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.loader.Loader;
import com.novoda.imageloader.core.model.ImageTagFactory;

public class RemoteImageView extends FrameLayout {

    private static final int DEFAULT_TAG_DIMENSION = 1024;

    private static ImageManager imageLoader;

    private ImageView imageView;
    private ImageTagFactory tagFactory;
    private String url;
    private int cachedWidth = DEFAULT_TAG_DIMENSION;
    private int cachedHeight = DEFAULT_TAG_DIMENSION;

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRemoteView(context, attrs);
        initFromAttributes(context, attrs);
        load();
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initRemoteView(context, attrs);
        initFromAttributes(context, attrs);
        load();
    }

    public void setUrl(String url) {
        this.url = url;
        imageView.setTag(tagFactory.build(url, getContext()));
    }

    public void load() {
        if (TextUtils.isEmpty(url)) {
            Log.w(getClass().getSimpleName(), "No url has been set on this view; cannot load image.");
            return;
        }

        imageView.setTag(tagFactory.build(url, getContext()));
        getImageLoader().load(imageView);
    }

    private void initRemoteView(Context context, AttributeSet attrs) {
        this.imageView = new ImageView(context, attrs);
        this.tagFactory = ImageTagFactory.newInstance();
        tagFactory.setWidth(cachedWidth);
        tagFactory.setHeight(cachedHeight);

        LayoutParams layoutParams = new LayoutParams(context, attrs);
        setLayoutParams(layoutParams);

        setPadding(0, 0, 0, 0);
        layoutParams.setMargins(0, 0, 0, 0);

        addView(imageView, getLayoutParams());
    }



    private void initFromAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RemoteImageView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.RemoteImageView_url:
                    url = typedArray.getString(attr);
                    break;
                case R.styleable.RemoteImageView_cached_width:
                    cachedWidth = typedArray.getInt(attr, DEFAULT_TAG_DIMENSION);
                    break;
                case R.styleable.RemoteImageView_cached_height:
                    cachedHeight = typedArray.getInt(attr, DEFAULT_TAG_DIMENSION);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
    }

    private Loader getImageLoader() {
        if (imageLoader != null) {
            return imageLoader.getLoader();
        }

        LoaderSettings settings = new LoaderSettings.SettingsBuilder().build(getContext());
        imageLoader = new ImageManager(settings);
        return imageLoader.getLoader();
    }

}
