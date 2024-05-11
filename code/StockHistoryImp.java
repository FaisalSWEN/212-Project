import java.util.Date;

public class StockHistoryImp implements StockHistory
{  
  
    TimeSeries<StockData> timeSeries;  
    String company_Code;   
        
    public StockHistoryImp()  
    {  
    timeSeries = new TimeSeriesImp<StockData> ();  
    }  

    @Override 
    public int size()  
    {  
        return timeSeries.size();  
    }  

    @Override   
    public boolean empty()  
    {  
        return timeSeries.empty();  
    }  

    @Override   
    public void clear()   
    {  
        DLL<DataPoint<StockData>> d1 = timeSeries.getAllDataPoints();
        if(d1.empty()) return;

        d1.findFirst();
        while(!d1.last()) 
        {
            timeSeries.removeDataPoint(d1.retrieve().date);
            d1.findNext();
        }

        timeSeries.removeDataPoint(d1.retrieve().date);
    }  


    @Override
    public String getCompanyCode() 
    {  
        return company_Code;  
    }  

    @Override  
    public void SetCompanyCode(String companyCode)  
    {  
        this.company_Code = companyCode;  
    }  

    @Override
    public TimeSeries<StockData> getTimeSeries()  
    {  
        return timeSeries;  
    }  

    @Override   
    public StockData getStockData(Date date)  
    {  
        return timeSeries.getDataPoint(date);  
    }  

    @Override 
    public boolean addStockData(Date date, StockData stockData)  
    {  
        DataPoint<StockData> dataPoint = new DataPoint<StockData>(date, stockData);  
        return timeSeries.addDataPoint(dataPoint);  
    }  

    @Override   
    public boolean removeStockData(Date date)  
    {  
        return timeSeries.removeDataPoint(date);  
    }
}  
