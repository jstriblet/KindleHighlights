import java.io.File; 
import java.util.Scanner; 

public class KindleHighlights {
    public static void main( String[] args) throws Exception 
    {
        File file = new File("/Users/striblet/Desktop/MyClippings.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) 
            System.out.println(sc.nextLine()); 
    }
}  