package currencyBusiness;

import java.util.Vector;
import javafx.util.Pair;
import java.lang.ArithmeticException;

import currencyBusiness.DataProcessing;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class DataProcessingTest {

    private DataProcessing dataProcessing = new DataProcessing();

    @Test
    public void computeRatioTest() {
        Pair<Float, Float> expected = new Pair<Float, Float>(new Float(0.8), new Float(1.25));
        Pair<Float, Float> actual = dataProcessing.computeRatio(new Float(5), new Float(4));
        assertEquals(expected, actual);
    }

    @Test
    public void computeRatioZeroTest() {
        Pair<Float, Float> expected = new Pair<Float, Float>(new Float(0), new Float(0));
        Pair<Float, Float> actual = dataProcessing.computeRatio(new Float(0), new Float(1));
        assertEquals(expected, actual);
    }

    @Test
    public void getExchangeRateTest() {
        Vector < Pair <Pair<String,String>, Float> > expected = new Vector < Pair <Pair<String,String>, Float> >();
        Vector < Pair <Pair<String,String>, Float> > actual = new Vector < Pair <Pair<String,String>, Float> >();
        Vector<Pair<String, String>> couple = new Vector<Pair<String, String>>();

        expected.add(new Pair<Pair<String, String>, Float>(new Pair<String,String>("SGD","USD"), new Float(0.74)));
        expected.add(new Pair<Pair<String, String>, Float>(new Pair<String,String>("USD","SGD"), new Float(1.3513514)));

        couple.add(new Pair<String, String>("SGD", "1"));
        couple.add(new Pair<String, String>("USD", "0.74"));

        dataProcessing.computeExchangeRate(couple, actual);

        assertEquals(expected, actual);
    }

    @Test
    public void getCurrenciesExchangeRateByDateInCurrencyCache() {
        String date = "2017-01-01";
        String currency = "USD";
        String expected = "";
        String actual = "";
        actual = dataProcessing.getCurrenciesExchangeRateByDateInCurrency(date, currency);
        expected = "[{" +
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

        assertEquals(expected, actual);
    }

    @Test
    public void getCurrenciesExchangeRateByDateBetweenCurrencies() {
        String date = "2017-01-01";
        String currencyX = "CHF";
        String currencyY = "GBP";
        String expected = "";
        String actual = "";
        actual = dataProcessing.getCurrenciesExchangeRateByDateBetweenCurrencies(date, currencyX, currencyY);
        expected = "[{" +
            "\"2017-01-01\":[" +
                "{\"from\":\"CHF\",\"to\":\"GBP\",\"rate\":\"0.8\"}," +
                "{\"from\":\"GBP\",\"to\":\"CHF\",\"rate\":\"1.25\"}" +
            "]" +
        "}]";

        assertEquals(expected, actual);
    }

    @Test
    public void getCurrenciesExchangeRateByDateInRange() {
        String dateX = "2017-01-01";
        String dateY = "2017-01-02";
        String currency = "CHF";
        String expected = "";
        String actual = "";
        actual = dataProcessing.getCurrenciesExchangeRateByDateInRange(dateX, dateY, currency);
        expected = "[" +
            "{\"2017-01-01\":[" +
            "{\"from\":\"CHF\",\"to\":\"USD\",\"rate\":\"1.04\"}," +
            "{\"from\":\"USD\",\"to\":\"CHF\",\"rate\":\"0.9615385\"}" +
            "]}," +
            " {\"2017-01-02\":[" +
            "{\"from\":\"CHF\",\"to\":\"USD\",\"rate\":\"1.24\"}," +
            "{\"from\":\"USD\",\"to\":\"CHF\",\"rate\":\"0.8064516\"}" +
            "]}" +
        "]";

        assertEquals(expected, actual);
    }
}
