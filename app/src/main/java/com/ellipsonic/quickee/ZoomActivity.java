package com.ellipsonic.quickee;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;

import java.text.DecimalFormat;

public class ZoomActivity extends Activity {
    static final int NONE = 0;
   private TouchImageView image;
    private DecimalFormat df;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        String imgpath = activityThatCalled.getExtras().getString("ImgPath");
        df = new DecimalFormat("#.##");
        image = (TouchImageView) findViewById(R.id.img);
        image.setImageURI(Uri.parse(imgpath));
             image.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {

            @Override
            public void onMove() {
                PointF point = image.getScrollPosition();
                RectF rect = image.getZoomedRect();
                float currentZoom = image.getCurrentZoom();
                boolean isZoomed = image.isZoomed();

            }
        });

    }

}