package currencyAPI;

import currencyBusiness.CurrenciesProcessing;
import java.lang.String;
import java.util.ArrayList;

public class CurrenciesAPI {
    CurrenciesProcessing currenciesProcessing = new CurrenciesProcessing();
    public CurrenciesAPI() {
    }

    public String getAllCurrencies(String date) {
        return currenciesProcessing.getAllCurrenciesExchangeRate(date);
    }

    public String getCurrenciesExchangeRate(String date, String currencyX, String currencyY) {
        return currenciesProcessing.getCurrenciesExchangeRate(date, currencyX, currencyY);
    }

    public String getCurrenciesExchangeRateRange(String dateX, String dateY, String currency) {
        return currenciesProcessing.getCurrenciesExchangeRateRange(dateX, dateY, currency);
    }
}
