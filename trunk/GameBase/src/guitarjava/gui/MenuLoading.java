package guitarjava.gui;

import guitarjava.game.Music;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lucasjadami
 */
public class MenuLoading extends Loading
{
    Map<String, List<Music>> musics;
    
    public MenuLoading()
    {
        
    }

    @Override
    public void load()
    {
        loadThread = new Thread()
        {

            @Override
            public void run()
            {
                loadMusics();

                WindowListener gui = getWindowListeners()[0];
                if (gui != null)
                    gui.windowClosed(new WindowEvent(MenuLoading.this, 0));
            }

        };

        super.load();
    }

    public Map<String, List<Music>> getMusics()
    {
        return musics;
    }
    
    private void loadMusics()
    {
        File folder = new File("musics/xml/");

        File[] files = folder.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".xml");
            }
        });

        int total = files.length;
        int loaded = 0;

        musics = new HashMap<String, List<Music>>();

        for (File file : files)
        {
            setState("Loading music file: " + file.getName());

            try
            {
                Music music = new Music(file.getPath(), "musics/mp3/" + file.getName().substring(0, file.getName().length() - 6) + ".mp3");

                String key = music.getName();
                
                List<Music> musicsList = musics.get(key);

                if (musicsList == null)
                {
                    musicsList = new ArrayList<Music>();
                    musics.put(key, musicsList);
                }

                musicsList.add(music);       
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

            loaded++;
            setProgress(loaded * 100 / total);
        }
    }
}
