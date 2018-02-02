package Game.GameUtils.Entity;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class Leaderboard {

    //private static File dir = new File("res/highscore.txt");
    private File dir;
    private String leaderboard[];

    public Leaderboard(){
        System.out.println(System.getProperty("user.dir") + "res/highscore.txt");
        dir = new File(System.getProperty("user.dir") + "res/highscore.txt");
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
        int tempPoints;
        int i = 0;
        String temp = (playername + ";" + punkte);
        System.out.println(temp.split(";")[1]);
        if (leaderboard != null) {
            for (String actLine : leaderboard) {
                tempPoints = Integer.valueOf(actLine.split(";")[1]);
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
            RandomAccessFile raf = new RandomAccessFile(dir, "rw");
            for (String actLine : leaderboard) {
                System.out.println(actLine);
                raf.writeChars(actLine);
            }
            raf.close();
        } catch (IOException e) {
            System.out.println("Error 404: File not found!");
        }
    }
}
