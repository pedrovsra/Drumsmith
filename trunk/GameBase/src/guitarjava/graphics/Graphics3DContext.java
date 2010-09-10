package guitarjava.graphics;

import com.sun.opengl.util.Animator;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    private List listeners; // Listeners for the graphics update
    private Animator animator; // Animator for OpenGL canvas
    private GL gl; // To use on draw operations
    private GLCanvas canvas; // OpenGL Canvas
    private int mulY = -1; // To invert y axis
    private int zFar = 2000; // zFar
    private GLCapabilities caps; // GL capabilities
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
    }

    /**
     * Draws the DrawData onto the screen.
     * @param data the data to be draw
     */
    public void draw(DrawData data)
    {
        if (data != null)
        {
            gl.glLoadIdentity();
            gl.glColor3f(data.color.getRed() / 256f, data.color.getGreen()
                    / 256f, data.color.getBlue() / 256f);
            gl.glTranslated(data.getX(), mulY * data.getY(), data.getZ());
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
            else if (data.type == DrawData.DRAW_3D_HALF_SPHERE)
            {
                draw3DHalfSphere(data);
            }
        }
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
    public void addGraphicsUpdateEventListener(GraphicsUpdateListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes an event lister from the GraphicsUpdate event.
     * @param listener the listener
     */
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
     * Initializing the context.
     */
    public void init(final Window component)
    {
        // Setting up opengl
        caps = new GLCapabilities();
        caps.setSampleBuffers(true); // Enable multisampling
        caps.setNumSamples(4);
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);
        component.add(canvas);
        animator = new Animator(canvas);
        // Adding close operation
        component.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                stop();
            }
        });
        // Others
        animator.start();
        canvas.setFocusable(false);
        // Test
        component.addKeyListener(new KeyAdapter()
        {

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    cameraEyeY += 5;
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    cameraEyeY -= 5;
                }
                else if (e.getKeyCode() == 'N')
                {
                    cameraEyeZ += 5;
                }
                else if (e.getKeyCode() == 'M')
                {
                    cameraEyeZ -= 5;
                }
                else
                {
                    return;
                }
                canvas.reshape(0, 0, component.getWidth(), component.getHeight());
                System.out.println(cameraEyeY + " " + cameraEyeZ);
            }
        });
    }

    /**
     * OpenGL init method.
     */
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
     * Setting light position. It will shine to the origin.
     */
    public void setLightPos(float x, float y, float z)
    {
        this.lightX = x;
        this.lightY = y;
        this.lightZ = z;
    }

    /**
     * OpenGL reshape method.
     */
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
    public void display(GLAutoDrawable drawable)
    {
        gl = drawable.getGL();
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Fire draw events
        fireGraphicsUpdateEvent();
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    /**
     * OpenGL display changed method.
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
    {
    }

    /**
     * Stops the context.
     */
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
    private void draw3DHalfSphere(DrawData data)
    {
        GLU glu = new GLU();
        gl.glTranslated(0, 0, -data.width / 3);
        double[] planeEq =
        {
            0, 0, 1, -data.width / 3
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
        gl.glVertex3d(-data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, data.height / 2, 0);
        gl.glVertex3d(-data.width / 2, data.height / 2, 0);
        gl.glEnd();
    }

    /**
     * Drawing a box.
     */
    private void draw2DRect(DrawData data)
    {
        gl.glBegin(GL.GL_LINES);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3d(-data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, -data.height / 2, 0);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3d(data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, data.height / 2, 0);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3d(data.width / 2, data.height / 2, 0);
        gl.glVertex3d(-data.width / 2, data.height / 2, 0);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3d(-data.width / 2, data.height / 2, 0);
        gl.glVertex3d(-data.width / 2, -data.height / 2, 0);
        gl.glEnd();
    }
}
