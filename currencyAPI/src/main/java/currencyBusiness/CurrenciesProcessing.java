/**
 * @author Viviane Desgrange
 */

package currencyBusiness;

import java.lang.String;
import java.util.ArrayList;
import currencyBusiness.DataProcessing;

/**
 * CurrenciesProcessing
 * Send currencies exchange rates data based on query parameters.
 */
public class CurrenciesProcessing {

    CurrenciesProcessing() {
    }

    /**
     * getAllCurrenciesRate
     * Return list of exchanges rates between a specified currency and USD, on specified date.
     */
    public static String getAllCurrenciesExchangeRate(String date) {
        return DataProcessing.getCurrenciesExchangeRateByDateInCurrency(date, "USD");
    }

    /**
     * getCurrenciesExchangeRate
     * Return exchanges rates between two currencies X and Y on a specified date.
     */
    public static String getCurrenciesExchangeRate(String date, String currencyX, String currencyY) {
        return DataProcessing.getCurrenciesExchangeRateByDateBetweenCurrencies(date, currencyX, currencyY);
    }

    /**
     * getCurrenciesExchangeRateRange
     * Return on a date range list of exchange rate between specified currency and USD.
     */
    public static ArrayList getCurrenciesExchangeRateRange(String dateX, String dateY, String currency) {
        return DataProcessing.getCurrenciesExchangeRateByDateInRange(dateX, dateY, currency);
    }
}
