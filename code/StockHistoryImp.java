import java.util.Date;

public class StockHistoryImp implements StockHistory {
    private String companyCode;
    private TimeSeries<StockData> stockTimeSeries;

    public StockHistoryImp(String companyCode) {
        this.companyCode = companyCode;
        this.stockTimeSeries = new TimeSeries<>();
    }

    @Override
    public int size() {
        return stockTimeSeries.size();
    }

    @Override
    public boolean empty() {
        return stockTimeSeries.isEmpty();
    }

    @Override
    public void clear() {
        stockTimeSeries.clear();
    }

    @Override
    public String getCompanyCode() {
        return companyCode;
    }

    @Override
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public TimeSeries<StockData> getTimeSeries() {
        return stockTimeSeries;
    }

    @Override
    public StockData getStockData(Date date) {
        return stockTimeSeries.get(date);
    }

    @Override
    public boolean addStockData(Date date, StockData stockData) {
        if (stockTimeSeries.containsKey(date)) {
            return false; // Entry already exists for this date
        }
        stockTimeSeries.put(date, stockData);
        return true;
    }

    @Override
    public boolean removeStockData(Date date) {
        return stockTimeSeries.remove(date) != null;
    }
}
