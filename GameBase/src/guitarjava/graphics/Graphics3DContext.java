package guitarjava.graphics;

import com.sun.opengl.util.Animator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

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
    // Camera variables
    private double cameraFromX = 0;
    private double cameraFromY = 0;
    private double cameraFromZ = 0;
    private double cameraToX = 0;
    private double cameraToY = 0;
    private double cameraToZ = 0;

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
            gl.glTranslated(data.x, mulY*data.y, data.z);
            if (data.type == DrawData.DRAW_2D_RECT)
            {
                drawBox(data);
            }
            else if (data.type == DrawData.DRAW_2D_FILL_RECT)
            {
                drawFilledBox(data);
            }
            else if (data.type == DrawData.DRAW_NOTE)
            {
                GLU glu = new GLU();
                GLUquadric quadric = glu.gluNewQuadric();
                glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
                glu.gluSphere(quadric, data.width / 5, 32, 32);
                glu.gluDeleteQuadric(quadric);
                quadric = glu.gluNewQuadric();
                glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
                glu.gluDisk(quadric, data.width / 5, data.width / 2, 32, 32);
                glu.gluDeleteQuadric(quadric);
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
        // Starting
        
        // Setting up opengl
        canvas = new GLCanvas();
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

        component.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    cameraFromY += 5;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    cameraFromY -= 5;
                else if (e.getKeyCode() == 'N')
                    cameraFromZ += 5;
                else if (e.getKeyCode() == 'M')
                    cameraFromZ -= 5;
                else
                    return;
                canvas.reshape(0, 0, component.getWidth(), component.getHeight());
                System.out.println(cameraFromY + " " + cameraFromZ);
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
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
        // Setting light
        float[] lightPos = {(float)cameraFromX, (float)cameraFromY, (float)cameraFromZ, 1};
        float[] lightAmbient = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        // Set light parameters.
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, lightDiffuse, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHTING);
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
        glu.gluLookAt(cameraFromX, cameraFromY, cameraFromZ, cameraToX, cameraToY, cameraToZ, 0, 1, 0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
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
     * Drawing a filled box.
     */
    private void drawFilledBox(DrawData data)
    {
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f( 0.0f, 0.0f, 1.0f);
        gl.glVertex3d(-data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, data.height / 2, 0);
        gl.glVertex3d(-data.width / 2, data.height / 2, 0);
        gl.glEnd(); // Done Drawing The Quad
    }

    /**
     * Drawing a box.
     */
    private void drawBox(DrawData data)
    {
        //gl.glLineWidth(100);
        gl.glBegin(GL.GL_LINES);
        gl.glNormal3f( 0.0f, 0.0f, 1.0f);
        gl.glVertex3d(-data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, -data.height / 2, 0);
        gl.glNormal3f( 0.0f, 0.0f, 1.0f);
        gl.glVertex3d(data.width / 2, -data.height / 2, 0);
        gl.glVertex3d(data.width / 2, data.height / 2, 0);
        gl.glNormal3f( 0.0f, 0.0f, 1.0f);
        gl.glVertex3d(data.width / 2, data.height / 2, 0);
        gl.glVertex3d(-data.width / 2, data.height / 2, 0);
        gl.glNormal3f( 0.0f, 0.0f, 1.0f);;
        gl.glVertex3d(-data.width / 2, data.height / 2, 0);
        gl.glVertex3d(-data.width / 2, -data.height / 2, 0);
        gl.glEnd();
    }

    /**
     * Setting camera, used only for 3D contexts.
     * @param fx camera x position
     * @param fy camera y position
     * @param fz camera z position
     * @param tx looking at x pos
     * @param ty looking at y pos
     * @param tz looking at z pos
     */
    public void setCamera(double fx, double fy, double fz, double tx, double ty, double tz)
    {
        cameraToX = tx; // Center
        cameraToY = ty;
        cameraToZ = tz;
        cameraFromX = fx; // Eye
        cameraFromY = fy;
        cameraFromZ = fz;
        canvas.reshape(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
