import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Java2Sheet {

    private static int choice;
    private static int choiceReveal;

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        clearConsole();

        do {
            clearConsole();
            System.out.println("(System) Welcome to Java2Sheet! \n");
            System.out.println("(System) This project aims to receive user data and create a CSV spreadsheet with this data! \n");
            System.out.println("(System) Ready to get Started?! \n");
            System.out.println("(System) [1] Yes | [2] No \n");
            System.out.printf("(User) R: ");
            choice = reader.nextInt();

            switch (choice) {
                case 1:
                    clearConsole();
                    System.out.println("(System) Awesome! \n");
                    System.out.println("(System) So to get started, I need some information from you! \n");
                    System.out.println("(System) How many columns do you want to insert into the CSV spreadsheet?! \n");
                    System.out.printf("(User) R: ");
                    
                    int numOfColumns = reader.nextInt();

                    clearConsole();
                    System.out.println("(System) Awesome! \n");
                    System.out.printf("(System) You have chosen " + numOfColumns + " columns to insert into the CSV spreadsheet! \n");
                    System.out.println("(System) Now, I will need the names of the columns you want to insert! \n");

                    String[] columnHeaders = new String[numOfColumns];

                    clearBuffer(reader);
                    for (int i = 0; i < numOfColumns; i++) {
                        System.out.println("(System) Enter header for column " + (i + 1));
                        System.out.printf("(User) R: ");
                        columnHeaders[i] = reader.nextLine();
                        System.out.println("\n");
                    }

                    System.out.println("(System) Now, I will need as many rows as you want to insert into the spreadsheet! \n");
                    System.out.printf("(User) R: ");
                    int numOfRows = reader.nextInt();

                    clearBuffer(reader);
                    String[][] dataSheet = new String[numOfRows][numOfColumns];

                    clearConsole();
                    System.out.println("(System) Amazing! \n");
                    System.out.println("(System) Now let's fill in the spreadsheet rows according to the number of data you chose! \n");

                    for (int i = 0; i < numOfRows; i++) {
                        System.out.println("(System) Enter data for row " + (i + 1) + ": ");
                        for (int j = 0; j < numOfColumns; j++) {
                            System.out.printf("(User) Column %d: ", j + 1);
                            dataSheet[i][j] = reader.nextLine();
                        }
                    }

                    clearConsole();
                    System.out.println("(System) Amazing! \n");
                    System.out.println("(System) All data entered has been stored in memory, now we will process your data into the spreadsheet! \n");

                    try (FileWriter writer = new FileWriter("Java2Sheet.csv")) {

                        for (int i = 0; i < columnHeaders.length; i++) {
                            writer.append(columnHeaders[i]);
                            if (i < columnHeaders.length - 1) {
                                writer.append(",");
                            }
                        }
                        writer.append("\n");

                        for (int i = 0; i < dataSheet.length; i++) {
                            for (int j = 0; j < dataSheet[i].length; j++) {
                                writer.append(dataSheet[i][j]);
                                if (j < dataSheet[i].length - 1) {
                                    writer.append(",");
                                }
                            }
                            writer.append("\n");
                        }
                        
                        

                        do{
                            System.out.println("(System) CSV file created successfully! \n\n");
                            System.out.println("(System) Would you like it to open the file in file explorer?! \n");
                            System.out.println("(System) [1] Yes | [2] No, return to menu \n");
                            System.out.printf("(User) R: ");
                            choiceReveal = reader.nextInt();

                            switch (choiceReveal) {
                                case 1:
                                    openExplorer();
                                    choiceReveal = 2;
                                    break;
                            
                                default:
                                    break;
                            }

                        }while(choiceReveal != 2);

                    } catch (IOException e) {
                        System.out.println("(System) An error occurred while creating the CSV file.");
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;
            }

        } while (choice != 2);
        reader.close();
    }

    // Function to clean the console
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

    // Function to clear the buffer after reading integers or other data types
    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private static void openExplorer() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) {
                new ProcessBuilder("explorer", ".").start();
            } else if (os.contains("mac")) {
                new ProcessBuilder("open", ".").start();
            } else {
                System.out.println("Sistema operacional não suportado!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
