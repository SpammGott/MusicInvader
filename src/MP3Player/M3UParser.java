package MP3Player;

import com.mpatric.mp3agic.Mp3File;
import java.io.*;
import java.util.ArrayList;

/**
 * Parser to read songs out of a directory and create playlists
 */
public class M3UParser {

    /**
     * Creates a RandomAccessFile
     * @param f m3u file
     * @return a playlist
     */
    public static Playlist parse(File f){
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "r");
            return extendedM3U(raf, f);
        } catch(FileNotFoundException fne){
            System.out.println(fne.getMessage());
        } catch (IOException ie){
            System.out.println(ie.getMessage());
        }
        return null;
    }

    /**
     * Creates a new MP3Player.Playlist
     * @param raf RandomAccessFile
     * @param f m3u file
     * @return a playlist
     * @throws IOException
     */
    private static Playlist extendedM3U(RandomAccessFile raf, File f) throws IOException {
        Playlist newPlaylist = new Playlist(f.getName().substring(0, f.getName().length() - 4));
        String actLine;

        while((actLine = raf.readLine()) != null){
            if(!actLine.equals("") && actLine.charAt(0) != '#'){
                System.out.println("test"+f.getPath());
                newPlaylist.addTrack(actLine);
                //if(actLine.charAt(1) == ':' && actLine.charAt(2) == '\\' || actLine.substring(1, 5).equals("home")){
                //    newPlaylist.addTrack(actLine);
                //} else {
                //    newPlaylist.addTrack(f.getPath().replace(f.getName(), "") + File.separatorChar + actLine);
                //}
            }
        }
        return newPlaylist;
    }

    /**
     * Creates a default playlist (contains all songs in a directory)
     * @param path where the default playlist should be created
     */
    public static void defaultPlaylist(String path){
        System.out.println("defaultPlaylistCreator");
        System.out.println(path);
        Mp3File temp;
        try {
            PrintWriter writer = new PrintWriter(path + "defaultPlaylist.m3u", "UTF-8");
            ArrayList<Track> tracks = getSongs(path);
            for (int i = 0; i < tracks.size(); i++){
                temp = tracks.get(i).getMp3file();
                //checks for empty entrys (happens when track can't be created)
                if(temp != null)
                    writer.println(temp.getFilename());
            }
            writer.close();
            System.out.println("Got it");
        }catch (Exception e){
            System.out.println("File couldn't be created.");
            //e.printStackTrace();
        }
    }

    /**
     * Writes all songs in a directory in a ArrayList
     * @param path the directory
     * @return Arraylist of tracks
     */
    public static ArrayList<Track> getSongs(String path){
        File dir = new File(path);
        File[] files = dir.listFiles();
        ArrayList<Track> tracks = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String temp = files[i].getName();
            if (temp.substring(temp.lastIndexOf(".") + 1).equals("mp3")) {
                tracks.add(new Track(files[i].getPath()));
            }
        }
        return tracks;
    }

    /**
     * Writes all playlists in a directory in a Arraylist
     * @param path the directory
     * @return Arraylist of playlists
     */
    public static ArrayList<Playlist> getPlaylist(String path){
        File dir = new File(path);
        File[] files = dir.listFiles();
        ArrayList<Playlist> playlist = new ArrayList<>();
        Playlist tempPlaylist;
        try {
            for(int i = 0; i < files.length; i++){
                String temp = files[i].getName();
                if(temp.substring(temp.lastIndexOf(".") + 1).equals("m3u")){
                    tempPlaylist = parse(files[i]);
                    if(tempPlaylist != null)
                        playlist.add(parse(files[i]));
                }
            }
        }catch (Exception e){
            System.out.println("Sorry but we can't find any music here :(");
            System.out.println(path);
        }

        return playlist;
    }
}
