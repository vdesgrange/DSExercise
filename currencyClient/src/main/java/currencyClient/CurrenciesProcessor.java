/**
 * @author Viviane Desgrange
 */
package client;

import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * CurrenciesProcessor
 * Class discribing currencies object.
 * List all currencies available.
 * Handle currencies data structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrenciesProcessor {
    protected static final HashMap<String, String> currencies = new HashMap<String, String>();
    private List<Map<String, List<FromToRate> > > data = new ArrayList<>();

    /**
     * CurrenciesProcessor
     * @constructor
     * Initialise list of currencies availables.
     */
    public CurrenciesProcessor() {
        this.currencies.put("USD", "United States Dollars");
        this.currencies.put("EUR", "Euro");
        this.currencies.put("CHF", "Swiss Franc");
        this.currencies.put("GBP", "British Pound");
        this.currencies.put("OMR", "Omani Rial");
        this.currencies.put("BHD", "Bahraini Dinar");
        this.currencies.put("KWD", "Kuwaiti Dinar");
        this.currencies.put("SGD", "Singapore Dollar");
    }

    /**
     * getData
     * Getter
     */
    public List getData() {
        return this.data;
    }

    /**
     * setData
     * @param rawData - JSon response
     * Read and store JSon into local data structure.
     */
    public void setData(String rawData) {
        Gson gson = new Gson();
        List<Map<String, List<FromToRate> > > newData = new ArrayList<>();
        Type type = new TypeToken< List< Map< String, List<FromToRate> > > >(){}.getType();
        newData = gson.fromJson(rawData, type);
        this.data = newData;
    }

    /**
     * toString
     * Return String representation of currencies object.
     * Rate between currencies by dates.
     */
    @Override
    public String toString() {
        String currenciesStr = "";

        for (Map<String, List<FromToRate> >  map : this.data) {
            for (Map.Entry<String, List<FromToRate> > item : map.entrySet()) {

                currenciesStr = currenciesStr.concat(String.format("Exchange rates on %s :\n", item.getKey()));

                for (FromToRate value : item.getValue()) {
                    currenciesStr = currenciesStr.concat(value.toString() + '\n');
                }

                currenciesStr = currenciesStr.concat("\n");
            }
        }

        return currenciesStr;
    }
}
