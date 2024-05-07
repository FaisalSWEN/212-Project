public class StockHistoryDataSetImp implements StockHistoryDataSet {
    private Map<String, StockHistory> stockHistoryMap;

    public StockHistoryDataSetImp() {
        stockHistoryMap = new BST<>();
    }

    @Override
    public int size() {
        return stockHistoryMap.size();
    }

    @Override
    public boolean empty() {
        return stockHistoryMap.empty();
    }

    @Override
    public void clear() {
        stockHistoryMap.clear();
    }

    @Override
    public Map<String, StockHistory> getStockHistoryMap() {
        return stockHistoryMap;
    }

    @Override
    public DLLComp<String> getAllCompanyCodes() {
        DLLComp<String> companyCodes = new DLLComp<>();
        for (String code : stockHistoryMap.getKeys()) {
            companyCodes.add(code);
        }
        return companyCodes;
    }

    @Override
    public StockHistory getStockHistory(String companyCode) {
        return stockHistoryMap.find(companyCode) ? stockHistoryMap.retrieve() : null;
    }

    @Override
    public boolean addStockHistory(StockHistory stockHistory) {
        String companyCode = stockHistory.getCompanyCode();
        return stockHistoryMap.insert(companyCode, stockHistory);
    }

    @Override
    public boolean removeStockHistory(String companyCode) {
        return stockHistoryMap.remove(companyCode);
    }
}
