/**
 * @author Viviane Desgrange
 */

package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import javafx.util.Pair;
import client.APIErrorHandler;

/**
 * Application
 * Application public class fetch data from RESTful api by using RestTemplate.
 */
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * getAllCurrencies
     * Get exchange rates from all currencies to USD currency.
     * It call api endpoint "/currencies" with parameters "date".
     * @param date - String date (format yyyy-MM-dd) used to get date currencies
     */
    public static void getAllCurrencies(String date) {
        String transactionUrl = "http://localhost:8080/currencies"; // endpoint
        UriComponentsBuilder builder = UriComponentsBuilder // create Uri
            .fromUriString(transactionUrl)
            .queryParam("date",date);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new APIErrorHandler());
            String data = restTemplate.getForObject(builder.toUriString(), String.class); // Get raw data into object
            log.info(data); // Display raw data
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * getCurrenciesExchangeRate
     * Get exchange rates between 2 currencies on a specific date.
     * It call api endpoint "/currencies" with parameters "date", "currencyA", "currencyB".
     * @param date - String - date (format yyyy-MM-dd) used to get date currencies
     * @param currencyA - String - first currency
     * @param currencyB - String - second currency.
     */
    public static void getCurrenciesExchangeRate(String date, String currencyA, String currencyB) {
        String transactionUrl = "http://localhost:8080/currencies"; // endpoint
        UriComponentsBuilder builder = UriComponentsBuilder // create Uri
            .fromUriString(transactionUrl)
            .queryParam("date", date)
            .queryParam("currencyA", currencyA)
            .queryParam("currencyB", currencyB);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new APIErrorHandler());
            String data = restTemplate.getForObject(builder.toUriString(), String.class);
            log.info(data); // Display raw data
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * getCurrenciesExchangeRateRange
     * Get exchange rates from a currency to USD on a date range.
     * It call api endpoint "/currencies" with parameters "startingDate", "endingDate", "currency".
     * @param startingDate - String - starting date (format yyyy-MM-dd) Start of the date range.
     * @param endingDate - String - ending date (format yyyy-MM-dd) End of the date range.
     * @param currency - String - currency requested.
     */
    public static void getCurrenciesExchangeRateRange(String startingDate, String endingDate, String currency) {
        String transactionUrl = "http://localhost:8080/currencies"; // endpoint
        UriComponentsBuilder builder = UriComponentsBuilder // create Uri
            .fromUriString(transactionUrl)
            .queryParam("startingDate", startingDate)
            .queryParam("endingDate", endingDate)
            .queryParam("currency", currency);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new APIErrorHandler());
            ArrayList<String> data = new ArrayList<String>();
            data = restTemplate.getForObject(builder.toUriString(), ArrayList.class); // Get data from the Uri.
            log.info(data.toString()); // Display raw data
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
    }

}
