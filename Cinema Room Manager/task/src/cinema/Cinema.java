package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    public static int purchasedTicketsNumber = 0;
    public static double percentagePurchasedTickets = 0.00;
    public static int totalSeatsCount = 0;
    public static int currentIncome = 0;
    public static double totalIncome = 0.00;
    public static boolean isPurchaseInvalid = true;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        askNumberOfSeats(sc);
        sc.close();
    }

    public static void askNumberOfSeats(Scanner sc) {
        System.out.println("Enter the number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = sc.nextInt();
        String[][] arr = fillSeatsArray(rows, seats);
        totalSeatsCount = rows * seats;
        renderSeatsView(arr);
        displayMenu(sc, arr, rows, seats);
    }

    public static void displayMenu(Scanner sc, String[][] arr, int rows, int seats) {
        while(true) {
            System.out.print("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n");
            int userChoice = sc.nextInt();

            switch (userChoice) {
                case 0:
                    return;
                case 1:
                    renderSeatsView(arr);
                    break;
                case 2:
                    while (isPurchaseInvalid) {
                        System.out.println("Enter a row number:");
                        int row = sc.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int seat = sc.nextInt();
                        currentIncome += checkPriceSeatPosition(rows, seats, row);
                        purchaseTicket(arr, row, seat, checkPriceSeatPosition(rows, seats, row));
                    }
                    isPurchaseInvalid = true;
                    break;
                case 3:
                    totalIncome = profitCalculator(rows, seats);
                    getStatistics();
                    break;
            }
        }
    }

    public static int checkPriceSeatPosition(int rows, int seats, int wantedRow) {
        return getSeatPrice(rows, seats, wantedRow);
    }

    public static void purchaseTicket(String[][] arr, int row, int seat, int price) {

        try {
            if(Objects.equals(arr[row][seat], "S")) {
                arr[row][seat] = "B";
                purchasedTicketsNumber++;
                printTicketPrice(price);
                isPurchaseInvalid = false;
            } else {
                System.out.println("That ticket has already been purchased!\n");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\nWrong input!\n");
        }
    }

    public static void getStatistics() {
        percentagePurchasedTickets = (double) purchasedTicketsNumber / totalSeatsCount * 100;
        System.out.printf("Number of purchased tickets: %d%n", purchasedTicketsNumber);
        System.out.printf("Percentage: %.2f%%%n", percentagePurchasedTickets);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%.0f%n%n", totalIncome);
    }

    public static void renderSeatsView(int rows, int seats) {
        printCinema();
        System.out.print(printSeats(fillSeatsArray(rows, seats)));
    }

    public static void renderSeatsView(String[][] arr) {
        printCinema();
        System.out.println(printSeats(arr));
    }

    public static void printCinema() {
        final String cinema = "Cinema:";
        System.out.println(cinema);
    }

    public static String[][] fillSeatsArray(int rows, int seats) {
        final int headerStartingNumber = 1;
        int totalRows = rows + headerStartingNumber;
        int totalSeats = seats + headerStartingNumber;
        String[][] arrSeats = new String[totalRows][totalSeats];
        int acc = 1;

        for(int i = 0; i < totalRows; i++) {
            for(int j = 0; j < totalSeats; j++) {
                if(i == 0) {
                    if(j == 0) {
                        arrSeats[i][j] = " ";
                    } else {
                        arrSeats[i][j] = Integer.toString(j);
                    }
                } else if(j == 0) {
                    arrSeats[i][j] = Integer.toString(acc++);
                } else {
                    arrSeats[i][j] = "S";
                }
            }
        }
        return arrSeats;
    }

    public static String printSeats(String[][] arr) {
        String strView = "";
        for (String[] strings : arr) {
            strView = strView.concat(String.join(" ", strings) + "\n");
        }
        return strView;
    }

    public static int profitCalculator(int rows, int seats) {
        int price, frontHalfRows, backHalfRows, totalSeats = rows * seats;
        final int frontHalfPrice = 10, backHalfPrice = 8;

        if(totalSeats < 60) {
            price = 10 * (totalSeats);
        } else {
            frontHalfRows = rows / 2;
            backHalfRows = rows - frontHalfRows;
            price = frontHalfRows * seats * frontHalfPrice + backHalfRows * seats * backHalfPrice;
        }
        return price;
    }

    public static int getSeatPrice(int rows, int seats, int wantedRow) {
        int price;
        int frontHalfRows = rows / 2;
        int totalSeats = rows * seats;

        if(totalSeats < 60) {
            price = 10;
        } else if (wantedRow > frontHalfRows) {
            price = 8;
        } else {
            price = 10;
        }

        return price;
    }

    public static void printFormatProfit(int price) {
        System.out.printf("Total Income:%n$%d%n", price);
    }

    public static void printTicketPrice(int price) {
        System.out.printf("%nTicket price: $%d%n%n", price);
    }

    public static void renderPrice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rowNumber = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsNumber = sc.nextInt();
        int profit = profitCalculator(rowNumber, seatsNumber);
        printFormatProfit(profit);
        sc.close();
    }
}