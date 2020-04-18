import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;

class KindleRecord {
    String title;
    String meta;
    String blank;
    String text;
    String dashed;
}

public class KindleHighlights {
    public static void main( String[] args) throws Exception {
        File currFile = new File("/Users/striblet/Documents/CS/KindleHighlights/My Clippings.txt");
        File sentFile = new File("/Users/striblet/Documents/CS/KindleHighlights/Sent Clippings.txt");
        int currCount, sentCount, currLength, sendLength;

        currCount = getCount(currFile);
        currLength = getLength(currFile);
        sentCount = getCount(sentFile);
        sendLength = getLength(sentFile);

        System.out.println(currCount + " records in most updated clippings file.");
        System.out.println(sentCount + " records in sent clippings file.");

        KindleRecord[] currentRecords = makeKindleArray(currCount, currFile);
        KindleRecord[] sentRecords;

        if ( currLength == sendLength ) {
            sentRecords = makeKindleArray(currCount, sentFile);
        } else {
            sentRecords = new KindleRecord[currCount];
            for (int i = 0; i < currCount; i++) {
                sentRecords[i] = new KindleRecord();
                sentRecords[i].title = "";
                sentRecords[i].meta = "";
                sentRecords[i].blank = "";
                sentRecords[i].text = "";
                sentRecords[i].dashed = "";
            }
        }

        KindleRecord[] recordsToSend = new KindleRecord[3];
        recordsToSend[0] = pickKindleRecord(currentRecords, sentRecords);
        recordsToSend[1] = pickKindleRecord(currentRecords, sentRecords);
        recordsToSend[2] = pickKindleRecord(currentRecords, sentRecords);

        // System.out.println( "------" );
        // System.out.println( randomRecord.title );
        // System.out.println( randomRecord.meta );
        // System.out.println( randomRecord.blank );
        // System.out.println( randomRecord.text );
        // System.out.println( randomRecord.dashed );
        // System.out.println( "------" );


        writeToFile("Sent Clippings.txt", sentRecords);
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

    public static KindleRecord[] makeKindleArray(int count, File file) throws Exception {
        KindleRecord[] array = new KindleRecord[count];
        Scanner inFile = new Scanner(file);
        int i = 0;
        if (0 < count ) { 
            while (inFile.hasNextLine()) {
                array[i] = new KindleRecord();
                array[i].title = inFile.nextLine();
                array[i].meta = inFile.nextLine();
                array[i].blank = inFile.nextLine();
                array[i].text = inFile.nextLine();
                array[i].dashed = inFile.nextLine();
                i++;
            }            
        }
 
        inFile.close();

        return array;
    }

    public static boolean writeToFile( String fileName, KindleRecord[] data ) throws Exception {
        PrintWriter outFile = new PrintWriter(fileName);

        for ( int i = 0; i < data.length; i++ ) {
            outFile.println(data[i].title);
            outFile.println(data[i].meta);
            outFile.println(data[i].blank);
            outFile.println(data[i].text);
            outFile.println(data[i].dashed);
        }

        outFile.close();

        return true;
    }

    public static KindleRecord pickKindleRecord( KindleRecord[] all, KindleRecord[] sent ) {
        int randomNum = (int)(Math.random()*all.length);
        KindleRecord newRecord = sent[randomNum];
        int loops = 0;

        while ( !newRecord.meta.equals("") && loops < all.length ) {
            randomNum = (int)(Math.random() * all.length);
            newRecord = sent[randomNum];
        }

        newRecord = all[randomNum];

        sent[randomNum] = newRecord;

        return newRecord;
    }

}