package com.crowdcraft.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by ericwood on 12/27/14.
 */
public class OverlayView extends View {
    public static final String TAG = OverlayView.class.getName();
    String accelData = "Accelerometer Data";
    String compassData = "Compass Data";
    String gyroData = "Gyro Data";

    public OverlayView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setTextAlign(Paint.Align.CENTER);
        contentPaint.setTextSize(20);
        contentPaint.setColor(Color.RED);
        canvas.drawText(accelData, canvas.getWidth()/2, canvas.getHeight()/4, contentPaint);
        canvas.drawText(compassData, canvas.getWidth()/2, canvas.getHeight()/2, contentPaint);
        canvas.drawText(gyroData, canvas.getWidth()/2, (canvas.getHeight()*3)/4, contentPaint);
    }

}
