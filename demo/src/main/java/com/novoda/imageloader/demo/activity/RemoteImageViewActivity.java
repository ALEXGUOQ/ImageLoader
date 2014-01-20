package com.novoda.imageloader.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.novoda.RemoteImageView;
import com.novoda.imageloader.demo.R;

public class RemoteImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_image_view);

        RemoteImageView view = (RemoteImageView) findViewById(R.id.remote_image_view);
    }

}
