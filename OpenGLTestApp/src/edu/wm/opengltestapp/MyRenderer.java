package edu.wm.opengltestapp;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

public class MyRenderer implements Renderer {

	private Triangle mTriangle;
	private Square mSquare;
	private MyObj myObj;
	ArrayList<Float> coords;
	ArrayList<Short> order;
	ArrayList<Float> normals;
	
	
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private final float[] xRotationMatrix = new float[16];
    private final float[] yRotationMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    
    // Declare as volatile because we are updating it from another thread
    public volatile float mAngle;
    public volatile float mAngle2;
	
    public MyRenderer(ArrayList<Float> coords, ArrayList<Short> order, ArrayList<Float> normals2)
    {
    	super();
    	this.coords = coords;
    	this.order = order;
    	this.normals = normals2;
    }
    
	@Override
	public void onDrawFrame(GL10 unused) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, 6f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);

        // Draw square
        //mSquare.draw(mMVPMatrix);

        // Create a rotation for the triangle
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(xRotationMatrix,  0,  -mAngle2,  1,  0,  0f);
        Matrix.setRotateM(yRotationMatrix,  0,  -mAngle,  0,  1,  0f);
        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);
        Matrix.multiplyMM(mRotationMatrix, 0, xRotationMatrix, 0, yRotationMatrix, 0);

        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);

        // Draw triangle
     //   mTriangle.draw(mMVPMatrix);
        myObj.draw(mMVPMatrix);
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		// TODO Auto-generated method stub
		GLES20.glViewport(0, 0, width, height);
		
		float ratio = (float) width/height;
		
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 15);
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		mTriangle = new Triangle();
		mSquare = new Square();
		myObj = new MyObj(coords, order);
		
		GLES20.glDepthFunc(GL10.GL_LEQUAL);
		GLES20.glEnable(GL10.GL_CULL_FACE);
		GLES20.glEnable(GL10.GL_DEPTH_TEST);
		GLES20.glCullFace(GL10.GL_BACK);
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
}

/*
 * 		objCoords = new float[coords.size()];
		for(int x = 0; x<coords.size(); x++) 
			objCoords[x] = coords.get(x);
		
		drawOrder = new short[order.size()];
		for(int x = 0; x<order.size(); x++)
			drawOrder[x] = order.get(x); */
