package Game.GameUtils.Utils;

import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * Creates a leaderboard file to save Leaderboards and to display them
 */
public class Leaderboard {


    private String leaderboard[] = new String[10];
    private String username = "XXX";

    public Leaderboard(){
        String actLine;
        int i = 0;
        try{
            RandomAccessFile raf = new RandomAccessFile("res/highscore.txt", "rw");
            while((actLine = raf.readLine()) != null){
                if(i < 10) {
                    leaderboard[i] = actLine;
                    i++;
                }
            }
            raf.close();
        } catch(IOException e){
            System.out.println("Error 404: File not found!");
        }
    }


    public void addHighscore(int punkte, String playername) {
        if(playername.equals(""))
            playername = username;
        int tempPoints;
        int i = 0;
        String temp = (playername + ";" + punkte + ";");
        if (leaderboard[0] != null) {
            for (String actLine : leaderboard) {
                if(actLine == null)
                    break;
                System.out.println(actLine);
                String tempLine[] = actLine.split(";");
                tempLine[1] = tempLine[1].replace("\0", "");
                System.out.println(tempLine[1]);
                tempPoints = Integer.parseInt(tempLine[1]);
                if (punkte > tempPoints) {
                    break;
                }
                i++;
            }

            if (i < 10) {
                String newLeaderboard[] = new String[10];
                for (int j = 0; j < 10; j++) {
                    if (j < i) {
                        newLeaderboard[j] = leaderboard[j];
                    } else if (j == i) {
                        newLeaderboard[j] = temp;
                    } else {
                        newLeaderboard[j] = leaderboard[j - 1];
                    }
                }
                leaderboard = newLeaderboard;
            }
        }else{
            leaderboard = new String[10];
            leaderboard[0] = temp;
        }

        try {
            RandomAccessFile raf = new RandomAccessFile("res/highscore.txt", "rw");
            for (String actLine : leaderboard) {
                if(actLine != null) {
                    actLine = actLine.replace("\n", "").replace("\r", "");//.replace("\0", "");
                    raf.writeChars(actLine + "\n");
                }
            }
            raf.close();
        } catch (IOException e) {
            System.out.println("Error 404: File not found!");
        }
    }

    public String[] getLeaderboard() {
        return leaderboard;
    }
}
