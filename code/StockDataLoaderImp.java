/*
 * This class was written by Faisal AlBader - ID:443102460 Class:37637
 * 
 * This class is assigned to: Faisal AlBader
 */

 // For file input handilng
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// For file date input handilng
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class StockDataLoaderImp implements StockDataLoader
{
  // For Date object
   private Date parseDate(String date)
   {
     try 
     {
         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
     } 

     catch (ParseException e) 
     {
         return null;
     }
  }


  
  // 1) Loads and adds stock history from the specified CSV file -- DONE
  // 2) The code of the company is the basename of the file -- DONE
  // 3) This method returns null if the operation is not successful due to errors like -- DONE
    /*
      non-existing file, -- DONE
      incorrect format, -- DONE
      repeated dates, -- DONE
      dates not sorted in increasing order, -- DONE
      etc.
    */
	public StockHistory loadStockDataFile(String fileName)
  {
    StockHistory companyStockHistory = new StockHistoryImp(fileName);

    try 
    {
      if (fileName.substring(fileName.indexOf('.')) != ".csv")
        throw new Exception("wrong extension file, expected '.csv' but the provided was "+ fileName.substring(fileName.indexOf('.')));

      companyStockHistory.SetCompanyCode(fileName.substring(0, fileName.indexOf('.')));
      Scanner reader = new Scanner(new File(fileName));

      if (reader.hasNextLine())
        reader.nextLine();
      // @else if empty ?
      
      while (reader.hasNextLine()) 
      {
        String[] dataArr = (reader.nextLine()).split("[,]");
        
        Date date =  parseDate(dataArr[0]); // @if date == null ? return null;
        double open = Double.parseDouble(dataArr[1]);
        double close = Double.parseDouble(dataArr[2]);
        double high = Double.parseDouble(dataArr[3]);
        double low = Double.parseDouble(dataArr[4]);
        long volume = Long.parseLong(dataArr[5]);
        
        if (!companyStockHistory.timeSeries.empty())
          if (date.compareTo(companyStockHistory.timeSeries.getMaxDate()) <= 0)
            throw new Exception("stock data date error, the dates either not sorted in increasing order or repeated");

        if (!companyStockHistory.addStockData(date, new StockData(open, close, high, low, volume)))
          throw new Exception("failed to add stock data to stock history");
      }

      reader.close();
      return companyStockHistory;
    } 
    
    catch (FileNotFoundException e)
    {
      System.out.println("An error occurred: " + e.getMessage());

      return null;
    }

    catch (Exception e) 
    {
      System.out.println("An error occurred: " + e.getMessage());

      return null;
    }
  }



  // 1) Loads and returns stock history data from all CSV files in the specified directory -- DONE
  // 2) This method returns null if the operation is not successful (see possible errors in the method loadStockDataFile) -- DONE
	public StockHistoryDataSet loadStockDataDir(String directoryPath)
  {
    StockHistoryDataSet companiesStockHistory = new StockHistoryDataSetImp();

    try
    {
      final File directory = new File(directoryPath);

      if (!directory.isDirectory())
        throw new Exception("wrong type input, expected a directory but the provided was a file");

      for (File fileEntry : directory.listFiles())
      {
        if (fileEntry.isFile() && fileEntry.getName().substring(fileEntry.getName().indexOf('.')) != ".csv")
          if(!companiesStockHistory.addStockHistory(loadStockDataFile(fileEntry.getName())))
            throw new Exception("duplicated files exist, expected a uniqe file names but duplicated files found");
      }
    }

    catch (FileNotFoundException e)
    {
      System.out.println("An error occurred: " + e.getMessage());

      return null;
    }

    catch(Exception e)
    {
      System.out.println("An error occurred: " + e.getMessage());

      return null;
    }
  }
}