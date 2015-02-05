package com.crowdcraft.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

class Cube implements Renderable {

    private static final int COORDS_PER_VERTEX = 3;

    /** How many bytes between vertices */
    private static final int STRIDE = COORDS_PER_VERTEX * 4;
    
    private FloatBuffer mVertexBuffer = null;
    private FloatBuffer mColorBuffer = null;
    private ByteBuffer  mIndexBuffer;
        
//    private float vertices[] = {    // X, Y, Z,
//                                    // R, G, B, A
//                                    -1.0f, -1.0f, -1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f,
//                                    1.0f, -1.0f, -1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f,
//                                    1.0f,  1.0f, -1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f,
//                                    -1.0f, 1.0f, -1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f,
//                                    -1.0f, -1.0f,  1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f,
//                                    1.0f, -1.0f,  1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f,
//                                    1.0f,  1.0f,  1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f,
//                                    -1.0f,  1.0f,  1.0f,
//                                    1.0f, 1.0f, 1.0f, 1.0f
//                                };

    // In OpenGL counter-clockwise winding is default. This means that when we look at a triangle,
    // if the points are counter-clockwise we are looking at the "front". If not we are looking at
    // the back. OpenGL has an optimization where all back-facing triangles are culled, since they
    // usually represent the backside of an object and aren't visible anyways.


//    private final float vertices[] = { // X, Y, Z
//            // Front face
//            -1.0f, 1.0f, 1.0f,
//            -1.0f, -1.0f, 1.0f,
//            1.0f, 1.0f, 1.0f,
//            -1.0f, -1.0f, 1.0f,
//            1.0f, -1.0f, 1.0f,
//            1.0f, 1.0f, 1.0f,
//
//            // Right face
//            1.0f, 1.0f, 1.0f,
//            1.0f, -1.0f, 1.0f,
//            1.0f, 1.0f, -1.0f,
//            1.0f, -1.0f, 1.0f,
//            1.0f, -1.0f, -1.0f,
//            1.0f, 1.0f, -1.0f,
//
//            // Back face
//            1.0f, 1.0f, -1.0f,
//            1.0f, -1.0f, -1.0f,
//            -1.0f, 1.0f, -1.0f,
//            1.0f, -1.0f, -1.0f,
//            -1.0f, -1.0f, -1.0f,
//            -1.0f, 1.0f, -1.0f,
//
//            // Left face
//            -1.0f, 1.0f, -1.0f,
//            -1.0f, -1.0f, -1.0f,
//            -1.0f, 1.0f, 1.0f,
//            -1.0f, -1.0f, -1.0f,
//            -1.0f, -1.0f, 1.0f,
//            -1.0f, 1.0f, 1.0f,
//
//            // Top face
//            -1.0f, 1.0f, -1.0f,
//            -1.0f, 1.0f, 1.0f,
//            1.0f, 1.0f, -1.0f,
//            -1.0f, 1.0f, 1.0f,
//            1.0f, 1.0f, 1.0f,
//            1.0f, 1.0f, -1.0f,
//
//            // Bottom face
//            1.0f, -1.0f, -1.0f,
//            1.0f, -1.0f, 1.0f,
//            -1.0f, -1.0f, -1.0f,
//            1.0f, -1.0f, 1.0f,
//            -1.0f, -1.0f, 1.0f,
//            -1.0f, -1.0f, -1.0f,
//    };

    private final float[] vertices = {
            //1. front-triangles
            //f1,f2,f3
            1,1,0,        -1,1,0,          -1,-1,0,

            //f3,f4,f1
            -1,-1,0,       1,-1,0,          1,1,0,

            //2. back-triangles
            //b1,b2,b3
            1,-1,1,          1,1,1,          -1,1,1,
            //b3,b4,b1
            -1,1,1,          -1,-1,1,       1,-1,1,

            //3. right-triangles
            //b2,f1,f4
            1,1,1,          1,1,0,          1,-1,0,
            //b2,f4,b1
            1,1,1,         1,-1,0,         1,-1,1,

            //4. left-triangles
            //b3, f2, b4
            -1,1,1,          -1,1,0,       -1,-1,1,
            //b4 f2 f3
            -1,-1,1,       -1,1,0,       -1,-1,0,

            //5. top-triangles
            //b2, b3, f2
            1,1,1,         -1,1,1,      -1,1,0,
            //b2, f2, f1
            1,1,1,         -1,1,0,      1,1,0,

            //6. bottom-triangles
            //b1, b4, f3
            1,-1,1,      -1,-1,1,   -1,-1,0,
            //b1, f3, f4
            1,-1,1,      -1,-1,0,   1,-1,0
/*
 */
    };

    final float[] colors = { // R, G, B, A
            // Front face (red)
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            // Right face (green)
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            // Back face (blue)
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            // Left face (yellow)
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            // Top face (cyan)
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,

            // Bottom face (magenta)
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f
     };

    private byte indices[] = {
                              0, 4, 5, 0, 5, 1,
                              1, 5, 6, 1, 6, 2,
                              2, 6, 7, 2, 7, 3,
                              3, 7, 4, 3, 4, 0,
                              4, 7, 6, 4, 6, 5,
                              3, 0, 1, 3, 1, 2
                              };

    public Cube() {
    }

    @Override
    public int getPositionOffset() {
        return 0;
    }

    @Override
    public int getPositionDataSize() {
        return 3;
    }

    @Override
    public int getColorOffset() {
        return 0;
    }

    @Override
    public int getColorSize() {
        return 4;
    }

    @Override
    public FloatBuffer getVertices() {
        if (mVertexBuffer == null) {
            // Initialize the buffers.
            mVertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            mVertexBuffer.put(vertices).position(0);
        }
        return mVertexBuffer;
    }

    @Override
    public FloatBuffer getColors() {
        if (mColorBuffer == null) {
            mColorBuffer = ByteBuffer.allocateDirect(colors.length * 4)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            mColorBuffer.put(colors).position(0);
        }
        return mColorBuffer;
    }

    public void setUniformColor(float r, float g, float b, float alpha) {
        mColorBuffer = null;
        for (int i = 0; i < colors.length - 4; i += 4) {
            colors[i] = r;
            colors[i+1] = g;
            colors[i+2] = b;
            colors[i+3] = alpha;
        }
    }
}
