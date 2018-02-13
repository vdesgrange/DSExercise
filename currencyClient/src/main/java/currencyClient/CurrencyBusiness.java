package client;

import java.lang.String;
import java.util.HashMap;
import java.util.Vector;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CurrencyBusiness {

    private static final HashMap<String, String> currency = new Currencies().currencies;

    CurrencyBusiness() {
    }

    private static boolean processUserEntry(String entry) {
        boolean exit            = false;
        String text             = entry;
        String date             = "";
        Pattern dateRe          = Pattern.compile("([0-9]{4})-([0-9]{2})-([0-9]{2})");
        Pattern currencyRe      = Pattern.compile("USD|EUR|CHF|GBP|OMR|BHD|KWH|SGD");
        Matcher dateMatch       = dateRe.matcher(text);
        Matcher currencyMatch   = currencyRe.matcher(text);
        Vector<String> dateList = new Vector<String>();
        Vector<String> currList = new Vector<String>();

        exit = Pattern.matches("exit", text);

        if (exit) {

            return exit;

        } else if (Pattern.matches("currencies", text)) {

            System.out.println("\n ###### List of all currencies availables ######");
            for (HashMap.Entry<String,String> item : currency.entrySet()) {
                System.out.format("%s : %s \n", item.getKey(), item.getValue());
            }
            System.out.println();

        } else {

            while (dateMatch.find()) {
                dateList.add(dateMatch.group());
            }

            while(currencyMatch.find()) {
                currList.add(currencyMatch.group());
            }

            if (dateList.size() == 1 && currList.size() == 0) {
                Application.getAllCurrencies(dateList.get(0));
            } else if (dateList.size() == 1 && currList.size() == 2) {
                Application.getCurrenciesExchangeRate(dateList.get(0), currList.get(0), currList.get(1));
            } else if (dateList.size() == 2 && currList.size() == 1) {
                Application.getCurrenciesExchangeRateRange(dateList.get(0), dateList.get(1), currList.get(0));
            }

        }

        return exit;
    }

    protected static void userInterface() {
        boolean exit    = false;
        String entry    = "";
        Scanner sc      = new Scanner(System.in);

        while(!exit) {
            System.out.println("####### Instruction #######");
            System.out.println("1 - Enter a date to get all currencies exchange rate (wrt USD).");
            System.out.println("2 - Enter a date and two currencies to get exchange rate between them.");
            System.out.println("3 - Enter a starting date, an ending date and a currency to get exchange rate in USD on that range.");
            System.out.println("4 - Type 'currencies' to list currencies availables.");
            System.out.println("5 - Type 'exit' in order to quit. \n");
            entry = sc.nextLine();
            exit = processUserEntry(entry);
        }
        return;
    }

    public static void main(String args[]) {
        userInterface();
    }
}
