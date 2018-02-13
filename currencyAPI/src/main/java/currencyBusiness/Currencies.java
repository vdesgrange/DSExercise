package currencyBusiness;

import java.lang.String;
import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Currencies {

    private String date = "2017-01-01";
    protected static final HashMap<String, String> currencies = new HashMap<String, String>();
    private HashMap<String, Integer> currenciesMap = new HashMap<String, Integer>();
    private Float[][] rates;

    public Currencies() {
        this.currencies.put("BHD", "Bahraini Dinar");
        this.currencies.put("CHF", "Swiss Franc");
        this.currencies.put("EUR", "Euro");
        this.currencies.put("GBP", "British Pound");
        this.currencies.put("KWD", "Kuwaiti Dinar");
        this.currencies.put("OMR", "Omani Rial");
        this.currencies.put("SGD", "Singapore Dollar");
        this.currencies.put("USD", "United States Dollars");
    }

    public void setCurrenciesMap(HashMap<String, Integer> currenciesMap) {
        this.currenciesMap = currenciesMap;
    }

    public HashMap<String, Integer> setCurrenciesMap() {
        return this.currenciesMap;
    }

    public void setRates(Float[][] rates) {
        this.rates = rates;
    }

    public Float[][] setRates() {
        return this.rates;
    }

    public Float getCurrenciesRates(String currencyX, String currencyY) {
        int currencyXIndex = this.currenciesMap.get(currencyX);
        int currencyYIndex = this.currenciesMap.get(currencyY);
        return this.rates[currencyXIndex][currencyYIndex];
    }

    public void setCurrenciesRates(String currencyX, String currencyY, Float rate) {
        int currencyXIndex = this.getIndexByCurrency(currencyX);
        int currencyYIndex = this.getIndexByCurrency(currencyY);
        this.rates[currencyXIndex][currencyYIndex] = rate;
    }

    public int getIndexByCurrency(String currency) {
        return this.currenciesMap.get(currency);
    }

    public String getCurrencyByIndex(int index) {
        String currency = "";
        for (HashMap.Entry<String,Integer> curr: this.currenciesMap.entrySet()) {
            if (curr.getValue() == index) {
                currency = curr.getKey();
                break;
            }
        }
        return currency;
    }

    public String getAllExchangeRateAtDate(String date, String outCurrency) {
        JsonObject jsObject = new JsonObject();
        JsonArray jsArray   = new JsonArray();

        for (HashMap.Entry<String,Integer> curr: this.currenciesMap.entrySet()) {
            JsonObject tmpJsObject = new JsonObject();
            tmpJsObject.addProperty("from", curr.getKey());
            tmpJsObject.addProperty("to", outCurrency);
            tmpJsObject.addProperty("rate", getCurrenciesRates(curr.getKey(), outCurrency));

            jsArray.add(tmpJsObject);
        }

        jsObject.add(date, jsArray);

        return jsObject.toString();
    }

    public String getExchangeRatesAtDateBetweenCurrencies(String date, String currencyX, String currencyY) {
        JsonObject jsObject     = new JsonObject();
        JsonObject jsObjectXY   = new JsonObject();
        JsonObject jsObjectYX   = new JsonObject();
        JsonArray jsArray       = new JsonArray();

        jsObjectXY.addProperty("from", currencyX);
        jsObjectXY.addProperty("to", currencyY);
        jsObjectXY.addProperty("rate", getCurrenciesRates(currencyX, currencyY));

        jsObjectYX.addProperty("from", currencyY);
        jsObjectYX.addProperty("to", currencyX);
        jsObjectYX.addProperty("rate", getCurrenciesRates(currencyY, currencyX));

        jsArray.add(jsObjectXY);
        jsArray.add(jsObjectYX);

        jsObject.add(date, jsArray);

        return jsObject.toString();
    }
}
