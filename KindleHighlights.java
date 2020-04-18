import java.io.File; 
import java.util.Scanner; 

class highlightRecord {
    String title;
    String meta;
    String text;
}

public class KindleHighlights {
    public static void main( String[] args) throws Exception 
    {
        Scanner inFile = new Scanner(new File("/Users/striblet/Documents/CS/KindleHighlights/My Clippings.txt"));
        highlightRecord[] kindleDB;
        int count = 0;
        String text;

        // Count how many highlights I have
        while (inFile.hasNextLine()) {
            text = inFile.nextLine();
            if ( text.equals("==========" ) ) {
                count++;
            }
        }
        inFile.close();

        System.out.println( count + " records in file." );

        inFile = new Scanner(new File("/Users/striblet/Documents/CS/KindleHighlights/My Clippings.txt"));
        inFile.close();
       


    }
}   