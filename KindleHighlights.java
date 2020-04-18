import java.io.File;
import java.util.Scanner;

class kindleRecord {
    String title;
    String meta;
    String text;
}

public class KindleHighlights {
    public static void main(String[] args) throws Exception {
        Scanner inFile = new Scanner(new File("/Users/striblet/Documents/CS/KindleHighlights/My Clippings.txt"));
        kindleRecord[] kindleDB;
        int count = 0;
        String text;

        // Count how many highlights I have
        while (inFile.hasNextLine()) {
            text = inFile.nextLine();
            if (text.equals("=========="))
                count++;
        }
        inFile.close();

        System.out.println(count + " records in file.");

        kindleDB = new kindleRecord[count];
        inFile = new Scanner(new File("/Users/striblet/Documents/CS/KindleHighlights/My Clippings.txt"));

        while (inFile.hasNextLine()) {
            kindleRecord record = new kindleRecord();
        }

        inFile.close();

    }
}