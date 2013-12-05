package edu.wm.opengltestapp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;
import android.util.Log;

public class MyObj {
	
	public static final String TAG = "MY_OBJECT";

	/*
	final String vertexShaderCode =
		    "uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
		  + "uniform mat4 u_MVMatrix;       \n"     // A constant representing the combined model/view matrix.
		  + "uniform vec3 u_LightPos;       \n"     // The position of the light in eye space.
		 
		  + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
		  + "attribute vec4 a_Color;        \n"     // Per-vertex color information we will pass in.
		  + "attribute vec3 a_Normal;       \n"     // Per-vertex normal information we will pass in.
		 
		  + "varying vec4 v_Color;          \n"     // This will be passed into the fragment shader.
		 
		  + "void main()                    \n"     // The entry point for our vertex shader.
		  + "{                              \n"
		// Transform the vertex into eye space.
		  + "   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);              \n"
		// Transform the normal's orientation into eye space.
		  + "   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));     \n"
		// Will be used for attenuation.
		  + "   float distance = length(u_LightPos - modelViewVertex);             \n"
		// Get a lighting direction vector from the light to the vertex.
		  + "   vec3 lightVector = normalize(u_LightPos - modelViewVertex);        \n"
		// Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
		// pointing in the same direction then it will get max illumination.
		  + "   float diffuse = max(dot2(modelViewNormal, lightVector), 0.1);       \n"
		// Attenuate the light based on distance.
		  + "   diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));  \n"
		// Multiply the color by the illumination level. It will be interpolated across the triangle.
		  + "   v_Color = a_Color * diffuse;                                       \n"
		// gl_Position is a special variable used to store the final position.
		// Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
		  + "   gl_Position = u_MVPMatrix * a_Position;                            \n"
		  + "}                                                                     \n";        
	*/
	/*
    private final String vertexShaderCode =
        // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
        "uniform mat4 u_MVMatrix; " +
       // "uniform mat4 u_MVMatrix;" + 
       // "uniform vec3 u_lightPos;" + 
        "uniform vec4 u_Color; " + 
        
        "attribute vec4 a_Position;" +
        "attribute vec3 a_Normal;" + 
        "attribute vec2 a_TexCoordinate;" +
        //"attribute vec3 a_Normal; " + 
        "varying vec3 v_Position;" +
        "varying vec3 v_Normal;" + 
        "varying vec2 v_TexCoordinate;" +
        "varying vec4 v_Color;" + 
        
        "void main() {" +
       // "vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);" + 
        // the matrix must be included as a modifier of gl_Position
       " v_Position = vec3(u_MVMatrix * a_Position);" + 
        "v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));" + 
		"v_Color = u_Color;" +
        "  gl_Position = vPosition * uMVPMatrix;" +
		"v_TexCoordinate = a_TexCoordinate;" +
        "}";
	*/
    private final String vertexShaderCode =
            "uniform mat4 u_MVPMatrix;" +
    
            "varying vec4 v_Color;" + 
            "varying vec2 v_TexCoordinate;" +
            
            "attribute vec4 a_Position;" +
            //"attribute vec2 a_TexCoordinate;" +
            
            "void main() {" +
            "v_Color = a_Position;" + 
            //"v_TexCoordinate = a_TexCoordinate;" + 
            //Multiply each vertex by the model/view/projection matrix
            "  gl_Position = a_Position * u_MVPMatrix;" + 
            "}";
    /*
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "uniform mat4 u_MVMatrix; " + 
            "uniform vec4 u_Color; " + 
            
            "attribute vec4 a_Position;" +
            "attribute vec3 a_Normal;" + 
            "attribute vec2 a_TexCoordinate;" +
            "attribute vec3 a_Normal; " + 
            "varying vec3 v_Position;" +
            "varying vec3 v_Normal;" + 
            "varying vec2 v_TexCoordinate;" +
            "varying vec4 v_Color;" + 
            
            "void main() {" +
           // "vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);" + 
            // the matrix must be included as a modifier of gl_Position
    		"v_Color = u_Color;" +
            "gl_Position = vPosition * uMVPMatrix;" +
            "}";
	*/
    /*
    private final String fragmentShaderCode =
        "precision mediump float;" +
        "varying vec4 v_Color;" +
        "void main() {" +
        "gl_FragColor = v_Color;" + 
        "}";
    */
    private final String fragmentShaderCode =
        "precision mediump float;" +
        "varying vec4 v_Color;" +
        "void main() {" +
        "  gl_FragColor = v_Color;" +
        "}"; 
    
