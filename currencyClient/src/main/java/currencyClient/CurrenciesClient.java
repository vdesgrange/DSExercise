package client;

import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrenciesClient {
    private HashMap<String, Float> currenciesList = new HashMap<String, Float>();

    public CurrenciesClient() {
    }

    public HashMap<String, Float> getCurrencies() {
        return this.currenciesList;
    }

    public void setCurrencies(HashMap<String, Float> currenciesList ) {
        this.currenciesList = currenciesList;
    }

    @Override
    public String toString() {
        return "";
    }
}
