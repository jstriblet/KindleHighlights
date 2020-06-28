import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Highlight {
    String title;
    String author;
    String pageNum;
    String location;
    String date;
    String text;
    public String toString() {
        return "title: " + title + " by: " + author;
    }
}

public class KindleHighlights {
    public static void main( String[] args) throws Exception {
        File currFile = new File("My Clippings.txt");
        File sentFile = new File("Sent Clippings.txt");
        int currHighlightCount, sentHighlightCount, currLineCount, sentLineCount;
        int numToPick = 3;

        currHighlightCount = getHighlightCount(currFile); // get the number of highlights in "My Clippings.txt"
        currLineCount = getLineCount(currFile); // get the number of lines in "My Clippings.txt"

        sentHighlightCount = getHighlightCount(sentFile); // get the number of highlights in "Sent Clippings.txt"
        sentLineCount = getLineCount(sentFile); // get the number of lines in "Sent Clippings.txt"

        System.out.println(currHighlightCount + " records in most updated clippings file.");
        System.out.println(sentHighlightCount + " records in sent clippings file.");
        System.out.println(currLineCount + " lines updated clippings file.");
        System.out.println(sentLineCount + " lines sent clippings file.");

        Highlight[] currentRecords = makeHighlights(currHighlightCount, currFile);
        Highlight[] sentRecords;

        if ( currLineCount == sentLineCount && currHighlightCount != sentHighlightCount) { // The current number of highlights in both files are equal && we have not gotten threw all the highlights yet
            sentRecords = makeHighlights(currHighlightCount+1, sentFile); // I'm not quite sure why we have to add one here, but to get the counts right it had to be done.
        } else {
            sentRecords = new Highlight[currHighlightCount+1]; // again, still not sure why the count is wrong.
            for (int i = 0; i <= currHighlightCount; i++) {
                sentRecords[i] = new Highlight();
                sentRecords[i].title = "";
                sentRecords[i].author = "";
                sentRecords[i].pageNum = "";
                sentRecords[i].location = "";
                sentRecords[i].date = "";
                sentRecords[i].text = "";
            }
        }

        Highlight[] recordsToSend = new Highlight[numToPick];
        for (int i = 0; i < recordsToSend.length; i++) {
            recordsToSend[i] = pickHighlight(currentRecords, sentRecords);
        }

        // System.out.println( "------" );
        // System.out.println( randomRecord.title );
        // System.out.println( randomRecord.title );
        // System.out.println( randomRecord.meta );
        // System.out.println( randomRecord.blank );
        // System.out.println( randomRecord.text );
        // System.out.println( randomRecord.dashed );
        // System.out.println( "------" );

        writeToFile("Sent Clippings.txt", sentRecords);
        writeToFile("Todays Clippings.txt", recordsToSend);
        renderEmail(recordsToSend, "Todays Email Body.htm");
    }

    public static int getHighlightCount ( File file ) throws Exception {
        Scanner inFile = new Scanner(file);
        int count = 0;
        String text;

        while ( inFile.hasNextLine() ) {
            text = inFile.nextLine();
            if (text.equals("==========")) {
                if (inFile.hasNextLine()) {
                    text = inFile.nextLine();
                    if ( text.length() > 5 ) {
                        count++;
                    }
                }                    
            }
        }
        inFile.close();

        return count;
    }

