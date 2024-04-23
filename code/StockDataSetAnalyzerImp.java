/*
 * This class was written by Faisal AlBader - ID:443102460 Class:37637
 * 
 * This class is assigned to: Faisal AlBader
 */

import java.util.Date;

public class StockDataSetAnalyzerImp implements StockDataSetAnalyzer
{
  private StockHistoryDataSet companiesStockHistory = new StockHistoryDataSetImp();



	// Returns the list of company codes sorted according to their stock performance between startDate and endDate -- DONE
  // It returns an empty list if either dates is null -- DONE
	public DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate)
  {
    DLLComp<CompPair<String, Double>> list = new DLLCompImp<CompPair<String, Double>>();

    if (startDate == null || endDate == null)
      return list;

    Map<String, StockHistory> companiesMap = companiesStockHistory.getStockHistoryMap();
    DLLComp<String> companies = companiesMap.getKeys();

    companies.findFirst();
    for(int i=0; i <companies.size(); i ++, companies.findNext())
    {
      companiesMap.find(companies.retrieve());

      StockHistory stockHistory = companiesMap.retrieve();
      double stockPerformance = (stockHistory.getStockData(endDate).close - stockHistory.getStockData(startDate).close) / stockHistory.getStockData(startDate).close;
      
      list.insert(new CompPair<String, Double>(companies.retrieve(), stockPerformance));
    }

    list.sort(false);
    return list;
  }



	// Returns the list of company codes sorted according to their total volume
	// between startDate and endDate inclusive. If startDate is null, fetches from
	// the earliest date. If endDate is null, fetches to the latest
	// date.
	public DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate)
  {
    
  }



	// Returns the list of company codes sorted by the maximum single day price
	// increase between startDate and endDate inclusive. If startDate is null,
	// fetches from the earliest date. If endDate is null, fetches to the latest
	// date.
	public DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate)
  {
    
  }


  
  // getter & setter -- DONE
	public StockHistoryDataSet getStockHistoryDataSet()
  {
    return companiesStockHistory;
  }
	public void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet)
  {
    companiesStockHistory = stockHistoryDataSet;
  }
}
