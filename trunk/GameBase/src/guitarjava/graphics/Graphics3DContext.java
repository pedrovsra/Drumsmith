package guitarjava.graphics;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Implements the GraphicsInterface. This is a 3D implementation.
 * @author brunojadami
 */
public class Graphics3DContext implements GraphicsInterface, GLEventListener
{

    private static final int MAX_CACHE = 1024; // Max cache DrawDatas
    private List listeners; // Listeners for the graphics update
    private Animator animator; // Animator for OpenGL canvas
    private GL gl; // To use on draw operations
    private GLCanvas canvas; // OpenGL Canvas
    private int mulY = -1; // To invert y axis
    private int zFar = 2000; // zFar
    private GLCapabilities caps; // GL capabilities
    private int cachedDatas[]; // Cache for DrawDatas, display lists id
    // Camera variables
    private float cameraEyeX = 0;
    private float cameraEyeY = 0;
    private float cameraEyeZ = 0;
    private float cameraToX = 0;
    private float cameraToY = 0;
    private float cameraToZ = 0;
    // Light variables
    private float lightX = 0;
    private float lightY = 0;
    private float lightZ = 0;

    /**
     * Constructor.
     */
    public Graphics3DContext()
    {
        listeners = new ArrayList();
        cachedDatas = new int[MAX_CACHE];
    }

    /**
     * Draws the DrawData onto the screen.
     * @param data the data to be draw
     */
    @Override
    public void draw(DrawData data)
    {
        if (data != null)
        {
            int list = 0;
            // Color and translation
            gl.glLoadIdentity();
            gl.glColor3f(data.color.getRed() / 256f, data.color.getGreen()
                    / 256f, data.color.getBlue() / 256f);
            gl.glTranslatef(data.getX(), mulY * data.getY(), data.getZ());
            // Check cache and generate
            if (data.cacheId > 0 && data.cacheId < MAX_CACHE)
            {
                if (cachedDatas[data.cacheId] != 0)
                {
                    gl.glCallList(cachedDatas[data.cacheId]);
                    return;
                }
                else
                {
                    list = gl.glGenLists(1);
                    gl.glNewList(list, GL.GL_COMPILE_AND_EXECUTE);
                }
            }
            // Draw operations
            if (data.type == DrawData.DRAW_2D_RECT)
            {
                draw2DRect(data);
            }
            else if (data.type == DrawData.DRAW_2D_FILLED_RECT)
            {
                draw2DFilledRect(data);
            }
            else if (data.type == DrawData.DRAW_3D_SPHERE)
            {
                draw3DSphere(data);
            }
            else if (data.type == DrawData.DRAW_3D_CLIPPED_SPHERE)
            {
                draw3DClippedSphere(data);
            }
            // End cache and save
            if (list != 0)
            {
                gl.glEndList();
                cachedDatas[data.cacheId] = list;
            }
        }
    }

