import java.io.File;
import java.util.Scanner;

class KindleRecord {
    String title;
    String meta;
    String text;
}

public class KindleHighlights {
    public static void main( String[] args) throws Exception {
        File file = new File("/Users/striblet/Documents/CS/KindleHighlights/My Clippings.txt");
        int count;

        count = getCount(file);
        System.out.println(count + " records in file.");

    }

    public static int getCount ( File file ) throws Exception {
        Scanner inFile = new Scanner(file);
        int count = 0;
        String text;

        while (inFile.hasNextLine()) {
            text = inFile.nextLine();
            if (text.equals("=========="))
                count++;
        }
        inFile.close();

        return count;
    }

    public static KindleRecord[] makeKindleArray(int count, File file) throws Exception {
        KindleRecord[] array = new KindleRecord[count];
        Scanner inFile = new Scanner(file);
        int i = 0;

        while (inFile.hasNextLine()) {
            array[i] = new KindleRecord();
            array[i].title = inFile.nextLine();
            array[i].meta = inFile.nextLine();
            inFile.nextLine();
            array[i].text = inFile.nextLine();
            inFile.nextLine();
            i++;
        }
        inFile.close();

        return array;
    }
}