/*
 * This class has been written by Faisal AlBader - ID:443102460 Class:37637
 * 
 * This class was assigned to: Mohammed
 */

  
public class StockHistoryDataSetImp implements StockHistoryDataSet 
{  
          
  Map <String, StockHistory> Companies;  
    

  public StockHistoryDataSetImp()  
  {  
    Companies = new BST<String, StockHistory>();  
  }  
    
  // Returns the number of companies for which data is stored. -- DONE 
  public int size()  
  {  
    return Companies.size();  
  }  

  // Returns true if there are no records, false otherwise. -- DONE 
  public boolean empty()  
  {  
    return Companies.empty();  
  }  

  // Clears all data from the storage.  
  public void clear()  
  {   
    Companies.clear();  
  }  

  // Returns the map of stock histories, where the key is the company code. -- DONE 
  public Map<String, StockHistory> getStockHistoryMap()  
  {  
    return Companies;  
  }  

  // Returns the list of all company codes stored in the dataset sorted in increasing order. -- DONE 
  public DLLComp<String> getAllCompanyCodes()  
  {  
    return Companies.getKeys();  
  }  

  // Retrieves the stock history for a specific company code, null if no data is found. -- DONE 
  public StockHistory getStockHistory(String companyCode)  
  {  
    if (Companies.find(companyCode))  
      return Companies.retrieve();  
    return null;  
  }  

  // Adds the stock history of a specific company. -- DONE 
  // This method returns true if the operation is successful, false otherwise (company code already exists). -- DONE 
  public boolean addStockHistory(StockHistory stockHistory)  
  {  
    if (!Companies.find(stockHistory.getCompanyCode()))  
    {  
      Companies.insert(stockHistory.getCompanyCode(), stockHistory);  
      return true;  
    }  
    return false;  
  }  

  // Removes the stock history of a specific company from the storage. -- DONE 
  // This method returns true if the operation is successful and false if the company code does not exist. -- DONE 
  public boolean removeStockHistory(String companyCode)  
  {  
    if (Companies.find(companyCode))  
    {  
      Companies.remove(companyCode);  
      return true;  
    }
     
    return false;  
  }  
          
}  