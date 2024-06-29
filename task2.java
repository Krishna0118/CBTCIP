import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {

    String questionText;
    List<String> options;
    int correctOption;

    public Question(String questionText, List<String> options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }
}

class User {

    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class task2 {

    static List<Question> questions = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static User loggedInUser;
    static boolean sessionOpen = false;
    static Timer timer = new Timer();
    static int timeRemaining = 60; // 1 minutes in seconds
    static int correctAnswers = 0;

    public static void main(String[] args) {

        initializeQuestions();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {

                case 1:
                    login(scanner);
                    break;

                case 2:
                    register(scanner);
                    break;

                case 3:
                    System.out.println("Exiting the system.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select a valid option!");
            }
        }
    }

    public static void initializeQuestions() {

        List<String> options1 = new ArrayList<>();
        options1.add("Guido van Rossum");
        options1.add("James Gosling");
        options1.add("Dennis Ritchie");
        options1.add("Bjarne Stroustrup");

        List<String> options2 = new ArrayList<>();
        options2.add("JRE");
        options2.add("JIT");
        options2.add("JDK");
        options2.add("JVM");

        List<String> options3 = new ArrayList<>();
        options3.add("Object-oriented");
        options3.add("Use of pointers");
        options3.add("Portable");
        options3.add("Dynamic and Extensible");

        List<String> options4 = new ArrayList<>();
        options4.add(".js");
        options4.add(".txt");
        options4.add(".class");
        options4.add(".java");

        List<String> options5 = new ArrayList<>();
        options5.add("Polymorphism");
        options5.add("Inheritance");
        options5.add("Compilation");
        options5.add("Encapsulation");

        Question question1 = new Question("Who invented Java Programming?", options1, 1);
        Question question2 = new Question("Which component is used to compile, debug and execute the java programs?", options2, 2);
        Question question3 = new Question("Which one of the following is not a Java feature?", options3, 1);
        Question question4 = new Question("What is the extension of Java code files?", options4, 3);
        Question question5 = new Question("Which of the following is not an OOPS concept in Java?", options5, 2);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);
    }

    public static void register(Scanner scanner) {

        System.out.print("Enter a Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username)) {
                System.out.println("Username already taken. Please choose another one.");
                return;
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        System.out.println("Registration successful! You can now log in.");
    }

    public static void login(Scanner scanner) {

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                loggedInUser = user;
                sessionOpen = true;
                userMenu(scanner);
                return;
            }
        }

        System.out.println("Invalid credentials. Please try again!");
    }

    public static void userMenu(Scanner scanner) {

        while (sessionOpen) {
            System.out.println("\n1. Start Exam");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Logout");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {

                case 1:
                    startExam(scanner);
                    break;

                case 2:
                    updateProfile(scanner);
                    break;

                case 3:
                    changePassword(scanner);
                    break;

                case 4:
                    logout();
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option!");
            }
        }
    }

    public static void startExam(Scanner scanner) {

        System.out.println("\nWelcome, " + loggedInUser.username + "!");
        timer = new Timer();
        timeRemaining = 300; // Reset timer
        correctAnswers = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeRemaining--;
                System.out.println("Time remaining: " + timeRemaining / 60 + " minutes " + timeRemaining % 60 + " seconds");
                if (timeRemaining <= 0) {
                    System.out.println("\nTime's up! Auto-submitting your answers.");
                    endSession();
                    timer.cancel();
                }
            }
        }, 0, 1000);

        for (int i = 0; i < questions.size(); i++) {

            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.questionText);
            for (int j = 0; j < question.options.size(); j++) {
                System.out.println((j + 1) + ". " + question.options.get(j));
            }

            System.out.print("Select your answer (1-" + question.options.size() + "): ");
            int userChoice = scanner.nextInt();
            if (userChoice == question.correctOption + 1) {
                System.out.println("Correct!");
                correctAnswers++;
            } else {
                System.out.println("Incorrect!");
            }
        }

        System.out.println("Exam completed!");
        System.out.println("You scored " + correctAnswers + " out of " + questions.size());
        endSession();
    }

    public static void updateProfile(Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        for (User user : users) {
            if (user.username.equals(newUsername)) {
                System.out.println("Username already taken. Please choose another one.");
                return;
            }
        }
        loggedInUser.username = newUsername;
        System.out.println("Username updated successfully.");
    }

    public static void changePassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        loggedInUser.password = newPassword;
        System.out.println("Password updated successfully.");
    }

    public static void logout() {
        sessionOpen = false;
        loggedInUser = null;
        timer.cancel();
        System.out.println("Logged out successfully.");
    }

    public static void endSession() {
        sessionOpen = false;
        loggedInUser = null;
        timer.cancel();
        System.out.println("Session closed. You have been logged out.");
    }
}

