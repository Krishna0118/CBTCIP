import java.util.Random;
import java.util.Scanner;
public class task1 {

public static void main(String[] args) {
    guessNumber();
}

public static void guessNumber() {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    int score = 0;
    boolean guess = true;

    while (guess) {
        int attempts = 0;
        int maxAttempts = 10;
        int numberToGuess = random.nextInt(100) + 1;
        boolean Won = false;

        System.out.println("Let's get started!");

        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int userGuess = scanner.nextInt();
            attempts++;

            if (userGuess == numberToGuess) {
                Won = true;
                break;
            } else if (userGuess < numberToGuess) {
                System.out.printf("Number is greater than %d. Attempts left: %d\n",userGuess,maxAttempts-attempts);
            } else {
                System.out.printf("Number is lower than %d. Attempts left: %d\n" ,userGuess,maxAttempts-attempts);
            }
        }

        if (Won) {
            int points = maxAttempts - attempts + 1;
            score += points;
            System.out.println("Hurray! You've guessed the number in " + attempts + " attempts.");
            System.out.println("You earned " + points + " points. Your total score is: " + score);
        } else {
            System.out.println("Sorry! You've used all " + maxAttempts + " attempts. The number was: " + numberToGuess);
        }

        System.out.print("Do you want to play another round? (yes/no): ");
        String response = scanner.next();
        guess = response.equalsIgnoreCase("yes");
    }

    System.out.println("Thank you for playing! Your final score is: " + score);
    scanner.close();
}
}

