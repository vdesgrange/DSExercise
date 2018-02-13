package currencyBusiness;

import java.lang.String;
import java.util.HashMap;
import javafx.util.Pair;
import currencyBusiness.DataProcessing;

public class CurrenciesProcessing {

    CurrenciesProcessing() {
    }

    public static HashMap getAllCurrenciesExchangeRate(String date) {
        return DataProcessing.getCurrenciesExchangeRateByDateInCurrency(date, "USD");
    }

    public static HashMap getCurrenciesExchangeRate(String date, String currencyX, String currencyY) {
        return DataProcessing.getCurrenciesExchangeRateByDateBetweenCurrencies(date, currencyX, currencyY);
    }

    public static HashMap getCurrenciesExchangeRateRange(String dateX, String dateY, String currency) {
        return DataProcessing.getCurrenciesExchangeRateByDateInRange(dateX, dateY, currency);
    }
}
