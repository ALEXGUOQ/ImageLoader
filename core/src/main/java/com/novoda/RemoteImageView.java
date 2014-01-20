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

    private boolean adjustViewBounds;
    private int maxHeight = Integer.MAX_VALUE;
    private int maxWidth = Integer.MAX_VALUE;
    private int scaleType = ImageView.ScaleType.CENTER_INSIDE.ordinal();
    private int src;

    public RemoteImageView(Context context) {
        super(context);
        initRemoteView(context);
        load();
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRemoteView(context);
        initFromAttributes(context, attrs);
        load();
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initRemoteView(context);
        initFromAttributes(context, attrs);
        load();
    }

    public void setUrl(String url) {
        this.url = url;
        imageView.setTag(tagFactory.build(url, getContext()));
    }

    public void load(){
        if (TextUtils.isEmpty(url)) {
            Log.w(getClass().getSimpleName(), "No url has been set on this view; cannot load image.");
            return;
        }

        imageView.setTag(tagFactory.build(url, getContext()));
        getImageLoader().load(imageView);
    }

    private void initRemoteView(Context context) {
        this.tagFactory = ImageTagFactory.newInstance();
        tagFactory.setWidth(cachedWidth);
        tagFactory.setHeight(cachedHeight);

        this.imageView = new ImageView(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
                case R.styleable.RemoteImageView_adjustViewBounds:
                    adjustViewBounds = typedArray.getBoolean(attr, false);
                    break;
                case R.styleable.RemoteImageView_maxHeight:
                    maxHeight = typedArray.getInt(attr, Integer.MAX_VALUE);
                    break;
                case R.styleable.RemoteImageView_maxWidth:
                    maxWidth = typedArray.getInt(attr, Integer.MAX_VALUE);
                    break;
                case R.styleable.RemoteImageView_scaleType:
                    scaleType = typedArray.getInt(attr, ImageView.ScaleType.CENTER_INSIDE.ordinal());
                    break;
                case R.styleable.RemoteImageView_src:
                    src = typedArray.getResourceId(attr, 0);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
        updateImageViewWithAttributes();
    }

    private void updateImageViewWithAttributes() {
        imageView.setMaxWidth(maxWidth);
        imageView.setMaxHeight(maxHeight);
        imageView.setScaleType(ImageView.ScaleType.values()[scaleType]);
        imageView.setAdjustViewBounds(adjustViewBounds);
        imageView.setBackgroundResource(src);
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
