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

}
