package src.primeirob.projetodois;

import java.util.Scanner;

public class Converter {
    private static int choice;
    private static int choiceConverter;

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
            do{
                clearConsole();
                System.out.println("(System) Welcome to Converter! \n");
                System.out.println("(System) This project is a converter from integers to roman numbers, and currency converter! \n");
                System.out.println("(System) Ready to get Started?! \n");
                System.out.println("(System) [1] Yes | [2] No, Exit \n");
                System.out.printf("(User) R: ");
                choice = reader.nextInt();

                if(choice == 1){
                    clearConsole();
                    System.out.println("(System) Awesome! \n");
                    System.out.println("(System) So to get started, I need some information from you! \n");
                    System.out.println("(System) Which conversion would you like to make?! \n");
                    System.out.println("(System) [1] Whole numbers for Romans | [2] Currency converter(\"USD\", \"EUR\", \"JPY\", \"GBP\", \"BRL\") | [3] Exit\n");
                    System.out.printf("(User) R: ");
                    choiceConverter = reader.nextInt();

                    switch (choiceConverter) {
                        case 1:
                            toRoman(reader);
                            break;

                        case 2:
                            try {
                                currencyConverter(reader);
                            } catch (IllegalArgumentException e) {
                                choice = 2;
                                System.out.println("Error: " + e.getMessage());
                            }
                            break;
                        case 3:
                            choice = 2;
                            break;

                        default:
                            break;
                    }
                }
        }while (choice != 2);
        reader.close();
    }

    private static void toRoman(Scanner reader) {
        int[] integers = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        String[] romans = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        StringBuilder result = new StringBuilder();

        int numberByUser;
        int choiceRoman;

        clearConsole();
        System.out.println("(System) Whole numbers for Romans! \n");
        System.out.println("(System) To begin, I need you to enter an integer in the range 1 to 3999! \n");
        System.out.printf("(User) R: ");
        numberByUser = reader.nextInt();

        for (int i = 0; i < integers.length; i++) {
            while (numberByUser >= integers[i]) {
                numberByUser -= integers[i];
                result.append(romans[i]);
            }
        }
        System.out.println("\n(System) The number in Roman numerals is: " + result.toString() + "! \n");
        System.out.println("(System) Would you like to perform another conversion?! \n");
        System.out.println("(System) [1] Yes | [2] No \n");
        choiceRoman = reader.nextInt();
        System.out.printf("(User) R: ");

        if (choiceRoman == 1) {
            toRoman(reader);
        }
    }

    private static void currencyConverter(Scanner reader) {
        String[] currency = { "USD", "EUR", "JPY", "GBP", "BRL" };
        double[] exchangeRate = { 1.0, 0.90, 144.907, 0.76, 5.64 };

        double currencyByUser;
        int choiceCurrancyTo;
        int choiceCurrancyFrom;
        int anotherConversion;

        clearConsole();

        System.out.println("(System) Currency converter \n");
        System.out.println("(System) To get started, I need you to enter which currency you are converting from! \n");
        System.out.println("1 - USD (EUA Dolar) \n2 - EUR (Euro) \n3 - JPY (Japanese Yen) \n4 - GBP (Pound Sterling) \n5 - BRL (Brazilian Real)\n");
        System.out.printf("(User) R: ");
        choiceCurrancyFrom = reader.nextInt();

        if (choiceCurrancyFrom < 1 || choiceCurrancyFrom > currency.length) {
            throw new IllegalArgumentException("Invalid source currency: " + choiceCurrancyFrom);
        }

        System.out.println("\n(System) Now I need to know which currency you want to convert to! \n");
        System.out.printf("(User) R: ");
        choiceCurrancyTo = reader.nextInt();

        if (choiceCurrancyTo < 1 || choiceCurrancyTo > currency.length) {
            throw new IllegalArgumentException("Invalid destination currency: " + choiceCurrancyTo);
        }

        clearConsole();

        String currencyFromIndex = currency[choiceCurrancyFrom - 1];
        String currencyToIndex = currency[choiceCurrancyTo - 1];

        System.out.println("(System) You chose to convert from ".concat(currencyFromIndex).concat(" to ").concat(currencyToIndex).concat(" \n"));
        System.out.println("(System) Enter the amount you want to convert now! \n");
        System.out.printf("(User) R: ");
        currencyByUser = reader.nextDouble();

        double amountUSD = currencyByUser / exchangeRate[choiceCurrancyFrom - 1];
        double amount = amountUSD * exchangeRate[choiceCurrancyTo - 1];

        System.out.printf("\n\n(System): %.2f %s to %s is: %.2f \n", currencyByUser, currencyFromIndex, currencyToIndex, amount);
        System.out.println("(System) Would you like to perform another conversion?! \n");
        System.out.println("(System) [1] Yes | [2] No \n");
        System.out.printf("(User) R: ");
        anotherConversion = reader.nextInt();

        if (anotherConversion == 1) {
            currencyConverter(reader);
        }

    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}