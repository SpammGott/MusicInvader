package MP3Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages Songs in a MP3Player.Playlist
 */
public class Playlist {

    private List<Track> list = new ArrayList<>();
    private String name;
    private int listSize = 0;
    private int actTrack = 0;
    private boolean shuffle;

    /**
     * Constructor
     * @param name of the playlist
     */
    public Playlist(String name){
        this.name = name;
        this.shuffle = false;
    }

    /**
     * Adds a track to a playlist
     * @param filename
     */
    public void addTrack(String filename){
        Track newTrack = new Track(filename);
        if(newTrack.hasWorked()) {
            list.add(newTrack);
            listSize++;
        }
    }

    /**
     * Gets track at specified position
     * @param index position
     * @return track
     */
    public Track getTrack(int index){
        if(index < listSize && index >= 0){
            actTrack = index;
            return getTrack();
        }

        return null;
    }

    /**
     * Gets the current track
     * @return current track
     */
    public Track getTrack() {
        return list.get(actTrack);
    }

    /**
     * Gets the next track, or the first if the current song is the last song
     * @return next track
     */
    public Track getNextTrack() {
        if(shuffle){
            int nextTrack;
            do {
                nextTrack = ThreadLocalRandom.current().nextInt(0, listSize);
            } while(nextTrack == actTrack);
            actTrack = nextTrack;
            System.out.println(list.get(actTrack).getTitle());
            return list.get(actTrack);
        }
        if((actTrack + 1) < listSize) {
            actTrack++;
        } else {
            actTrack = 0;
        }
        System.out.println(list.get(actTrack).getTitle());
        return list.get(actTrack);
    }

    public Track getNextTrackWithoutChangingIndex(){
        int temp = actTrack;
        if((actTrack + 1) < listSize) {
            temp++;
        } else {
            temp = 0;
        }
        return list.get(temp);
    }

    public Track getPrevTrackWithoutChangingIndex(){
        int temp = actTrack;
        if(actTrack <= 0) {
            temp = listSize-1;
        } else {
            temp--;
        }
        return list.get(temp);
    }

    /**
     * Gets the name of the MP3Player.Playlist
     * @return playlist name
     */
    public String getName() {return name;}

    /**
     * Gets list of tracks
     * @return list of tracks
     */
    public List<Track> getList(){
        return list;
    }

    /**
     * @return name and size of playlist as string
     */
    @Override
    public String toString(){
        return name + "\t\t" + listSize;
    }
}
