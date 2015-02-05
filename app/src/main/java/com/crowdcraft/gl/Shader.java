package com.crowdcraft.gl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;

/**
 * Created by ericwood on 1/25/15.
 */
public class Shader {

    static Shader create() {
        Shader shader = new Shader();
        return shader;
    }

    private final String vertexShaderCode =
            "uniform mat4 u_MVPMatrix;        \n"     // A constant representing the combined model/view/projection matrix.

                    + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
                    + "attribute vec4 a_Color;        \n"     // Per-vertex color information we will pass in.

                    + "varying vec4 v_Color;          \n"     // This will be passed into the fragment shader.

                    + "void main()                    \n"     // The entry point for our vertex shader.
                    + "{                              \n"
                    + "   v_Color = a_Color;          \n"     // Pass the color through to the fragment shader.
                    // It will be interpolated across the triangle.
                    + "   gl_Position = u_MVPMatrix   \n"     // gl_Position is a special variable used to store the final position.
                    + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
                    + "}                              \n";    // normalized screen coordinates.

    private final String fragmentShaderCode =
            "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
                    // precision in the fragment shader.
                    + "varying vec4 v_Color;          \n"     // This is the color from the vertex shader interpolated across the
                    // triangle per fragment.
                    + "void main()                    \n"     // The entry point for our fragment shader.
                    + "{                              \n"
                    + "   gl_FragColor = v_Color;     \n"     // Pass the color directly through the pipeline.
                    + "}";

    private int mProgramHandle = 0;

    /** This will be used to pass in the transformation matrix. */
    private int mMVPMatrixHandle;

    /** This will be used to pass in model position information. */
    private int mPositionHandle;

    /** This will be used to pass in model color information. */
    private int mColorHandle;

    private Shader() {
    }

    public void init() {
        int programHandle = getProgram();
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(programHandle);

        // Set program handles. These will later be used to pass in values to the program.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
    }

    public void draw(Renderable r, float[] modelMatrix, float[] viewMatrix, float[] projectionMatrix) {
        if (mProgramHandle == 0) {
            throw new IllegalStateException("Calling draw on uninitialized shader instance");
        }

        float[] mvpMatrix = new float[16];

        FloatBuffer vertices = r.getVertices();
        FloatBuffer colors = r.getColors();

        // Pass in the position information
        vertices.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, r.getPositionDataSize(), GLES20.GL_FLOAT, false,
                0, vertices);

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Pass in the color information
        colors.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, r.getColorSize(), GLES20.GL_FLOAT, false,
                0, colors);

        GLES20.glEnableVertexAttribArray(mColorHandle);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the cube
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);

    }

    private int getProgram() {
        if (mProgramHandle == 0) {
            mProgramHandle = createProgram();
        }
        return mProgramHandle;
    }

    private int createProgram() {
        int programHandle = GLES20.glCreateProgram();

        int vertexShaderHandle = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShaderHandle = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // Bind the vertex shader to the program.
        GLES20.glAttachShader(programHandle, vertexShaderHandle);

        // Bind the fragment shader to the program.
        GLES20.glAttachShader(programHandle, fragmentShaderHandle);

        // Bind attributes
        GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
        GLES20.glBindAttribLocation(programHandle, 1, "a_Color");

        // Link the two shaders together into a program.
        GLES20.glLinkProgram(programHandle);

        // Get the link status.
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

        // If the link failed, delete the program.
        if (linkStatus[0] == 0)
        {
            GLES20.glDeleteProgram(programHandle);
            programHandle = 0;
        }

        if (programHandle == 0)
        {
            throw new RuntimeException("Error creating program.");
        }
        return programHandle;
    }

    private int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shaderHandle = GLES20.glCreateShader(type);

        if (shaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderCode);

            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0)
        {
            throw new RuntimeException("Error creating vertex shader.");
        }
        return shaderHandle;
    }


}
