package edu.wm.opengltestapp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

public class Triangle {
	
	//Specifies where the vertex will be located.
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +

            "attribute vec4 vPosition;" +
            "void main() {" +
            // the matrix must be included as a modifier of gl_Position
            "  gl_Position = vPosition * uMVPMatrix;" +
            "}";
		//Determines what color each part of each triangle should be.
		private final String fragmentShaderCode =
		    "precision mediump float;" +
		    "uniform vec4 vColor;" +
		    "void main() {" +
		    "  gl_FragColor = vColor;" +
		    "}";
	private final int mProgram;
	private int mPositionHandle;
	private int mColorHandle;
	private int mMVPMatrixHandle;
		
	private final FloatBuffer vertexBuffer;
	static final int COORDS_PER_VERTEX = 3;
	static float triangleCoords[] = { // in counterclockwise order:
        0.0f,  0.622008459f, 0.0f,   // top
       -0.5f, -0.311004243f, 0.0f,   // bottom left
        0.5f, -0.311004243f, 0.0f    // bottom right
   };
	private float[] mRotationMatrix = new float[16];
	
	
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // bytes per vertex
	
	float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
	
	public Triangle() {
		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
		//use the device hardware's native byte order
		bb.order(ByteOrder.nativeOrder());
		
		//Create a floating point buffer from the ByteBuffer
		vertexBuffer = bb.asFloatBuffer();
		//Add the coordinates
		vertexBuffer.put(triangleCoords);
		//Set the buffer to read from the first coordiante
		vertexBuffer.position(0);
		
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		
		mProgram = GLES20.glCreateProgram(); //Create empty OpenGL ES Program
		GLES20.glAttachShader(mProgram, vertexShader); //Add the shaders
		GLES20.glAttachShader(mProgram, fragmentShader);
		GLES20.glLinkProgram(mProgram); //Creates OpenGL ES program executables
	}

	public static int loadShader(int type, String shaderCode) {
		//Creates a shader value depending on what type it is
		//(GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);
		
		//Add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		return shader;
	}
	
	public void draw(float[] mvpMatrix)
	{
		GLES20.glUseProgram(mProgram); //Add the program to the environment
		
		//Get handle to vertex shader's vPosition member - access the vPosition variable in the shader code
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
		//Enable the handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		// Prepare the triangle coordinate data - fill in all the information
	    GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
	                                 GLES20.GL_FLOAT, false,
	                                 vertexStride, vertexBuffer);

	    // get handle to fragment shader's vColor member
	    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

	    // Set color for drawing the triangle
	    GLES20.glUniform4fv(mColorHandle, 1, color, 0);
	    
	    mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

	    GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
	    
	    // Draw the triangle
	    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

	    // Disable vertex array
	    GLES20.glDisableVertexAttribArray(mPositionHandle);

		
	}
}
