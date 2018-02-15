package currencyAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationTest {

    @Autowired
	private TestRestTemplate restTemplate;

    @Test
    public void testGetAllCurrencies() {
        UriComponentsBuilder builder;
        String response;
        String validResponse;

        builder = UriComponentsBuilder // create Uri
            .fromUriString("/currencies")
            .queryParam("date", "2017-01-01");
        validResponse = "[{" +
            "\"2017-01-01\":[" +
                "{\"from\":\"CHF\",\"to\":\"USD\",\"rate\":\"1.04\"}," +
                "{\"from\":\"SGD\",\"to\":\"USD\",\"rate\":\"0.74\"}," +
                "{\"from\":\"EUR\",\"to\":\"USD\",\"rate\":\"1.19\"}," +
                "{\"from\":\"GBP\",\"to\":\"USD\",\"rate\":\"1.3\"}," +
                "{\"from\":\"OMR\",\"to\":\"USD\",\"rate\":\"2.6\"}," +
                "{\"from\":\"USD\",\"to\":\"USD\",\"rate\":\"1.0\"}," +
                "{\"from\":\"KWD\",\"to\":\"USD\",\"rate\":\"3.32\"}," +
                "{\"from\":\"BHD\",\"to\":\"USD\",\"rate\":\"2.65\"}" +
            "]" +
        "}]";
        response = this.restTemplate.getForObject(builder.toUriString(), String.class);

        assertEquals(validResponse, response);
    }

    @Test
    public void testGetCurrenciesExchangeRate() {
        UriComponentsBuilder builder;
        String response;
        String validResponse;

        builder = UriComponentsBuilder // create Uri
            .fromUriString("/currencies")
            .queryParam("date", "2017-01-01")
            .queryParam("currencyA", "EUR")
            .queryParam("currencyB", "USD");
        validResponse = "[{" +
            "\"2017-01-01\":[" +
                "{\"from\":\"EUR\",\"to\":\"USD\",\"rate\":\"1.19\"}," +
                "{\"from\":\"USD\",\"to\":\"EUR\",\"rate\":\"0.8403361\"}" +
            "]" +
        "}]";
        response = this.restTemplate.getForObject(builder.toUriString(), String.class);

        builder = UriComponentsBuilder // create Uri
            .fromUriString("/currencies")
            .queryParam("date", "2017-01-01")
            .queryParam("currencyA", "KWD")
            .queryParam("currencyB", "OMR");
        validResponse = "[{" +
            "\"2017-01-01\":[" +
                "{\"from\":\"KWD\",\"to\":\"OMR\",\"rate\":\"1.2769231\"}," +
                "{\"from\":\"OMR\",\"to\":\"KWD\",\"rate\":\"0.78313255\"}" +
            "]" +
        "}]";
        response = this.restTemplate.getForObject(builder.toUriString(), String.class);

        assertEquals(validResponse, response);
    }

    @Test
    public void testGetCurrenciesExchangeRateRange() {
        UriComponentsBuilder builder;
        String response;
        String validResponse;

        builder = UriComponentsBuilder // create Uri
            .fromUriString("/currencies")
            .queryParam("startingDate", "2017-01-01")
            .queryParam("endingDate", "2017-01-02")
            .queryParam("currency", "SGD");
        validResponse = "[" +
            "{\"2017-01-01\":[" +
            "{\"from\":\"SGD\",\"to\":\"USD\",\"rate\":\"0.74\"}," +
            "{\"from\":\"USD\",\"to\":\"SGD\",\"rate\":\"1.3513514\"}" +
            "]}," +
            " {\"2017-01-02\":[" +
            "{\"from\":\"SGD\",\"to\":\"USD\",\"rate\":\"0.23999998\"}," +
            "{\"from\":\"USD\",\"to\":\"SGD\",\"rate\":\"4.166667\"}" +
            "]}" +
        "]";
        response = this.restTemplate.getForObject(builder.toUriString(), String.class);

        assertEquals(validResponse, response);
    }
}
