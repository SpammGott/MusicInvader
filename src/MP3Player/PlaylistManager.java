package MP3Player;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages playlists
 */
public class PlaylistManager {

    private Map<String, Playlist> playlisten = new HashMap<>();

    private PropertyChangeSupport changes;


    /**
     * Constructor, creates a default playlist in given directory
     * and adds playlists from said directory
     * @param dir directory
     */
    public PlaylistManager(String dir) {
        System.out.println("PlaylistManager");
        M3UParser.defaultPlaylist(dir);
        M3UParser.titleSongPlaylist(dir);
        addPlaylists(M3UParser.getPlaylist(dir));
        this.changes = new PropertyChangeSupport(getPlaylistList());
    }

    /**
     * Adds playlists from an arraylist of playlists
     * @param playlists arraylist of playlists
     */
    public void addPlaylists(ArrayList<Playlist> playlists){
        for (Iterator<Playlist> it = playlists.iterator(); it.hasNext(); ) {
            Playlist actPlaylist = it.next();
            playlisten.put(actPlaylist.getName(), actPlaylist);
        }
    }

    /**
     * Adds a single playlist
     * @param playlist a playlist
     */
    public void addPlaylists(Playlist playlist){
        if(playlist != null) {
            ArrayList<Playlist> old = getPlaylistList();
            playlisten.put(playlist.getName(), playlist);
            changes.firePropertyChange("MP3Player.Playlist added", old, getPlaylistList());
        }
    }

    /**
     * Gets a playlist by name
     * @param name  of a playlist
     * @return playlist
     */
    public Playlist getPlaylist(String name) {
        return playlisten.get(name);
    }

    /**
     * Returns an ArrayList of all MP3Player.Playlist
     * @return list of playlists
     */
    public ArrayList<Playlist> getPlaylistList(){
        ArrayList<Playlist> allPlaylist = new ArrayList<>();
        for (Map.Entry<String, Playlist> entry : playlisten.entrySet()){
            allPlaylist.add(entry.getValue());
        }
        return allPlaylist;
    }

    /**
     * Gets all songs of a given playlist
     * @param playlist playlistname
     * @return playlist
     */
    public Playlist getAllSongs(String playlist) {
        return playlisten.get(playlist);
    }

    public void addPropertyChangeListener(PropertyChangeListener propChngListn){
        changes.addPropertyChangeListener(propChngListn);
    }

}