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
import com.novoda.imageloader.core.model.ImageTagFactory;

public class RemoteImageView extends FrameLayout {

    private static final int DEFAULT_TAG_DIMENSION = 1024;

    private ImageView imageView;
    private String url;
    private int tagWidth = DEFAULT_TAG_DIMENSION;
    private int tagHeight = DEFAULT_TAG_DIMENSION;
    private ImageManager imageLoader;


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

    private void initRemoteView(Context context) {
        this.imageView = new ImageView(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(imageView, getLayoutParams());

        LoaderSettings settings = new LoaderSettings.SettingsBuilder().build(getContext());
        imageLoader = new ImageManager(settings);
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
                    tagWidth = typedArray.getInt(attr, DEFAULT_TAG_DIMENSION);
                    break;
                case R.styleable.RemoteImageView_cached_height:
                    tagHeight = typedArray.getInt(attr, DEFAULT_TAG_DIMENSION);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void load(){
        if (TextUtils.isEmpty(url)) {
            Log.w(getClass().getSimpleName(), "No url has been set on this view; cannot load image.");
            return;
        }

        ImageTagFactory tagFactory = ImageTagFactory.newInstance();
        tagFactory.setWidth(tagWidth);
        tagFactory.setHeight(tagHeight);
        imageView.setTag(tagFactory.build(url, getContext()));

        imageLoader.getLoader().load(imageView);
    }

}
