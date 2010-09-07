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
public class Graphics3DContext extends JFrame implements GraphicsInterface, GLEventListener
{

    static final public int GRAPHICS_WIDTH = 500; // Width
    static final public int GRAPHICS_HEIGHT = 600; // Height
    private List listeners; // Listeners for the graphics update
    private Animator animator; // Animator for OpenGL canvas
    GL glDrawable; // To use on draw operations
    private double cameraY = -12;
    private double cameraZ = 5;

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
            glDrawable.glLoadIdentity();
            glDrawable.glTranslated(data.x, data.y, 0);
            glDrawable.glColor3f(data.color.getRed() / 256f, data.color.getGreen()
                    / 256f, data.color.getBlue() / 256f);
            if (data.type == DrawData.DRAW_BOX)
            {
                drawBox(data);
            }
            else if (data.type == DrawData.DRAW_HALF_SPHERE)
            {
                GLU glu = new GLU();
                GLUquadric quadric = glu.gluNewQuadric();
                glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
                glu.gluDisk(quadric, 0.12, 0.3, 32, 32);
                glu.gluDeleteQuadric(quadric);
            }
        }
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
    public void init(Window component)
    {
        // Starting
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Setting up opengl
        final GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(this);
        add(canvas);
        setResizable(false);
        setIgnoreRepaint(true);
        setSize(GRAPHICS_WIDTH, GRAPHICS_HEIGHT);
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
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
        animator.start();

        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == 'A')
                    ++cameraY;
                else if (e.getKeyCode() == 'S')
                    --cameraY;
                else if (e.getKeyCode() == 'Q')
                    ++cameraZ;
                else if (e.getKeyCode() == 'W')
                    --cameraZ;
                canvas.reshape(0, 0, getWidth(), getHeight());
                System.out.println(cameraY + " " + cameraZ);
            }
        });
    }

    /**
     * OpenGL init method.
     */
    public void init(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        // Enable VSync
        gl.setSwapInterval(1);
        // Setup the drawing area and shading mode
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
    }

    /**
     * OpenGL reshape method.
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        if (height <= 0) // Avoid a divide by zero error
        {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 1000);
        glu.gluLookAt(0, cameraY, cameraZ, 0, 0, 0, 0, 1, 0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * OpenGL display method.
     */
    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        glDrawable = gl;
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        //setLight();
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
     * Setting light.
     */
    private void setLight()
    {
        // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {0, -10, 5, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        glDrawable.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        glDrawable.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
        glDrawable.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        glDrawable.glEnable(GL.GL_LIGHT1);
        glDrawable.glEnable(GL.GL_LIGHTING);

        // Set material properties.
        float[] rgba = {0.3f, 0.5f, 1f};
        glDrawable.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        glDrawable.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        glDrawable.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
    }

    /**
     * Stops the context.
     */
    public void stop()
    {
        animator.stop();
    }

    /**
     * Drawing a box.
     */
    private void drawBox(DrawData data)
    {
        glDrawable.glBegin(GL.GL_QUADS);
        glDrawable.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
        glDrawable.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
        glDrawable.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
        glDrawable.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)
        glDrawable.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad (Bottom)
        glDrawable.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad (Bottom)
        glDrawable.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Bottom)
        glDrawable.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Bottom)
        glDrawable.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
        glDrawable.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
        glDrawable.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Front)
        glDrawable.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Front)
        glDrawable.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Back)
        glDrawable.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Back)
        glDrawable.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Back)
        glDrawable.glVertex3f(1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Back)
        glDrawable.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
        glDrawable.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
        glDrawable.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
        glDrawable.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Left)
        glDrawable.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
        glDrawable.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
        glDrawable.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Right)
        glDrawable.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Right)
        glDrawable.glEnd(); // Done Drawing The Quad

    }
}