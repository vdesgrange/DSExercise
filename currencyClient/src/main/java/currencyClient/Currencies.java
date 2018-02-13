package client;

import java.lang.String;
import java.util.HashMap;

public class Currencies {

    protected static final HashMap<String, String> currencies = new HashMap<String, String>();

    Currencies() {
        this.currencies.put("USD", "United States Dollars");
        this.currencies.put("EUR", "Euro");
        this.currencies.put("CHF", "Swiss Franc");
        this.currencies.put( "GBP", "British Pound");
        this.currencies.put("OMR", "Omani Rial");
        this.currencies.put("BHD", "Bahraini Dinar");
        this.currencies.put("KWH", "Kuwaiti Dinar");
        this.currencies.put("SGD", "Singapore Dollar");
    }
}
