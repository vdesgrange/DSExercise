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
 * Currencies
 * Class discribing currencies object.
 * List all currencies available.
 * Handle currencies data structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currencies {
    protected static final HashMap<String, String> currencies = new HashMap<String, String>();
    private HashMap<String, ArrayList<FromToRate> > data = new HashMap<String, ArrayList<FromToRate> >();

    public class FromToRate {
        private String from;
        private String to;
        private String rate;

        public String getFrom() {
            return this.from;
        }

        public String getTo() {
            return this.to;
        }

        public String getRate() {
            return this.rate;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        @Override
        public String toString() {
            return String.format("Exchange rate from %s to %s is %f", this.from, this.to, this.rate);
        }
    }

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

    public void setData(String rawData) {
        Gson gson = new Gson();
        Type type = new TypeToken< Map< String, List<FromToRate> > >(){}.getType();
        HashMap<String, ArrayList<FromToRate> > data = new HashMap<String, ArrayList<FromToRate> >();
        this.data = gson.fromJson(rawData, type);
    }

    @Override
    public String toString() {
        String currenciesStr = "";

        for (HashMap.Entry<String, ArrayList<FromToRate> > item : this.data.entrySet()) {

            currenciesStr.concat(String.format("Exchange rates on %s :\n", item.getKey()));
            /*for (FromToRate value : item.getValue()) {
                currenciesStr.concat(value.toString());
            }*/

            currenciesStr.concat("\n");
        }

        return currenciesStr;
    }
}
