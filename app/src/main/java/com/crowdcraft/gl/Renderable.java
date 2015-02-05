package com.crowdcraft.gl;

import java.nio.FloatBuffer;

/**
 * Created by ericwood on 1/25/15.
 */
public interface Renderable {
    FloatBuffer getVertices();
    FloatBuffer getColors();
    int getPositionOffset();
    int getPositionDataSize();
    int getColorOffset();
    int getColorSize();
}
