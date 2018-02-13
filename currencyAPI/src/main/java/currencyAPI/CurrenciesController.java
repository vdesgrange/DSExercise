package currencyAPI;

import java.lang.String;
import java.util.Vector;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrenciesController {

    CurrenciesAPI currenciesAPI = new CurrenciesAPI();

    @RequestMapping(
        value = "/currencies",
        params = {"date"},
        method = RequestMethod.GET)
    public HashMap getAllCurrencies(@RequestParam(value="date", defaultValue="2017-01-01") String date) {
        return currenciesAPI.getAllCurrencies(date);
    }

    @RequestMapping(
        value = "/currencies",
        params = {"date","currencyA","currencyB"},
        method = RequestMethod.GET)
    public HashMap getCurrenciesExchangeRate(
            @RequestParam(value="date", defaultValue="2017-01-01") String date,
            @RequestParam(value="currencyA", defaultValue="EUR") String currencyA,
            @RequestParam(value="currencyB", defaultValue="EUR") String currencyB) {
        return currenciesAPI.getCurrenciesExchangeRate(date, currencyA, currencyB);
    }

    @RequestMapping(
        value = "/currencies",
        params = {"startingDate","endingDate","currency"},
        method = RequestMethod.GET)
    public Vector getCurrenciesExchangeRateRange(
            @RequestParam(value="startingDate", defaultValue="2017-01-01") String startingDate,
            @RequestParam(value="endingDate", defaultValue="2017-01-01") String endingDate,
            @RequestParam(value="currency", defaultValue="EUR") String currency) {
        return currenciesAPI.getCurrenciesExchangeRateRange(startingDate, endingDate,currency);
    }

}
