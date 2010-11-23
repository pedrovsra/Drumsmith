package guitarjava.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Contains all information about a music in the game. It is also responsable for
 * the IO part.
 * @author lucasjadami
 */
public class Music
{
    private static final int MUSIC_GAIN = 8;

    private String xmlPath;
    private String mp3Path;
    private String version;
    private String name;
    private String artist;
    private String album;
    private String difficulty;
    private int year;
    private int length;
    private int notePointer;
    private int soloPointer;
    private boolean silent;
    private List<NoteXml> notes;
    private List<SoloXml> solos;
    private AdvancedPlayer player;

    public Music(String musicXml, String musicMP3) throws ParserConfigurationException, SAXException, IOException,
            NumberFormatException, NullPointerException, DOMException, JavaLayerException, Exception
    {
        this.xmlPath = musicXml;
        this.mp3Path = musicMP3;
        
        notes = new ArrayList<NoteXml>();
        solos = new ArrayList<SoloXml>();

        readProperties(musicXml);

        reopen();
    }

    public final void reopen() throws Exception
    {
        FileInputStream input = new FileInputStream(mp3Path);
        player = new AdvancedPlayer(input);
        notePointer = 0;
        soloPointer = 0;
    }

    public void readNotes() throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.parse(this.xmlPath);

        readNotes(document);
        readSolos(document);
    }

    /**
     * @return The number of notes of the music.
     */
    public int getNumberOfNotes()
    {
        return notes.size();
    }

    /**
     * @return The version.
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * @return The name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The artist.
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * @return The album.
     */
    public String getAlbum()
    {
        return album;
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    /**
     * @return The year.
     */
    public int getYear()
    {
        return year;
    }

    /**
     * @return The length.
     */
    public int getLength()
    {
        return length;
    }

    /**
     * @param time The current time of the execution.
     * @return If the time is equal or greater than the current time, the entity
     * of the next node. Null otherwise.
     */
    public NoteXml getNextNote(float time)
    {
        if (notePointer == notes.size())
        {
            return null;
        }

        NoteXml noteXml = notes.get(notePointer);

        if (time > noteXml.getTime())
        {
            notePointer++;
            return noteXml;
        }

        return null;
    }

    /**
     * @param time The current time of the execution.
     * @return If the time is equal or greater than the current time, the entity
     * of the next solo. Null otherwise.
     */
    public SoloXml getNextSolo(float time)
    {
        if (soloPointer == solos.size())
        {
            return null;
        }

        SoloXml soloXml = solos.get(soloPointer);

        if (time > soloXml.getTime())
        {
            soloPointer++;
            return soloXml;
        }

        return null;
    }

    /**
     * Plays the music.
     * @throws JavaLayerException
     */
    public void play() throws JavaLayerException
    {
        player.play();
    }

    public void stop()
    {
        player.close();
    }

    /**
     * @param silent True to mute the music, false to unmute.
     */
    public boolean setSilent(boolean silent)
    {
        // Checks if the music needs to be toggled, so it won't force silent every time.
        if (this.silent == silent)
            return true;

        this.silent = silent;

        if (silent)
            return player.setGain(-MUSIC_GAIN);
        else
            return player.setGain(0);
    }

    /**
     * @return The current position of the music.
     */
    public int getCurrentPosition()
    {
        return player.getCurrentPosition();
    }
    
    private void readProperties(String musicXml) throws NullPointerException, NumberFormatException,
            DOMException, ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.parse(musicXml);

        Node rootNode = document.getElementsByTagName("Properties").item(0);

        for (Node node = rootNode.getFirstChild(); node != null; node = node.getNextSibling())
        {
            if (node.getNodeName().equals("#text"))
            {
                continue;
            }

            if (node.getNodeName().equals("Version"))
            {
                version = node.getTextContent();
            }
            else if (node.getNodeName().equals("Title"))
            {
                name = node.getTextContent();
            }
            else if (node.getNodeName().equals("Artist"))
            {
                artist = node.getTextContent();
            }
            else if (node.getNodeName().equals("Album"))
            {
                album = node.getTextContent();
            }
            else if (node.getNodeName().equals("Difficulty"))
            {
                difficulty = node.getTextContent();
            }
            else if (node.getNodeName().equals("Year"))
            {
                year = Integer.parseInt(node.getTextContent());
            }
            else if (node.getNodeName().equals("Length"))
            {
                length = Integer.parseInt(node.getTextContent());
            }

            node = node.getNextSibling();
        }
    }

    /**
     * Read the notes of the xml document.
     * @param document The xml document.
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    private void readNotes(Document document) throws NullPointerException, NumberFormatException
    {
        NodeList nodes = document.getElementsByTagName("Note");
        int number = 0;
        float lastTime = -1;

        for (int i = 0; i < nodes.getLength(); ++i)
        {
            Node node = nodes.item(i);

            float time = -1;
            float duration = -1;
            int track = -1;
            int special = 0;

            NamedNodeMap attributes = node.getAttributes();

            time = Float.parseFloat(attributes.getNamedItem("time").getNodeValue());
            duration = Float.parseFloat(attributes.getNamedItem("duration").getNodeValue());
            track = Integer.parseInt(attributes.getNamedItem("track").getNodeValue());
            //special = Integer.parseInt(attributes.getNamedItem("special").getNodeValue());

            if (time != lastTime)
                number++;

            lastTime = time;
            
            notes.add(new NoteXml(time, duration, track, number, special));
        }
    }

    /**
     * Read the solos of the xml document.
     * @param document The xml document.
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    private void readSolos(Document document) throws NullPointerException, NumberFormatException
    {
        NodeList nodes = document.getElementsByTagName("Solo");
        int number = 0;
        float lastTime = -1;

        for (int i = 0; i < nodes.getLength(); ++i)
        {
            Node node = nodes.item(i);

            float time = -1;
            float duration = -1;

            NamedNodeMap attributes = node.getAttributes();

            time = Float.parseFloat(attributes.getNamedItem("time").getNodeValue());
            duration = Float.parseFloat(attributes.getNamedItem("duration").getNodeValue());

            solos.add(new SoloXml(time, duration));
        }
    }
}
