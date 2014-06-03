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

    @Override public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dx = x - mPreviousX;
            float dy = y - mPreviousY;
            requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    /**
     * Render a cell.
     */
    private class BlockRenderer implements GLSurfaceView.Renderer {
        public BlockRenderer() {
        	int type = (int)(Math.random() * 7);
        	System.out.println(type);
        	float start_x = 0;
        	float start_y = .7f;
        	switch(type) {
        		case 0: // I block
        			mCell = new Cell(start_x, start_y); // Second to bottom
        			mCell2 = new Cell(start_x, start_y + SIZE * 2);
        			mCell3 = new Cell(start_x, start_y + SIZE * 4);
        			mCell4 = new Cell(start_x, start_y - SIZE * 2); 
        			break;
        		case 1: // O block
        			mCell = new Cell(start_x, start_y); // Lower Left Corner
        			mCell2 = new Cell(start_x, start_y + SIZE * 2);
        			mCell3 = new Cell(start_x + SIZE * 2, start_y + SIZE * 2);
        			mCell4 = new Cell(start_x + SIZE * 2, start_y);
        			break;
        		case 2: // J Block
        			mCell = new Cell(start_x, start_y); // Middle of 3-line
        			mCell2 = new Cell(start_x, start_y - SIZE * 2);
        			mCell3 = new Cell(start_x - SIZE * 2, start_y - SIZE * 2);
        			mCell4 = new Cell(start_x, start_y + SIZE * 2);
        			break;
        		case 3: // L Block
        			mCell = new Cell(start_x, start_y); // Middle of 3-line
        			mCell2 = new Cell(start_x, start_y - SIZE * 2);
        			mCell3 = new Cell(start_x + SIZE * 2, start_y - SIZE * 2);
        			mCell4 = new Cell(start_x, start_y + SIZE * 2);
        			break;
        		case 4: // Z Block
        			mCell = new Cell(start_x, start_y); // Middle top
        			mCell2 = new Cell(start_x, start_y - SIZE * 2);
        			mCell3 = new Cell(start_x + SIZE * 2, start_y - SIZE * 2);
        			mCell4 = new Cell(start_x - SIZE * 2, start_y);
        			break;
        		case 5: // S Block
        			mCell = new Cell(start_x, start_y); // Middle top
        			mCell2 = new Cell(start_x, start_y - SIZE * 2);
        			mCell3 = new Cell(start_x - SIZE * 2, start_y - SIZE * 2);
        			mCell4 = new Cell(start_x + SIZE * 2, start_y);
        			break;
        		case 6: // T Block
        			mCell = new Cell(start_x, start_y); // Middle of 3-line
        			mCell2 = new Cell(start_x, start_y + SIZE * 2);
        			mCell3 = new Cell(start_x - SIZE * 2, start_y);
        			mCell4 = new Cell(start_x + SIZE * 2, start_y);
        			break;		
        	}
        }

        public void onDrawFrame(GL10 gl) {
            /*
             * Usually, the first thing one might want to do is to clear
             * the screen. The most efficient way of doing this is to use
             * glClear().
             */

            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            /*
             * Now we're ready to draw some 3D objects
             */
            
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -3.0f);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            mCell.draw(gl);
            mCell2.draw(gl);
            mCell3.draw(gl);
            mCell4.draw(gl);
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
        private Cell mCell;
        private Cell mCell2;
        private Cell mCell3;
        private Cell mCell4;
    }
    
    public final float SIZE = .075f;
    
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float TRACKBALL_SCALE_FACTOR = 36.0f;
    private BlockRenderer mRenderer;
    private float mPreviousX;
    private float mPreviousY;
}