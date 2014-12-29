package com.crowdcraft.activity;

import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.crowdcraft.GLClearRenderer;
import com.crowdcraft.R;
import com.crowdcraft.view.CameraView;


public class ViewerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        FrameLayout viewerPane = (FrameLayout) findViewById(R.id.viewerPane);

        // Now let's create an OpenGL surface.
        GLSurfaceView glView = new GLSurfaceView( this );
        // To see the camera preview, the OpenGL surface has to be created translucently.
        // See link above.
        glView.setEGLConfigChooser( 8, 8, 8, 8, 16, 0 );
        glView.getHolder().setFormat( PixelFormat.TRANSLUCENT );
        // The renderer will be implemented in a separate class
        glView.setRenderer( new GLClearRenderer() );
        // Now set this as the main view.
        viewerPane.addView(glView);

        CameraView cameraView = new CameraView(getApplicationContext(),this);
        viewerPane.addView(cameraView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