	/*
    private final String fragmentShaderCode = 
    		"precision mediump float;" +

    		"uniform vec3 u_lightPos;" +
    		"uniform sampler2D u_Texture;" +

    		"varying vec3 v_Position;" +
    		"varying vec4 v_Color;" +
    		"varying vec3 v_Normal;" +
    		"varying vec2 v_TexCoordinate;" +

		    "void main() {" +
		    	"float distance = length(u_lightPos - v_Position);" +
		    	"vec3 lightVector = normalize(u_lightPos - v_Position);" +
		    	"float diffuse = max(dot(v_Normal, lightVector), 0.1);" +
		    	"diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));" +
		    	"diffuse = diffuse + 0.3;" +
		    	
		    	"gl_FragColor = (v_Color * diffuse * texture2D(u_Texture, v_TexCoordinate));" +
		    "}";
    */
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawOrderBuffer;
    private FloatBuffer normalsBuffer;
    private FloatBuffer texCoordsBuffer;
    
    private final int mProgram;
    private int mPositionHandle;
    private int mNormalsHandle;
    
    private int mColorHandle;
    private int mMVPMatrixHandle;
    
    private int mTextureUniformHandle;
    private int mTextureCoordinateHandle;
    private final int mTextureCoordinateDataSize = 2;
    private int mTextureDataHandle;
    
    private float[] lightPos = {1, 2, 2};

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float objCoords[] = { -0.520609f,  2.72283f, -1.519276f,   // top left
                                    -0.490855f, 2.698357f, -1.519276f,   // bottom left
                                     -.490901f, 2.722791f, -1.482732f,  // bottom right
                                     -0.490852f,  2.747221f,  -1.519276f}; // top right

    private short drawOrder[] = { 1, 2, 3 }; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    /*
	public final List<Short> indices;
	public final List<Vertex> vertices;
    */
    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };
	
	public MyObj(ArrayList<Float> coords, ArrayList<Short> order,
			ArrayList<Float> textCoords, ArrayList<Short> textOrder) {
        // initialize vertex byte buffer for shape coordinates
		
		objCoords = new float[coords.size()];
		for(int x = 0; x<coords.size(); x++) 
		{
			objCoords[x] = coords.get(x);
			Log.v("a", "" + objCoords[x]);
		}
		
		drawOrder = new short[order.size()];
		for(int x = 0; x<order.size(); x++)
		{
			drawOrder[x] = order.get(x); 
			Log.v("a", "" + drawOrder[x]);
		}
		
        ByteBuffer bb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
                objCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(objCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawOrderBuffer = dlb.asShortBuffer();
        drawOrderBuffer.put(drawOrder);
        drawOrderBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                                                   vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                                                     fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        checkGlError("attaching vertex shader");
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        checkGlError("attaching fragment shader");
        GLES20.glBindAttribLocation(mProgram, 0, "a_Position");
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
        checkGlError("linking program");
        GLES20.glBindAttribLocation(mProgram, 0, "a_Position");
	}


	public static int loadShader(int type, String shaderCode) {
		//Creates a shader value depending on what type it is
		//(GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);
		
		//Add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		final int[] compileStatus = new int[1];
		GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		if(compileStatus[0]==0)
		{
			GLES20.glDeleteShader(shader);
			Log.v(TAG, GLES20.glGetShaderInfoLog(shader));
			throw new RuntimeException("Error creating " + (type==GLES20.GL_VERTEX_SHADER? "vertex shader" : "fragment shader"));
		}
		checkGlError("loading shader: ");
		return shader;
	}
	
	public void draw(float[] mvpMatrix)
	{
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        checkGlError("getting vPosition ");
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        checkGlError("enabling vertex attribute");
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);
        checkGlError("getting vertex attribute");
        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "v_Color");
        checkGlError("getting vertex attribute");
        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        checkGlError("glGetUniformLocation");
        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        checkGlError("glUniformMatrix4fv");
        // Draw the square
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
                              GLES20.GL_UNSIGNED_SHORT, drawOrderBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("MyObj", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
	
}
