package guitarjava.test;

import guitarjava.game.Music;
import guitarjava.game.NoteXml;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lucasjadami
 */
public class MusicTest
{
    private Music music;

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /*@Test
    public void testThings() throws Exception
    {
        music = new Music("musics/xml/Music1_4.xml", "musics/mp3/Music1.mp3", "musics/score/Music1.sco");

        Thread t = new Thread()
        {
            public void run()
            {
                try
                {
                    music.play();
                }
                catch (JavaLayerException ex)
                {
                    Logger.getLogger(MusicTest.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("THREAD END");
            }
        };

        t.start();
        Thread.sleep(10000);
        music.stop();
        Thread.sleep(10000);
        System.out.println("TEST END");
    }*/

    @Test
    public void testThings() throws Exception
    {
        music = new Music("musics/xml/Music1_4.xml", "musics/mp3/Music1.mp3", "musics/score/Music1.sco");

        music.play();
        Thread.sleep(20500);
        music.stop();
        Thread.sleep(5000);
        music.play(true);
        Thread.sleep(10000);
    }
}