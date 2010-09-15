package guitarjava.test;

import guitarjava.game.Music;
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

    @Test
    public void testLoadMusic() throws Exception
    {
        music = new Music("musics/xml/Music1_4.xml", "musics/mp3/Music1.mp3");
  
        music.play();

        int i = 0;
        while (!music.setSilent(true))
        {
            System.out.println(i++);
        }
        assertTrue(music.setSilent(true));

        assertTrue(music.getVersion().equals(VERSION));
        assertTrue(music.getName().equals(NAME));
        assertTrue(music.getArtist().equals(ARTIST));
        assertTrue(music.getAlbum().equals(ALBUM));
        assertTrue(music.getYear() == YEAR);
        assertTrue(music.getLength() == LENGTH);
        assertTrue(music.getNumberOfNotes() == NUMBER_OF_NOTES);

        Thread.sleep(12000);
    }
}