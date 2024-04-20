// ------------ ********************************************* ------------ //
//                                                                         //
// ------------ experimental Main class for testing some code ------------ //
//                                                                         //
// ------------ ********************************************* ------------ //

// For file input handilng
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// For file date input handilng
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class MainTester 
{
  public static void main(String args[])
  {
    //System.out.println("GOOGL.csv".substring("GOOGL.csv".indexOf('.')) != ".csv");

    try
    {
      Scanner reader = new Scanner(new File("fileName"));
    }

    catch (FileNotFoundException e)
    {
      System.out.println("An error occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
