package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Vector;
import javafx.util.Pair;
import client.APIErrorHandler;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void getAllCurrencies(String date) {
        String transactionUrl = "http://localhost:8080/currencies";
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(transactionUrl)
            .queryParam("date",date);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new APIErrorHandler());
            HashMap<String, String> currencies = new HashMap<String, String>();
            currencies = restTemplate.getForObject(builder.toUriString(), HashMap.class);
            log.info(currencies.toString());
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
    }

    public static void getCurrenciesExchangeRate(String date, String currencyA, String currencyB) {
        String transactionUrl = "http://localhost:8080/currencies";
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(transactionUrl)
            .queryParam("date", date)
            .queryParam("currencyA", currencyA)
            .queryParam("currencyB", currencyB);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new APIErrorHandler());
            HashMap<String, String> currencies = new HashMap<String, String>();
            currencies = restTemplate.getForObject(builder.toUriString(), HashMap.class);
            log.info(currencies.toString());
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
    }

    public static void getCurrenciesExchangeRateRange(String startingDate, String endingDate, String currency) {
        String transactionUrl = "http://localhost:8080/currencies";
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(transactionUrl)
            .queryParam("startingDate", startingDate)
            .queryParam("endingDate", endingDate)
            .queryParam("currency", currency);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new APIErrorHandler());
            HashMap<String, String> currencies = new HashMap<String, String>();
            currencies = restTemplate.getForObject(builder.toUriString(), HashMap.class);
            log.info(currencies.toString());
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
    }

}
