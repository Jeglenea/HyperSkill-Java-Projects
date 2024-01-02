package bullscows;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length = 0;
        int numPossibleChars = 0;

        try {
            System.out.println("Input the length of the secret code");
            length = readInt(scanner);

            System.out.println("Input the number of possible symbols in the code:");
            numPossibleChars = readInt(scanner);

            validateCodeLength(length, numPossibleChars);
        } catch (InputMismatchException e) {
            System.out.println("Error: \"" + scanner.next() + "\" isn't a valid number.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (length <= 0 || length > 36 || numPossibleChars <= 0 || numPossibleChars > 36) {
            System.out.println("Error: Invalid code length or number of possible characters.");
            return;
        }

        // Generate the secret code
        String secretCode = generateSecretCode(length, numPossibleChars);
        System.out.println("The secret is prepared: " + "*".repeat(length) + " (" + getAvailableChars(numPossibleChars) + ").");
        // Uncomment the line below to see the secret code while testing
        // System.out.println("Secret Code: " + secretCode);
        System.out.println("Secret Code: " + secretCode);
        System.out.println("Okay, let's start a game!");

        int turn = 1;

        while (true) {
            System.out.println("Turn " + turn + ":");
            String userGuess = scanner.next();

            // Grade the user's guess
            int[] grade = gradeGuess(userGuess, secretCode);

            // Print the grade
            System.out.println(formatGrade(grade));

            // Check if the user has guessed the code
            if (grade[0] == length) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }

            turn++;
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: \"" + scanner.next() + "\" isn't a valid number.");
            }
        }
    }

    private static String generateSecretCode(int length, int numPossibleChars) {
        if (length > numPossibleChars) {
            throw new IllegalArgumentException("Error: It's not possible to generate a code with a length of " +
                    length + " with " + numPossibleChars + " unique symbols.");
        }

        StringBuilder secretCode = new StringBuilder();
        Random random = new Random();

        Set<Character> usedChars = new HashSet<>();

        for (int i = 0; i < length; i++) {
            char randomChar;
            do {
                int randomIndex = random.nextInt(numPossibleChars);
                randomChar = getCharByIndex(randomIndex);
            } while (usedChars.contains(randomChar));

            usedChars.add(randomChar);
            secretCode.append(randomChar);
        }

        System.out.println("Generating secret code with length: " + length + ", possible characters: " + numPossibleChars);
        return secretCode.toString();
    }

    private static void validateCodeLength(int length, int numPossibleChars) {
        if (length > numPossibleChars) {
            throw new IllegalArgumentException("It's not possible to generate a code with a length of " +
                    length + " with " + numPossibleChars + " unique symbols.");
        }
    }

    private static char getCharByIndex(int index) {
        if (index < 10) {
            return (char) ('0' + index);
        } else if (index < 36) {
            return (char) ('a' + index - 10);
        } else {
            // Handle the case where index is out of bounds
            throw new IllegalArgumentException("Invalid index");
        }
    }

    private static String getAvailableChars(int numPossibleChars) {
        StringBuilder availableChars = new StringBuilder();

        char firstChar = getCharByIndex(0);
        char lastChar = getCharByIndex(Math.min(numPossibleChars - 1, 35));

        if (firstChar <= '9' && lastChar >= 'a') {
            // If both numeric and alphabetic characters are included
            availableChars.append(firstChar).append("-9, a-").append(lastChar);
        } else {
            // If only numeric characters or only alphabetic characters are included
            availableChars.append(firstChar).append("-").append(lastChar);
        }

        // Do not add a period at the end

        return availableChars.toString();
    }




    private static int[] gradeGuess(String userGuess, String secretCode) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < userGuess.length(); i++) {
            char guessDigit = userGuess.charAt(i);
            char secretDigit = secretCode.charAt(i);

            if (guessDigit == secretDigit) {
                bulls++;
            } else if (secretCode.contains(String.valueOf(guessDigit))) {
                cows++;
            }
        }

        return new int[]{bulls, cows};
    }

    private static String formatGrade(int[] grade) {
        if (grade[0] == 0 && grade[1] == 0) {
            return "Grade: None";
        }

        StringBuilder result = new StringBuilder("Grade: ");
        if (grade[0] > 0) {
            result.append(grade[0]).append(grade[0] > 1 ? " bulls" : " bull");
        }

        if (grade[0] > 0 && grade[1] > 0) {
            result.append(" and ");
        }

        if (grade[1] > 0) {
            result.append(grade[1]).append(grade[1] > 1 ? " cows" : " cow");
        }

        return result.toString();
    }
}