    public static int getLineCount ( File file ) throws Exception {
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

    public static Highlight[] makeHighlights(int count, File file) throws Exception {
        Highlight[] array = new Highlight[count];
        Scanner inFile = new Scanner(file);
        int i = 0;

        if (0 < count ) { 
            while (inFile.hasNextLine() && i < count ) {
                array[i] = new Highlight();
                
                String line = inFile.nextLine();
                array[i].title = line.split("\\(")[0];

                // Regex for splitting the first line into title and author
                List<String> matchList = new ArrayList<String>();
                Pattern regex = Pattern.compile("\\((.*?)\\)");
                Matcher regexMatcher = regex.matcher(line);

                while (regexMatcher.find()) { //Finds Matching Pattern in String
                    matchList.add(regexMatcher.group(1)); //Fetching Group from String
                }

                if ( 0 < matchList.size() ) {
                    array[i].author = matchList.get(matchList.size() - 1); // Get the last item in the array list.
                } else {
                    array[i].author = "";
                }

                line = inFile.nextLine();

                array[i].pageNum = line.split("\\|").length > 0 ? line.split("\\|")[0] : "" ;
                array[i].location = line.split("\\|").length > 1 ? line.split("\\|")[1] : "" ;
                array[i].date = line.split("\\|").length > 2 ? line.split("\\|")[2] : "" ;
                inFile.nextLine(); // Skip blank Line
                array[i].text = inFile.nextLine();
                inFile.nextLine(); // Skip dashed Line

                // Clean up the highlight record
                array[i].title = array[i].title.replaceAll("[^\\x00-\\x7F]", "");
                array[i].author = array[i].author.replaceAll("[^\\x00-\\x7F]", "");
                array[i].pageNum = array[i].pageNum.replaceAll("[^\\x00-\\x7F]", "");
                array[i].location = array[i].location.replaceAll("[^\\x00-\\x7F]", "");
                array[i].date = array[i].date.replaceAll("[^\\x00-\\x7F]", "");
                array[i].text = array[i].text.replaceAll("[^\\x00-\\x7F]", "");

                i++;
            }            
        }
 
        inFile.close();

        return array;
    }

    public static boolean writeToFile( String fileName, Highlight[] data ) throws Exception {
        PrintWriter outFile = new PrintWriter(fileName);


        for ( int i = 0; i < data.length; i++ ) {
            outFile.println(data[i].title + "" + data[i].author + "");
            outFile.println(data[i].pageNum + "|" + data[i].location + "" + data[i].date );
            outFile.println(""); // Line Break
            outFile.println(data[i].text);
            outFile.println("==========");
        }

        outFile.close();

        return true;
    }

    public static Highlight pickHighlight( Highlight[] all, Highlight[] sent ) {
        int randomNum = (int)(Math.random()*all.length);
        Highlight newRecord = sent[randomNum];
        int loops = 0;

        // the new record is not already sent, and new record length > 3, and the we've been looking for < 1000000 loop itterations 
        // this needs to be refactored
        while ( !newRecord.text.equals("") && all[randomNum].text.split(" ").length > 3 && loops < 1000000 ) { 
            randomNum = (int)(Math.random() * all.length);
            newRecord = sent[randomNum];
            loops++;
        }

        newRecord = all[randomNum];
        sent[randomNum] = newRecord;

        return newRecord;
    }

    public static void renderEmail( Highlight[] highlights, String fileOutName ) throws Exception {
        PrintWriter outFile = new PrintWriter(fileOutName);

        for (int i = 0; i < highlights.length; i++) {

            String title = highlights[i].title == "" ? "[Title Not Found]" : highlights[i].title;
            String author = highlights[i].author == "" ? "[Author Not Found]" : highlights[i].author;
            String text = highlights[i].text == "" ? "[Text Not Found]" : highlights[i].text;

            if ( (title.equals( "A Guide to the Good Life  ") ) && ("[Author Not Found]" == author) ) {
                author = "William B. Irvine";
            }

            outFile.println("<table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background: #fff; border-radius: 3px; width: 100%;\" width=\"100%\">");
            outFile.println( "<tr>" );
            outFile.println( "<td class=\"wrapper\" style=\"font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\" >" );
            outFile.println( "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\" width=\"100%\">" );
            outFile.println( "<tr>" );
            outFile.println( "<td style=\"font-size: 14px; vertical-align: top;\">" );
            outFile.println( "<span style=\"font-weight: bold; font-size: 18px;\">" + title + "</span>");
            outFile.println( "<br>");
            if ( !author.equals("[Text Not Found]") ) {
                outFile.println( "<span style=\"color:#9f8e7d; font-size:85%; vertical-align: bottom;\">by: " + author + "</span>" );
            }
            outFile.println( "<hr>" );
            outFile.println( "<span style=\"margin-left: 0px;\">" + text + "</span>" );
            outFile.println( "</td>" );
            outFile.println( "</tr>" );
            outFile.println( "</table>" );
            outFile.println( "</td>" );
            outFile.println( "</tr>" );
            outFile.println("</table>");
            outFile.println("<div style=\"width:100%;\">&nbsp;</div>");
        }
        outFile.close();
    }
}
