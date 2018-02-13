package currencyAPI;

import currencyBusiness.CurrenciesProcessing;
import java.lang.String;
import java.util.HashMap;
import java.util.Vector;

public class CurrenciesAPI {
    public CurrenciesAPI() {
    }

    public static HashMap getAllCurrencies(String date) {
        return CurrenciesProcessing.getAllCurrenciesExchangeRate(date);
    }

    public static HashMap getCurrenciesExchangeRate(String date, String currencyX, String currencyY) {
        return CurrenciesProcessing.getCurrenciesExchangeRate(date, currencyX, currencyY);
    }

    public static Vector getCurrenciesExchangeRateRange(String dateX, String dateY, String currency) {
        return CurrenciesProcessing.getCurrenciesExchangeRateRange(dateX, dateY, currency);
    }
}
