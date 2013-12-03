package edu.wm.opengltestapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.ETC1Util;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

public class MainActivity extends Activity {

	
	class MyGLSurfaceView extends GLSurfaceView {
		
		private final MyRenderer mRenderer;
		
		public MyGLSurfaceView(Context context, ArrayList<Float> coords, ArrayList<Short> order, ArrayList<Float> normals) {
			super(context);
			setEGLContextClientVersion(2); //Must go above everything else
			mRenderer = new MyRenderer(coords, order, normals);
			setRenderer(mRenderer);
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}
		
		private final float TOUCH_SCALE_FACTOR = 180.0f / 420; //320
		private float mPreviousX;
		private float mPreviousY;
		
		@Override
		public boolean onTouchEvent(MotionEvent e) {
		    // MotionEvent reports input details from the touch screen
		    // and other input controls. In this case, you are only
		    // interested in events where the touch position changed.

		    float x = e.getX();
		    float y = e.getY();

		    switch (e.getAction()) {
		        case MotionEvent.ACTION_MOVE:

		            float dx = x - mPreviousX;
		            float dy = y - mPreviousY;

		            // reverse direction of rotation above the mid-line
		            if (y > getHeight() / 2) {
		              dx = dx * -1 ;
		            }

		            // reverse direction of rotation to left of the mid-line
		            if (x < getWidth() / 2) {
		              dy = dy * -1 ;
		            }

		            //mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR;
		            mRenderer.mAngle += (dx * TOUCH_SCALE_FACTOR);  // = 180.0f / 320
		            mRenderer.mAngle2 += (dy) * TOUCH_SCALE_FACTOR;
		            requestRender();
		    }

		    mPreviousX = x;
		    mPreviousY = y;
		    return true;
		}
	}
	
	private GLSurfaceView mGLView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<Float> coords = new ArrayList<Float>();
		ArrayList<Short> order = new ArrayList<Short>();
		ArrayList<Float> normals = new ArrayList<Float>();
		try {
			LoadObject(coords, order, normals);
		} catch (IOException e) {
			Log.v("a", "IOException");
			e.printStackTrace();
		}
		//Log.v("a", coords.get(0).toString());
		mGLView = new MyGLSurfaceView(this, coords, order, normals);
		setContentView(mGLView);
	}

	private void LoadObject(ArrayList<Float> coords, ArrayList<Short> order, ArrayList<Float> normals) throws IOException {
		BufferedReader x = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.alduin)));
		String line;
		while((line = x.readLine()) != null)
		{
			String[] split = line.split(" ");
			if(split[0].equals("v"))
			{
				coords.add(Float.valueOf(split[1]));
				coords.add(Float.valueOf(split[2]));
				coords.add(Float.valueOf(split[3]));
			}
			else if(split[0].equals("f"))
			{
				String[] temp = split[1].split("/");
				order.add((short)(Short.valueOf(temp[0])-1));
				//normals.add((short)(Short.valueOf(temp[0])-1));
				temp = split[2].split("/");
				order.add((short)(Short.valueOf(temp[0])-1));
				//normals.add((short)(Short.valueOf(temp[0])-1));
				temp = split[3].split("/");
				order.add((short)(Short.valueOf(temp[0])-1));
				//normals.add((short)(Short.valueOf(temp[0])-1));
			}	
			else if(split[0].equals("vn"))
			{
				normals.add(Float.valueOf(split[1]));
				normals.add(Float.valueOf(split[2]));
				normals.add(Float.valueOf(split[3]));
			}
		}
	}
	
	public int loadTexture(String filename) {
		final int[] textureHandle = new int[1];
		
		GLES20.glGenTextures(1,  textureHandle, 0);
		
		AssetManager assetMgr = this.getAssets();
		InputStream is = null;
		
		try {
			is = assetMgr.open(filename, AssetManager.ACCESS_STREAMING);
			
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
			
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
			
			ETC1Util.loadTexture(GLES10.GL_TEXTURE_2D, 0, 0, GLES10.GL_RGB, GLES10.GL_UNSIGNED_SHORT_5_6_5, is);

		}
		catch (IOException ex)
		{}
		finally {
			try{
				if(is != null) {
					is.close();
				}
			}
			catch (IOException e){
			}
		}
		if(textureHandle[0] == 0)
		{
			throw new RuntimeException("Error loading texture");
		}
		return 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
