package com.crowdcraft.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

import com.crowdcraft.gl.Demo2Renderer;
import com.crowdcraft.gl.DemoRenderer;
import com.crowdcraft.gl.Geometry3DRenderer;

/**
 * TODO: document your custom view class.
 */
public class Geomtery3DView extends GLSurfaceView {

    public Geomtery3DView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        // Transparent background for view
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new Geometry3DRenderer());
//        setRenderer(new DemoRenderer());
//        setRenderer(new Demo2Renderer());
    }
}
