/**
 * @author Viviane Desgrange
 */
package client;

import java.lang.String;
import java.util.HashMap;
import javafx.util.Pair;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Currencies
 * Class discribing currencies object.
 * List all currencies available.
 * Handle currencies data structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currencies {
    protected static final HashMap<String, String> currencies = new HashMap<String, String>();
    private HashMap <String, HashMap <Pair <String, String>, Float> > data = new HashMap();

    public Currencies() {
        this.currencies.put("USD", "United States Dollars");
        this.currencies.put("EUR", "Euro");
        this.currencies.put("CHF", "Swiss Franc");
        this.currencies.put("GBP", "British Pound");
        this.currencies.put("OMR", "Omani Rial");
        this.currencies.put("BHD", "Bahraini Dinar");
        this.currencies.put("KWD", "Kuwaiti Dinar");
        this.currencies.put("SGD", "Singapore Dollar");
    }

    public HashMap getData() {
        return this.data;
    }

    public void setData(HashMap <String, HashMap <Pair <String, String>, Float> > data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String currenciesStr = "";

        for (HashMap.Entry<String, HashMap <Pair <String, String>, Float> > item : this.data.entrySet()) {

            currenciesStr.concat(String.format("Exchange rates on %s :\n", item.getKey()));

            for (HashMap.Entry<Pair <String, String>, Float> value : item.getValue().entrySet()) {
                String fromCurrency = value.getKey().getKey();
                String toCurrency = value.getKey().getValue();
                currenciesStr.concat(String.format("Rate from %s to %s is %f\n", fromCurrency, value.getValue()));
            }

            currenciesStr.concat("\n");
        }

        return currenciesStr;
    }
}
