import com.mpatric.mp3agic.Mp3File;
import java.io.File;

/**
 * Contains a song and its info
 */
public class Track {
    private Mp3File mp3file;
    private String name;
    private long length;
    private String filename, track, artist, title, album, year;
    private int genre;
    private byte[] albumImageData;
    private boolean hasWorked = false;

    /**
     * Constructor, creates an MP3File out of a filename and fetches metadata
     * @param filename path of the file
     */
    public Track(String filename){
        this.filename = filename;
        try {
            mp3file = new Mp3File(filename);
            hasWorked = true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        if(hasWorked) {
            length = mp3file.getLengthInSeconds();
            String seperator = String.valueOf(File.separatorChar);
            if(seperator.equals("\\"))
                    seperator = seperator + seperator;
            String[] temp = filename.split(String.valueOf(seperator));
            name = temp[temp.length-1].substring(0, temp[temp.length-1].lastIndexOf("."));
            if (mp3file.hasId3v1Tag()) {
                this.track = mp3file.getId3v1Tag().getTrack();
                this.artist = mp3file.getId3v1Tag().getArtist();
                this.title = mp3file.getId3v1Tag().getTitle();
                this.album = mp3file.getId3v1Tag().getAlbum();
                this.year = mp3file.getId3v1Tag().getYear();
                this.genre = mp3file.getId3v1Tag().getGenre();
            }
            if (mp3file.hasId3v2Tag()) {
                this.track = mp3file.getId3v2Tag().getTrack();
                this.artist = mp3file.getId3v2Tag().getArtist();
                this.title = mp3file.getId3v2Tag().getTitle();
                this.album = mp3file.getId3v2Tag().getAlbum();
                this.year = mp3file.getId3v2Tag().getYear();
                this.genre = mp3file.getId3v2Tag().getGenre();
                albumImageData = mp3file.getId3v2Tag().getAlbumImage();
            }
        } else{
            System.out.println(filename);
            System.out.println("Song konnte nicht geladen werden.");
        }
    }


    public Mp3File getMp3file() {return mp3file;}

    public long getLength() {return length;}

    public String getName() {return name;}

    public String getFilename() {return filename;}

    public String getTrack() {return track;}

    public String getArtist() {return artist;}

    public String getTitle() {return title;}

    public String getAlbum() {return album;}

    public String getYear() {return year;}

    public int getGenre() {return genre;}

    public byte[] getAlbumImageData() {return albumImageData;}

    public boolean hasWorked(){return hasWorked;}

    @Override
    public String toString(){
        String s;
        if(name != null)
            s = this.name + "\t\t\t";
        else
            s = "-\t\t\t\t";

        if(artist != null)
            s = s + this.artist + "\t\t\t";
        else
            s = s + "-\t\t\t\t";

        s = s + (int)(this.length / 60);
        if(this.length % 60 < 10)
            s = s + ":0" + (this.length) % 60;
        else
            s = s + ":" + (this.length) % 60;
        return  s;
    }

}
