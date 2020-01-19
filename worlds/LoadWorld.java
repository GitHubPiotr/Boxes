package boxes.worlds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadWorld {

    public ArrayList<Integer> arrayList = new ArrayList<>();
    static String x, y;
    private static int iX = 0;
    private static int iY = 0;
    
    // LOAD WORLD FROM FILE
    public LoadWorld(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String s;
            s = reader.readLine();
            String [] wordsXY = s.split(" ");
            x = wordsXY[0];
            iX = Integer.parseInt(x);
            y = wordsXY[1];
            iY = Integer.parseInt(y);

            while ((s = reader.readLine()) != null) {
                String[] words = s.split(" ");
                for (int i = 0; i < iY; i++) {
                    arrayList.add(Integer.parseInt(words[i]));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> getArrayList(int i) {
        return arrayList;
    }

    public static int getX() {
        return iX;
    }

    public static int getY() {
        return iY;
    }

}
