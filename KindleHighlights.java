import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;

class HighlightRecord {
    String title;
    String meta;
    String blank;
    String text;
    String dashed;
}

public class KindleHighlights {
    public static void main( String[] args) throws Exception {
        File currFile = new File("My Clippings.txt");
        File sentFile = new File("Sent Clippings.txt");
        int currCount, sentCount, currLength, sendLength;

        currCount = getCount(currFile);
        currLength = getLength(currFile);
        sentCount = getCount(sentFile); 
        sendLength = getLength(sentFile);

        System.out.println(currCount + " records in most updated clippings file.");
        System.out.println(sentCount + " records in sent clippings file.");

        HighlightRecord[] currentRecords = makeKindleArray(currCount, currFile);
        HighlightRecord[] sentRecords;

        if ( currLength == sendLength && currCount != sentCount) {
            sentRecords = makeKindleArray(currCount, sentFile);
        } else {
            sentRecords = new HighlightRecord[currCount];
            for (int i = 0; i < currCount; i++) {
                sentRecords[i] = new HighlightRecord();
                sentRecords[i].title = "";
                sentRecords[i].meta = "";
                sentRecords[i].blank = "";
                sentRecords[i].text = "";
                sentRecords[i].dashed = "";
            }
        }

        HighlightRecord[] recordsToSend = new HighlightRecord[3];
        recordsToSend[0] = pickHighlightRecord(currentRecords, sentRecords);
        recordsToSend[1] = pickHighlightRecord(currentRecords, sentRecords);
        recordsToSend[2] = pickHighlightRecord(currentRecords, sentRecords);

        // System.out.println( "------" );
        // System.out.println( randomRecord.title );
        // System.out.println( randomRecord.meta );
        // System.out.println( randomRecord.blank );
        // System.out.println( randomRecord.text );
        // System.out.println( randomRecord.dashed );
        // System.out.println( "------" );

        writeToFile("/Users/striblet/Documents/CS/KindleHighlights/Sent Clippings.txt", sentRecords, false);
        writeToFile("/Users/striblet/Documents/CS/KindleHighlights/Todays Clippings.txt", recordsToSend, true);
    }

    public static int getCount ( File file ) throws Exception {
        Scanner inFile = new Scanner(file);
        int count = 0;
        String text;

        while ( inFile.hasNextLine() ) {
            text = inFile.nextLine();
            if (text.equals("=========="))
                count++;
        }
        inFile.close();

        return count;
    }

    public static int getLength ( File file ) throws Exception {
        Scanner inFile = new Scanner(file);
        int count = 0;
        String text;

        while ( inFile.hasNextLine() ) {
            text = inFile.nextLine();
            if (!text.equals("!.randomtext<.>"))
                count++;
        }
        inFile.close();

        return count;
    }

    public static HighlightRecord[] makeKindleArray(int count, File file) throws Exception {
        HighlightRecord[] array = new HighlightRecord[count];
        Scanner inFile = new Scanner(file);
        int i = 0;
        if (0 < count ) { 
            while (inFile.hasNextLine()) {
                array[i] = new HighlightRecord();
                array[i].title = inFile.nextLine();
                array[i].title = array[i].title.replaceAll("[^\\x00-\\x7F]", "");
                array[i].meta = inFile.nextLine();
                array[i].meta = array[i].meta.replaceAll("[^\\x00-\\x7F]", "");
                array[i].blank = inFile.nextLine();
                array[i].blank = array[i].blank.replaceAll("[^\\x00-\\x7F]", "");
                array[i].text = inFile.nextLine();
                array[i].text = array[i].text.replaceAll("[^\\x00-\\x7F]", "");
                array[i].dashed = inFile.nextLine();
                array[i].dashed = array[i].dashed.replaceAll("[^\\x00-\\x7F]", "");
                i++;
            }            
        }
 
        inFile.close();

        return array;
    }

    public static boolean writeToFile( String fileName, HighlightRecord[] data, boolean includeBreaks ) throws Exception {
        PrintWriter outFile = new PrintWriter(fileName);

        for ( int i = 0; i < data.length; i++ ) {
            outFile.println(data[i].title);
            outFile.println(data[i].meta);
            outFile.println(data[i].blank);
            outFile.println(data[i].text);
            outFile.println(data[i].dashed);
            if ( includeBreaks ) {
                outFile.println("");
                outFile.println("");
                outFile.println("");
            }
        }

        outFile.close();

        return true;
    }

    public static HighlightRecord pickHighlightRecord( HighlightRecord[] all, HighlightRecord[] sent ) {
        int randomNum = (int)(Math.random()*all.length);
        HighlightRecord newRecord = sent[randomNum];
        int loops = 0;

        while ( !newRecord.meta.equals("") && loops < 100000 ) {
            randomNum = (int)(Math.random() * all.length);
            newRecord = sent[randomNum];
            loops++;
        }

        newRecord = all[randomNum];

        sent[randomNum] = newRecord;

        return newRecord;
    }

}