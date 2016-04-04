import java.util.Scanner;

public class MainDriver {
    public static void main(String[]args) {
        Hangman hangman = new Hangman();
        Scanner scan = new Scanner(System.in);

        System.out.println("What would you like to do?");
        System.out.println("Type 'help' for help solving.");
        System.out.println("Type 'play' to try and guess a word.");
        System.out.println("Type 'create' for me to guess your word.");

        switch (scan.next()) {
            case "help":
                hangman.help();
                break;
            case "play":
                hangman.play();
                break;
            case "create":
//                hangman.create();
                break;
            default:
                System.out.println("You messed up dumbass!");
        }
    }
}
