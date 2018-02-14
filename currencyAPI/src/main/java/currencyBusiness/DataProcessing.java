/**
 * @author Viviane Desgrange
 */

package currencyBusiness;

import java.util.Calendar;
import java.util.Date;
import java.lang.Float;
import java.util.HashMap;
import java.util.regex.Matcher;
import javafx.util.Pair;
import java.text.ParseException;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.lang.String;
import java.util.Vector;
import java.io.*;
import java.util.ArrayList;
import java.lang.ArithmeticException;

import currencyBusiness.Currencies;

/**
 * DataProcessing
 * Raw data processing methods.
 */
public class DataProcessing {

    private final HashMap<String, String> currency = new Currencies().currencies;

    public DataProcessing() {
    }

    /**
     * computeRatio
     * Compute ratio between two Float values.
     * Return both Float value within a Pair.
     * @param rateX - Float - float value.
     * @param rateY - Float - float value.
     */
    protected Pair<Float, Float> computeRatio(Float rateX, Float rateY ) {
        Float rateXtoY = new Float(0);
        Float rateYtoX = new Float(0);
        try {
            if (rateX == new Float(0) || rateY == new Float(0)) {
                throw new ArithmeticException("Arithmetic exception occured");
            }
            else {
                rateXtoY = rateY/rateX;
                rateYtoX = rateX/rateY;
            }
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        return new Pair<Float, Float>(rateXtoY, rateYtoX);
    }

    /**
     * computeExchangeRate
     * Received currencies and their amount grab from a file line.
     * Process them to return a data structure with exchange rate between both currencies.
     * @param couple couple - Vector - Raw data from file : Amount and Currency grab from a line file.
     * @param processedCouple - Vector - Vector of exchanges rates for each currencies
     */
    protected void computeExchangeRate(Vector < Pair<String, String> > couple, Vector < Pair <Pair<String,String>, Float> > processedCouple ) {
        if (couple.size() == 2) {
            Pair <Pair <String, String>, Float> exchangeRateX; // Currency - unit  <EUR,1>
            Pair <Pair <String, String>, Float> exchangeRateY; // Currency - unit <USD,1.19>


            Pair<String, String> itemX = couple.get(0);
            Pair<String, String> itemY = couple.get(1);
            Pair<Float, Float> ratio = computeRatio(Float.parseFloat(itemX.getValue()), Float.parseFloat(itemY.getValue()));

            exchangeRateX = new Pair<Pair<String,String>, Float>(new Pair<String, String>(itemX.getKey(), itemY.getKey()), ratio.getKey());
            exchangeRateY = new Pair<Pair<String,String>, Float>(new Pair<String, String>(itemY.getKey(), itemX.getKey()), ratio.getValue());

            processedCouple.add(exchangeRateX);
            processedCouple.add(exchangeRateY);
        }

        return;
    }

    /**
     * processFileLine
     * Get currencies and rate from the line.
     * Process currencies and rate in order to get ration between currencies.
     * Updatet currencies exchange rate data structure.
     * @param line - String - line of text issued from raw data file.
     * @param currenciesPair - Vector - currencies exchange rate data structure.
     */
    private void processFileLine(String line, Vector<Pair<Pair<String,String>, Float> > currenciesPair) {
        Pattern rateRe              = Pattern.compile("[0-9]+(\\.[0-9]*)?");
        Pattern currencyRe          = Pattern.compile("USD|EUR|CHF|GBP|OMR|BHD|KWD|SGD");
        Matcher rateMatch           = rateRe.matcher(line);
        Matcher currencyMatch       = currencyRe.matcher(line);
        Vector<Pair<String, String>> listCurRat = new Vector<Pair<String, String>>();
        Pair<String, String> pairCurRat;

        while (currencyMatch.find() && rateMatch.find()) {
            pairCurRat  = new Pair<String, String>(currencyMatch.group(), rateMatch.group());
            listCurRat.add(pairCurRat);
        }
        computeExchangeRate(listCurRat, currenciesPair);

    }

    /**
     * readFile
     * Read a file associated to a given date (format yyyy-MM-dd).
     * Process every line and update currencies exchange rate data structure.
     * @param date - String - file date
     */
    protected Vector<Pair<Pair<String,String>, Float> > readFile(String date) {
        Vector<Pair<Pair<String,String>, Float> > currenciesPair = new Vector<Pair<Pair<String,String>, Float>>();
        String path = String.format("./data/%s.txt", date);

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);

            while(reader.ready()) {
                String line = reader.readLine();
                processFileLine(line, currenciesPair);
            }

            reader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currenciesPair;
    }

    /**
     * getCurrenciesMap
     * Return a Map of currencies and associated unique number.
     * Map used to identify currency into matrice
     */
    protected HashMap<String, Integer> getCurrenciesMap() {
        // Set an index for each currency
        int index=0;
        HashMap<String, Integer> currenciesMap = new HashMap<String, Integer>();

        for (HashMap.Entry<String, String> curr: currency.entrySet())
            currenciesMap.put(curr.getKey(), index++);

        return currenciesMap;
    }

