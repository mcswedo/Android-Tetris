package cs520.project.tetris;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Wrapper activity demonstrating the use of {@link GLSurfaceView}, a view
 * that uses OpenGL drawing into a dedicated surface.
 *
 * Shows:
 * + How to redraw in response to user input.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create our Preview view and set it as the content of our
        // Activity
        mGLSurfaceView = new TouchSurfaceView(this);
        setContentView(mGLSurfaceView);
        mGLSurfaceView.requestFocus();
        mGLSurfaceView.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private GLSurfaceView mGLSurfaceView;
}

/**
 * Implement a simple rotation control.
 *
 */
class TouchSurfaceView extends GLSurfaceView {

    public TouchSurfaceView(Context context) {
        super(context);
        mRenderer = new BlockRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override public boolean onTrackballEvent(MotionEvent e) {
        requestRender();
        return true;
    }

    @Override public boolean onTouchEvent(MotionEvent e){
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;
			mRenderer.mBlock.start_x += dx * TOUCH_SCALE_FACTOR;
			requestRender();
			//return true;
		}
		
		mPreviousX = x;
		return true;
	}

    /**
     * Render a cell.
     */
    private class BlockRenderer implements GLSurfaceView.Renderer {
        public BlockRenderer(){
        	mBlock = new Block();
        }
        
        public void onDrawFrame(GL10 gl) {

            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -3.0f);
       
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            
            // If the block is off the screen, spawn a new one!
            if(mBlock.start_y <= -2.2f)
            {
            	mBlock = new Block();
            	mBlock.start_y = 0.7f;
            }
            
            // Vertical movement
            mBlock.start_y -= MOVE_INCREMENT;
            gl.glTranslatef(0, mBlock.start_y, 0);
            
            // Horizontal movement
            gl.glTranslatef(mBlock.start_x, 0, 0);
            requestRender();

            mBlock.draw(gl);
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
             gl.glViewport(0, 0, width, height);

             /*
              * Set our projection matrix. This doesn't have to be done
              * each time we draw, but usually a new projection needs to
              * be set when the viewport is resized.
              */

             float ratio = (float) width / height;
             gl.glMatrixMode(GL10.GL_PROJECTION);
             gl.glLoadIdentity();
             gl.glOrthof(-ratio, ratio, -1, 1, 1, 10);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            /*
             * By default, OpenGL enables features that improve quality
             * but reduce performance. One might want to tweak that
             * especially on software renderer.
             */
            gl.glDisable(GL10.GL_DITHER);

            /*
             * Some one-time OpenGL initialization can be made here
             * probably based on features of this particular context
             */
             gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                     GL10.GL_FASTEST);

             gl.glClearColor(1,1,1,1);
             gl.glEnable(GL10.GL_CULL_FACE);
             gl.glShadeModel(GL10.GL_SMOOTH);
             gl.glEnable(GL10.GL_DEPTH_TEST);
        }
        
        private Block mBlock;
    }
    
    public final float MOVE_INCREMENT = .005f;

    private final float TOUCH_SCALE_FACTOR = .0025f;
    private final float TRACKBALL_SCALE_FACTOR = 36.0f;
    private BlockRenderer mRenderer;
    private float mPreviousX;
    private float mPreviousY;
}