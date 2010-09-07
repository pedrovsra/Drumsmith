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
    private String version;
    private String name;
    private String artist;
    private String album;
    private int year;
    private int length;
    private int notePointer;
    private List<NoteXml> notes;
    private AdvancedPlayer player;

    /**
     * @param musicXml Path to the music on xml format.
     * @param musicMP3 Path to the music on mp3 format.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws NumberFormatException
     * @throws NullPointerException
     * @throws DOMException
     * @throws JavaLayerException
     */
    public Music(String musicXml, String musicMP3) throws ParserConfigurationException, SAXException, IOException,
            NumberFormatException, NullPointerException, DOMException, JavaLayerException
    {
        notes = new ArrayList<NoteXml>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.parse(musicXml);

        readProperties(document);
        readNotes(document);

        FileInputStream input = new FileInputStream(musicMP3);
        player = new AdvancedPlayer(input);
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
    public NoteXml getNextNote(double time)
    {
        if (notePointer == notes.size())
            return null;

        NoteXml noteXml = notes.get(notePointer);

        if (time > noteXml.getTime())
        {
            notePointer++;
            return noteXml;
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

    /**
     * @param silent True to mute the music, false to unmute.
     */
    public void setSilent(boolean silent)
    {
        // TODO.
    }

    /**
     * Read the properties of the xml document.
     * @param document The xml document.
     * @throws NullPointerException
     * @throws NumberFormatException
     * @throws DOMException
     */
    private void readProperties(Document document) throws NullPointerException, NumberFormatException,
            DOMException
    {
        Node rootNode = document.getElementsByTagName("Properties").item(0);

        for (Node node =  rootNode.getFirstChild(); node != null; node = node.getNextSibling())
        {
            if (node.getNodeName().equals("#text"))
                continue;

            if (node.getNodeName().equals("Version"))
                version = node.getTextContent();
            else if (node.getNodeName().equals("Title"))
                name = node.getTextContent();
            else if (node.getNodeName().equals("Artist"))
                artist = node.getTextContent();
            else if(node.getNodeName().equals("Album"))
                album = node.getTextContent();
            else if(node.getNodeName().equals("Year"))
                year = Integer.parseInt(node.getTextContent());
            else if(node.getNodeName().equals("Length"))
                length = Integer.parseInt(node.getTextContent());

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
        for (int i = 0; i < nodes.getLength(); ++i)
        {
            Node node = nodes.item(i);

            double time = -1;
            double duration = -1;
            int track = -1;

            NamedNodeMap attributes = node.getAttributes();

            time = Double.parseDouble(attributes.getNamedItem("time").getNodeValue());
            duration = Double.parseDouble(attributes.getNamedItem("duration").getNodeValue());
            track = Integer.parseInt(attributes.getNamedItem("track").getNodeValue());

            notes.add(new NoteXml(time, duration, track));
        }
    }
}