    /**
     * Initializing the context.
     */
    @Override
    public void init(final Window component)
    {
        // Setting up opengl
        caps = new GLCapabilities();
        caps.setSampleBuffers(true); // Enable multisampling
        caps.setNumSamples(2);
        caps.setDoubleBuffered(true);
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);
        canvas.setAutoSwapBufferMode(false);
        component.add(canvas);
        // Adding close operation
        component.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                stop();
            }
        });
        canvas.setFocusable(false);
        // Others
        animator = new Animator(canvas);
        animator.start();
    }

    /**
     * OpenGL init method.
     */
    @Override
    public void init(GLAutoDrawable drawable)
    {
        gl = drawable.getGL();
        // Enable VSync
        gl.setSwapInterval(1);
        // Setup other stuff
        gl.glDisable(GL.GL_BLEND);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glEnable(GL.GL_NORMALIZE);
        //gl.glEnable(GL.GL_LINE_SMOOTH);
        //gl.glEnable(GL.GL_POLYGON_SMOOTH);
        //gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_LIGHTING);
        // Enable multisampling (anti-aliasing)
        gl.glEnable(GL.GL_MULTISAMPLE);
    }

    /**
     * Setting mulY, to invert y axis.
     */
    public void setMulY(int mulY)
    {
        this.mulY = mulY;
    }

    /**
     * Adds an event lister to the GraphicsUpdate event.
     * @param listener the listener
     */
    @Override
    public void addGraphicsUpdateEventListener(GraphicsUpdateListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes an event lister from the GraphicsUpdate event.
     * @param listener the listener
     */
    @Override
    public void removeGraphicsUpdateEventListener(GraphicsUpdateListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * Fires the GraphicsUpdate event, calling all the listeners.
     */
    private void fireGraphicsUpdateEvent()
    {
        GraphicsUpdateEvent event = new GraphicsUpdateEvent(this);
        Iterator i = listeners.iterator();
        while (i.hasNext())
        {
            ((GraphicsUpdateListener) i.next()).graphicsUpdateEvent(event);
        }
    }

    /**
     * Setting light position. It will shine to the origin.
     */
    @Override
    public void setLightPos(float x, float y, float z)
    {
        this.lightX = x;
        this.lightY = y;
        this.lightZ = z;
    }

    /**
     * OpenGL reshape method.
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        gl = drawable.getGL();
        GLU glu = new GLU();
        if (height <= 0) // Avoid a divide by zero error
        {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, zFar);
        glu.gluLookAt(cameraEyeX, cameraEyeY, cameraEyeZ, cameraToX, cameraToY, cameraToZ, 0, 1, 0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        // Setting light
        float[] lightPos =
        {
            (float) lightX, (float) lightY, (float) lightZ, 0
        };
        float[] lightAmbient =
        {
            0.1f, 0.1f, 0.1f, 1.0f
        };
        float[] lightDiffuse =
        {
            1.0f, 1.0f, 1.0f, 1.0f
        };
        // Set light parameters.
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, lightDiffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0);
    }

    /**
     * OpenGL display method.
     */
    @Override
    public void display(GLAutoDrawable drawable)
    {
        gl = drawable.getGL();
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Fire draw events
        fireGraphicsUpdateEvent();
        // Swap buffers
        long time = System.currentTimeMillis();
        canvas.swapBuffers();
        //System.out.println(System.currentTimeMillis() - time);
    }

    /**
     * OpenGL display changed method.
     */
    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
    {
    }

    /**
     * Stops the context.
     */
    @Override
    public void stop()
    {
        animator.stop();
    }

    /**
     * Setting camera, used only for 3D contexts.
     * @param ex camera x position
     * @param ey camera y position
     * @param ez camera z position
     * @param tx looking at x pos
     * @param ty looking at y pos
     * @param tz looking at z pos
     */
    @Override
    public void setCamera(float ex, float ey, float ez, float tx, float ty, float tz)
    {
        cameraToX = tx; // Center
        cameraToY = ty;
        cameraToZ = tz;
        cameraEyeX = ex; // Eye
        cameraEyeY = ey;
        cameraEyeZ = ez;
        canvas.reshape(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Drawing a half sphere.
     */
    private void draw3DClippedSphere(DrawData data)
    {
        GLU glu = new GLU();
        gl.glTranslated(0, 0, -data.width / 3);
        double[] planeEq =
        {
            0, 0, 1, -data.height
        };
        gl.glClipPlane(GL.GL_CLIP_PLANE0, planeEq, 0);
        gl.glEnable(GL.GL_CLIP_PLANE0);
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluSphere(quadric, data.width / 2, 16, 16);
        glu.gluDeleteQuadric(quadric);
        gl.glDisable(GL.GL_CLIP_PLANE0);
    }

    /**
     * Drawing a sphere.
     */
    private void draw3DSphere(DrawData data)
    {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluSphere(quadric, data.width / 2, 16, 16);
        glu.gluDeleteQuadric(quadric);
    }

    /**
     * Drawing a filled box.
     */
    private void draw2DFilledRect(DrawData data)
    {
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-data.width / 2, -data.height / 2, 0);
        gl.glVertex3f(data.width / 2, -data.height / 2, 0);
        gl.glVertex3f(data.width / 2, data.height / 2, 0);
        gl.glVertex3f(-data.width / 2, data.height / 2, 0);
        gl.glEnd();
    }

    /**
     * Drawing a box.
     */
    private void draw2DRect(DrawData data)
    {
        gl.glBegin(GL.GL_LINES);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-data.width / 2, -data.height / 2, 0);
        gl.glVertex3f(data.width / 2, -data.height / 2, 0);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(data.width / 2, -data.height / 2, 0);
        gl.glVertex3f(data.width / 2, data.height / 2, 0);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(data.width / 2, data.height / 2, 0);
        gl.glVertex3f(-data.width / 2, data.height / 2, 0);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-data.width / 2, data.height / 2, 0);
        gl.glVertex3f(-data.width / 2, -data.height / 2, 0);
        gl.glEnd();
    }
}
