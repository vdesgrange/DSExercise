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

import currencyBusiness.Currencies;

public class DataProcessing {

    private static final HashMap<String, String> currency = new Currencies().currencies;

    DataProcessing() {
    }

    protected static Pair<Float, Float> computeRatio(Float rateX, Float rateY ) {
        Float rateXtoY = rateY/rateX;
        Float rateYtoX = rateX/rateY;

        return new Pair<Float, Float>(rateXtoY, rateYtoX);
    }

    protected static void computeExchangeRate(Vector < Pair<String, String> > couple, Vector < Pair <Pair<String,String>, Float> > processedCouple ) {
        if (couple.size() == 2) {
            Pair <Pair <String, String>, Float> exchangeRateX;
            Pair <Pair <String, String>, Float> exchangeRateY;


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


    private static void processFileLine(String line, Vector<Pair<Pair<String,String>, Float> > currenciesPair) {
        Pattern rateRe              = Pattern.compile("[0-9]+(\\.[0-9]*)?");
        Pattern currencyRe          = Pattern.compile("USD|EUR|CHF|GBP|OMR|BHD|KWH|SGD");
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

    protected static Vector<Pair<Pair<String,String>, Float> > readFile(String date) {
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

    protected static void writeFile(String date, HashMap<String, Integer> currencies, Float[][] rates) {
        try {
            String path         = String.format("%s.csv", date);
            FileWriter writer   = new FileWriter(path);

            for (HashMap.Entry<String,Integer> curr: currencies.entrySet()) {
                writer.write(curr + ",");
            }

            for (int i=0; i < rates.length; i++) {
                writer.write('\n');
                for (int j=0; j < rates[0].length; j++) {
                    writer.write(rates[i][j] + ",");
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static HashMap<String, Integer> getCurrenciesMap() {
        // Set an index for each currency
        int index=0;
        HashMap<String, Integer> currenciesMap = new HashMap<String, Integer>();

        for (HashMap.Entry<String, String> curr: currency.entrySet())
            currenciesMap.put(curr.getKey(), index++);

        return currenciesMap;
    }

    protected static Float[][] getExchangeRatesMatrix(HashMap<String, Integer> currenciesMap, Vector< Pair<Pair<String, String>, Float> > currenciesPair) {

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

        // Floyd Warshall
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

    public static HashMap getCurrenciesExchangeRateByDateInCurrency(String date, String currencyId) {
        // Set an index for each currency
        HashMap <String, HashMap <Pair <String, String>, Float> > data = new HashMap <String, HashMap <Pair <String, String>, Float> >();
        HashMap<String, Integer> currenciesMap = getCurrenciesMap();
        Vector<Pair<Pair<String,String>, Float> > currenciesPair = new Vector<Pair<Pair<String,String>, Float>>();
        currenciesPair = readFile(date);

        Float[][] rates = getExchangeRatesMatrix(currenciesMap, currenciesPair);

        Integer currencyIndex = currenciesMap.get(currencyId);

        HashMap<Pair<String, String>, Float> exchangeRateMap = new HashMap<Pair<String,String>, Float>();
        for (HashMap.Entry<String, Integer> curr: currenciesMap.entrySet()) {
            exchangeRateMap.put(new Pair<String, String>(curr.getKey(),currencyId), rates[curr.getValue()][currencyIndex]);
        }
        data.put(date, exchangeRateMap);
        return data;
    }

    public static HashMap getCurrenciesExchangeRateByDateBetweenCurrencies(String date, String currencyX, String currencyY) {
        // Set an index for each currency
        HashMap <String, HashMap <Pair <String, String>, Float> > data = new HashMap <String, HashMap <Pair <String, String>, Float> >();
        HashMap<String, Integer> currenciesMap = getCurrenciesMap();
        Vector<Pair<Pair<String,String>, Float> > currenciesPair = new Vector<Pair<Pair<String,String>, Float>>();
        HashMap<Pair<String, String>, Float> exchangeRateMap = new HashMap<Pair<String,String>, Float>();

        currenciesPair = readFile(date);
        Float[][] rates = getExchangeRatesMatrix(currenciesMap, currenciesPair);
        Integer currencyXIndex = currenciesMap.get(currencyX);
        Integer currencyYIndex = currenciesMap.get(currencyY);

        exchangeRateMap.put(new Pair<String, String>(currencyX, currencyY), rates[currencyXIndex][currencyYIndex]);
        exchangeRateMap.put(new Pair<String, String>(currencyY, currencyX), rates[currencyYIndex][currencyXIndex]);
        data.put(date, exchangeRateMap);

        return data;
    }

    public static HashMap getCurrenciesExchangeRateByDateInRange(String dateX, String dateY, String currency) {
        // Set an index for each currency
        HashMap <String, HashMap <Pair <String, String>, Float> > data = new HashMap <String, HashMap <Pair <String, String>, Float> >();
        HashMap<String, Integer > currenciesMap = getCurrenciesMap();
        Vector<Pair<Pair<String,String>, Float> > currenciesPair = new Vector<Pair<Pair<String,String>, Float>>();

        Integer currencyXIndex  = currenciesMap.get(currency);
        Integer currencyYIndex  = currenciesMap.get("USD");
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
            HashMap<Pair<String, String>, Float> exchangeRateMap = new HashMap<Pair<String,String>, Float>();

            if (currenciesPair.size() > 0) {
                Float[][] rates = getExchangeRatesMatrix(currenciesMap, currenciesPair);
                exchangeRateMap.put(new Pair<String, String>(currency, "USD"), rates[currencyXIndex][currencyYIndex]);
            }
            data.put(date, exchangeRateMap);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        }

        return data;
    }

}
