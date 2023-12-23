package machine;

import java.util.Scanner;


public class CoffeeMachine {
    private static int waterCap = 400;
    private static int milkCap = 540;
    private static int beanCap = 120;
    private static int cupCap = 9;
    private static int moneyCap = 550;

    enum CoffeeType {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        int water;
        int milk;
        int beans;
        int cost;

        CoffeeType(int water, int milk, int beans, int cost) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println(CoffeeMachine.stateMessage());
        } while (CoffeeMachine.pushCommand(sc.nextLine()));
    }

    public static String stateMessage() {
        return "Write action (buy, fill, take, remaining, exit):";
    }

    public static boolean pushCommand(String command) {
        Scanner scanner = new Scanner(System.in);
        switch (command.toLowerCase()) {
            case "buy":
                buyCoffee(scanner);
                break;
            case "fill":
                fillMachine(scanner);
                break;
            case "take":
                takeMoney();
                break;
            case "remaining":
                displayResources();
                break;
            case "exit":
                return false;
            default:
                System.out.println("Invalid command. Please try again.");
        }
        return true;
    }

    private static void buyCoffee(Scanner scanner) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice >= 1 && choice <= CoffeeType.values().length) {
                CoffeeType selectedCoffee = CoffeeType.values()[choice - 1];

                if (hasEnoughResources(selectedCoffee)) {
                    makeCoffee(selectedCoffee);
                    System.out.println("I have enough resources, making you a coffee!");
                } else {
                    displayResourceShortage(selectedCoffee);
                }
            } else {
                System.out.println("Invalid choice. Please enter a valid number.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
        }
        System.out.println("");
    }

    private static boolean hasEnoughResources(CoffeeType coffee) {
        return waterCap >= coffee.water && milkCap >= coffee.milk &&
                beanCap >= coffee.beans && cupCap >= 1;
    }

    private static void makeCoffee(CoffeeType coffee) {
        waterCap -= coffee.water;
        milkCap -= coffee.milk;
        beanCap -= coffee.beans;
        cupCap--;
        moneyCap += coffee.cost;
    }

    private static void displayResourceShortage(CoffeeType coffee) {
        if (waterCap < coffee.water) {
            System.out.println("Sorry, not enough water!");
        } else if (milkCap < coffee.milk) {
            System.out.println("Sorry, not enough milk!");
        } else if (beanCap < coffee.beans) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (cupCap < 1) {
            System.out.println("Sorry, not enough cups!");
        }
    }

    private static void fillMachine(Scanner scanner) {
        System.out.println("Write how many ml of water you want to add:");
        int water = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Write how many ml of milk you want to add:");
        int milk = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int beans = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Write how many disposable cups you want to add:");
        int cups = scanner.nextInt();
        scanner.nextLine();

        waterCap += water;
        milkCap += milk;
        beanCap += beans;
        cupCap += cups;
        System.out.println("");
    }

    private static void takeMoney() {
        System.out.println("I gave you $" + moneyCap);
        moneyCap = 0;
        System.out.println("");
    }

    private static void displayResources() {
        System.out.println("The coffee machine has:");
        System.out.println(waterCap + " ml of water");
        System.out.println(milkCap + " ml of milk");
        System.out.println(beanCap + " g of coffee beans");
        System.out.println(cupCap + " disposable cups");
        System.out.println("$" + moneyCap + " of money");
        System.out.println("");
    }
}
