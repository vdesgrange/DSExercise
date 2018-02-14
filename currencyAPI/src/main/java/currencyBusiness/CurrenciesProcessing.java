/**
 * @author Viviane Desgrange
 */

package currencyBusiness;

import java.lang.String;
import java.util.ArrayList;
import currencyBusiness.DataProcessing;
import org.springframework.cache.annotation.*;

/**
 * CurrenciesProcessing
 * Send currencies exchange rates data based on query parameters.
 */
public class CurrenciesProcessing {

    DataProcessing dataProcessing = new DataProcessing();

    public CurrenciesProcessing() {
    }

    /**
     * getAllCurrenciesRate
     * Return list of exchanges rates between a specified currency and USD, on specified date.
     */
    @Cacheable(value="getAllCurrenciesExchangeRateCache")
    public String getAllCurrenciesExchangeRate(String date) {
        return dataProcessing.getCurrenciesExchangeRateByDateInCurrency(date, "USD");
    }

    /**
     * getCurrenciesExchangeRate
     * Return exchanges rates between two currencies X and Y on a specified date.
     */
    @Cacheable(value="getCurrenciesExchangeRateCache")
    public String getCurrenciesExchangeRate(String date, String currencyX, String currencyY) {
        return dataProcessing.getCurrenciesExchangeRateByDateBetweenCurrencies(date, currencyX, currencyY);
    }

    /**
     * getCurrenciesExchangeRateRange
     * Return on a date range list of exchange rate between specified currency and USD.
     */
    @Cacheable(value="getCurrenciesExchangeRateRangeCache")
    public String getCurrenciesExchangeRateRange(String dateX, String dateY, String currency) {
        return dataProcessing.getCurrenciesExchangeRateByDateInRange(dateX, dateY, currency);
    }
}
