package currencyBusiness;

import java.lang.String;
import java.util.Vector;
import java.util.HashMap;
import javafx.util.Pair;

public class Currencies {

    protected static final HashMap<String, String> currencies = new HashMap<String, String>();
    private HashMap<Pair<String, String>, Float> exchangeRates = new HashMap<Pair<String, String>, Float>();

    Currencies() {
        this.currencies.put("BHD", "Bahraini Dinar");
        this.currencies.put("CHF", "Swiss Franc");
        this.currencies.put("EUR", "Euro");
        this.currencies.put("GBP", "British Pound");
        this.currencies.put("KWD", "Kuwaiti Dinar");
        this.currencies.put("OMR", "Omani Rial");
        this.currencies.put("SGD", "Singapore Dollar");
        this.currencies.put("USD", "United States Dollars");
    }

    public void setExchangeRates(String currencyX, String currencyY, Float rate) {
        Pair<String, String> key = new Pair<String, String>(currencyX, currencyY);
        this.exchangeRates.replace(key, rate);
    }

    public Float getExchangeRates(String currencyX, String currencyY, Float rate) {
        Pair<String, String> key = new Pair<String, String>(currencyX, currencyY);
        return this.exchangeRates.get(key);
    }
}
