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
    private static String VERSION = "0.1";
    private static String NAME = "I Love Rock and Roll";
    private static String ARTIST = "Joan Jett and the Blackhearts";
    private static String ALBUM = "Vazio";
    private static int YEAR = 0;
    private static int LENGTH = 177;
    private static int NUMBER_OF_NOTES = 475;

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
    public void testLoadMusic() throws Exception
    {
        music = new Music("musics/xml/Music1_4.xml", "musics/mp3/Music1.mp3");

        assertTrue(music.getVersion().equals(VERSION));
        assertTrue(music.getName().equals(NAME));
        assertTrue(music.getArtist().equals(ARTIST));
        assertTrue(music.getAlbum().equals(ALBUM));
        assertTrue(music.getYear() == YEAR);
        assertTrue(music.getLength() == LENGTH);
        assertTrue(music.getNumberOfNotes() == NUMBER_OF_NOTES);
    }

    @Test
    public void testNotes() throws Exception
    {
        float time = 100000;

        music = new Music("musics/xml/Music1_4.xml", "musics/mp3/Music1.mp3");
        
        NoteXml note = music.getNextNote(time);
        while (note != null)
        {
            System.out.println(note.getNumber());
            note = music.getNextNote(time);
        }
    }*/

    @Test
    public void testThings() throws Exception
    {
        for (int i = 0; i < 10; ++i)
        {
            music = new Music("musics/xml/Music1_4.xml", "musics/mp3/Music1.mp3", "musics/score/Music1.sco");
            Thread thread = new Thread()
            {
                @Override
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
                }
            };
            
            thread.start();
            Thread.sleep(10000);
            music.stop();
        }
    }
}