package currencyAPI;

import currencyBusiness.CurrenciesProcessing;
import java.lang.String;
import java.util.ArrayList;

public class CurrenciesAPI {
    public CurrenciesAPI() {
    }

    public static String getAllCurrencies(String date) {
        return CurrenciesProcessing.getAllCurrenciesExchangeRate(date);
    }

    public static String getCurrenciesExchangeRate(String date, String currencyX, String currencyY) {
        return CurrenciesProcessing.getCurrenciesExchangeRate(date, currencyX, currencyY);
    }

    public static ArrayList getCurrenciesExchangeRateRange(String dateX, String dateY, String currency) {
        return CurrenciesProcessing.getCurrenciesExchangeRateRange(dateX, dateY, currency);
    }
}
