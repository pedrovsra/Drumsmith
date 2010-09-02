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
    private static String MUSIC_NAME = "TestMusic.xml";
    private static String VERSION = "0.1";
    private static String NAME = "Can't Stop";
    private static String ARTIST = "Red Hot Chili Peppers";
    private static String ALBUM = "Vazio";
    private static int YEAR = 1;
    private static int LENGTH = 267;
    private static int NUMBER_OF_NOTES = 1505;

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
        music = new Music("TestMusic.xml");

        assertTrue(music.getVersion().equals(VERSION));
        assertTrue(music.getName().equals(NAME));
        assertTrue(music.getArtist().equals(ARTIST));
        assertTrue(music.getAlbum().equals(ALBUM));
        assertTrue(music.getYear() == YEAR);
        assertTrue(music.getLength() == LENGTH);
        assertTrue(music.getNumberOfNotes() == NUMBER_OF_NOTES);
    }
}