    /**
     * getExchangeRatesMatrix
     * Process exchange rates between every currencies availables.
     * Return a 2D Float array.
     * @param currenciesMap - map of currencies and associated unique number.
     * @param currenciesPair - currencies exchange rate data structure.
     */
    protected Float[][] getExchangeRatesMatrix(HashMap<String, Integer> currenciesMap, Vector< Pair<Pair<String, String>, Float> > currenciesPair) {

        int length = currenciesMap.size();

        // Martix of rate
        Float[][] rates = new Float[length][length];

        // Init every rate to -1
        for(int i=0; i < length; i++)
            for(int j=0; j < length; j++)
                rates[i][j] = new Float(-1.);

        // Rate from XXX => XXX = 1
        for(int i=0; i < length; i++)
            rates[i][i] = new Float(1.);
        // Insert known rates (XXX => YYY and YYY => XXX)
        for(Pair<Pair<String, String>, Float> obj: currenciesPair) {
            Integer idFrom = currenciesMap.get(obj.getKey().getKey());
            Integer idTo = currenciesMap.get(obj.getKey().getValue());
            rates[idFrom][idTo] = obj.getValue();
            rates[idTo][idFrom] = new Float(1.)/obj.getValue();
        }

        // Floyd Warshall algorithm in order to compute exchange rates between currencies.
        for(int i=0; i < length; i++) {
            for(int j=0; j < length; j++) {
                for(int k=0; k < length; k++) {
                    // If:
                    //  - Rate i=>j is not defined
                    //  - Rate i=>k & k=>j is defined
                    if (rates[i][j] == -1 && rates[i][k] != -1 && rates[k][j] != -1) {
                        rates[i][j] = rates[i][k] * rates[k][j];
                        rates[j][i] = new Float(1.)/rates[i][j];
                    }
                }
            }
        }

        return rates;
    }

    /**
     * getCurrenciesExchangeRateByDateInCurrency
     * Return list of exchanges rates between a specified currency and USD, on specified date.
     * @param date - String - date to check.
     * @param currencyId - String - Id of the currency
     */
    public String getCurrenciesExchangeRateByDateInCurrency(String date, String currencyId) {
        HashMap<String, Integer> currenciesMap = getCurrenciesMap();
        Vector<Pair<Pair<String,String>, Float> > currenciesPair = new Vector<Pair<Pair<String,String>, Float>>();
        currenciesPair = readFile(date);

        Float[][] rates = getExchangeRatesMatrix(currenciesMap, currenciesPair);
        Currencies data = new Currencies(date);
        data.setCurrenciesMap(currenciesMap);
        data.setRates(rates);
        String response = data.getAllExchangeRateAtDate(currencyId);

        return response;

    }

    /**
     * getCurrenciesExchangeRateByDateBetweenCurrencies
     * Return exchanges rates between two currencies X and Y on a specified date.
     * @param date - String - date to check
     * @param currencyX - String - first currency
     * @param currencyY - String - second currency
     */
    public String getCurrenciesExchangeRateByDateBetweenCurrencies(String date, String currencyX, String currencyY) {
        HashMap<String, Integer> currenciesMap = getCurrenciesMap();
        Vector<Pair<Pair<String,String>, Float> > currenciesPair = new Vector<Pair<Pair<String,String>, Float>>();

        currenciesPair = readFile(date);
        Float[][] rates = getExchangeRatesMatrix(currenciesMap, currenciesPair);

        Currencies data = new Currencies(date);
        data.setCurrenciesMap(currenciesMap);
        data.setRates(rates);
        String response = data.getExchangeRatesAtDateBetweenCurrencies(currencyX, currencyY);
        return response;
    }

    /**
     * getCurrenciesExchangeRateByDateInRange
     * Return on a date range list of exchange rate between specified currency and USD.
     * @param dateX - String - starting date
     * @param dateY - String - ending date
     * @param currency - String - currency id
     */
    public String getCurrenciesExchangeRateByDateInRange(String dateX, String dateY, String currency) {
        HashMap<String, Integer > currenciesMap = getCurrenciesMap();
        Vector<Pair<Pair<String,String>, Float> > currenciesPair = new Vector<Pair<Pair<String,String>, Float>>();
        ArrayList<String> response = new ArrayList<String>();

        SimpleDateFormat sdf    = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar       = Calendar.getInstance();
        Date endingDate         = new Date();

        try {
            endingDate = sdf.parse(dateY);
            calendar.setTime(sdf.parse(dateX));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (calendar.getTime().compareTo(endingDate) <= 0) {
            String date = sdf.format(calendar.getTime());
            currenciesPair  = readFile(date);

            if (currenciesPair.size() > 0) {
                Float[][] rates = getExchangeRatesMatrix(currenciesMap, currenciesPair);
                Currencies data = new Currencies(date);
                data.setCurrenciesMap(currenciesMap);
                data.setRates(rates);
                String str = data.getExchangeRatesAtDateBetweenCurrencies(currency, "USD");
                response.add(str);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        }

        return response.toString();
    }

}
