/**
 * @author Viviane Desgrange
 */
package client;

import java.lang.String;
import java.util.HashMap;
import java.util.Vector;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * CurrencyBusiness
 * General methods from Client side.
 * Shell interface and processing methods.
 */
public class CurrencyBusiness {

    private static final HashMap<String, String> currency = new Currencies().currencies;

    CurrencyBusiness() {
    }

    /**
     * processUserEntry
     * Check the content of the user entry into shell.
     * Get currencies and dates. Call API endpoint depending on entries.
     * @param entry - String - user entry
     */
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

        if (exit) { // User entry is "exit", stop the client.

            return exit;

        } else if (Pattern.matches("currencies", text)) { // User entry is "currencies", list all currencies.

            System.out.println("\n ###### List of all currencies availables ######");
            for (HashMap.Entry<String,String> item : currency.entrySet()) {
                System.out.format("%s : %s \n", item.getKey(), item.getValue());
            }
            System.out.println();

        } else {

            while (dateMatch.find()) { // Find dates into user entry
                dateList.add(dateMatch.group());
            }

            while(currencyMatch.find()) { // Find currencies into user entry
                currList.add(currencyMatch.group());
            }

            if (dateList.size() == 1 && currList.size() == 0) { // Only one date - display all currencies to USD
                Application.getAllCurrencies(dateList.get(0));
            } else if (dateList.size() == 1 && currList.size() == 2) { // One date - two currencies -> display exchange rate between currencies.
                Application.getCurrenciesExchangeRate(dateList.get(0), currList.get(0), currList.get(1));
            } else if (dateList.size() == 2 && currList.size() == 1) { // Two dates - one currency -> display exchange rate to USD in date range.
                Application.getCurrenciesExchangeRateRange(dateList.get(0), dateList.get(1), currList.get(0));
            }

        }

        return exit;
    }

    /**
     * userInterface
     * Display small shell interface.
     */
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

    /**
     * main
     * Call user interface
     */
    public static void main(String args[]) {
        userInterface();
    }
}
