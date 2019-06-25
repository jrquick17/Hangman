import java.util.Scanner;

public class Hangman {
    private Scanner scan = new Scanner(System.in);

    private int strikes;
    private String[] answer, currentWord;
    private Opponent opponent;

    public Hangman() {
        opponent = new Opponent();
        strikes = 0;
    }

    private boolean addStrike() {
        this.strikes++;

        System.out.println("Strike " + strikes + " of 6!");
        switch (strikes) {
            case 1:
                System.out.println("  ___\n /   |\n O   |\n     |\n     |\n     |\n ____|____");
                break;
            case 2:
                System.out.println("  ___\n /   |\n O   |\n |   |\n |   |\n     |\n ____|____");
                break;
            case 3:
                System.out.println("  ___\n /   |\n O   |\n |   |\n |   |\n  \\  |\n ____|____");
                break;
            case 4:
                System.out.println("  ___\n /   |\n O   |\n |   |\n |   |\n/ \\  |\n ____|____");
                break;
            case 5:
                System.out.println("  ___\n /   |\n O   |\n\\|   |\n |   |\n/ \\  |\n ____|____");
                break;
            case 6:
            default:
                System.out.println("  ___\n /   |\n O   |\n\\|/  |\n |   |\n/ \\  |\n ____|____");
                break;
        }

        return strikes == 6;
    }

    private boolean answerContains(String guess) {
        for (String letter : answer) {
            if (guess.equals(letter)) {
                return true;
            }
        }

        return false;
    }

    private void fillIn(String letter) {
        for (int i = 0; i < answer.length; i++) {
            if (answer[i].equals(letter)) {
                currentWord[i] = answer[i];
            }
        }
    }

    private boolean guess(String letter) {
        if (!StatService.alreadyGuessed(letter)) {
            StatService.addToGuessed(letter);

            if (this.answerContains(letter)) {
                this.fillIn(letter);
                return true;
            } else {
                this.addStrike();
            }
        } else {
            System.out.println("Cute, but you already guessed that.");
        }

        return false;
    }

    private boolean isComplete() {
        for (String letter : currentWord) {
            if (letter == null) {
                return false;
            }
        }

        return true;
    }

    private boolean isStruckOut() {
        return strikes == 6;
    }

    private boolean keepPlaying() {
        return !isStruckOut() && !this.isComplete();
    }

    public void create() {
        System.out.println("What is your word?");
        String scanAnswer = scan.next();
        this.answer = WordService.toArray(scanAnswer);
        this.currentWord = new String[scanAnswer.length()];
        opponent.giveWordLength(currentWord.length);

        System.out.println("And which letter are you giving me? ");
        scanAnswer = scan.next();
        if (!scanAnswer.equalsIgnoreCase("None")) {
            StatService.addToGuessed(scanAnswer);
            this.fillIn(scanAnswer);
            opponent.giveCurrentWord(currentWord);
        }

        while (this.keepPlaying()) {
            System.out.println(WordService.toString(currentWord));
            String guessed = opponent.guessLetter();

            System.out.println("Was I correct?");
            scanAnswer = scan.next();

            if (scanAnswer.equalsIgnoreCase("Yes")) {
                StatService.addToGuessed(guessed);
                this.fillIn(guessed);
                opponent.giveCurrentWord(currentWord);
            } else if (scanAnswer.equalsIgnoreCase("No")) {
                StatService.addToGuessed(guessed);
                opponent.giveNotLetter(guessed);
                this.addStrike();
            } else {
                System.out.println("What? I didn't catch that.");
            }
        }

        if (this.isComplete()) {
            System.out.println("Suck on that fool!");
        } else if (this.isStruckOut()) {
            System.out.println("I'm not sure how, but we both know you're a cheating bastard!");
        }
    }

    public void help() {
        System.out.println("What is your word?");
        answer = WordService.toArray(scan.next());
        currentWord = new String[answer.length];

        opponent.giveWordLength(answer.length);

        System.out.println("Enter the known letter: ");
        String answer = scan.next();
        this.fillIn(answer);
        StatService.addToGuessed(answer);

        opponent.giveCurrentWord(currentWord);

        while (!isComplete()) {
            System.out.println(WordService.toString(currentWord));

            opponent.getLetterPercents();

            System.out.println("Guess a letter?");
            answer = scan.next();
            if (answer.equalsIgnoreCase("Show")) {
                for (int i = 0; i < opponent.getWords().size(); i++) {
                    System.out.println(opponent.getWords().get(i));
                }
            } else {
                StatService.addToGuessed(answer);
                this.fillIn(answer);
            }
        }

        System.out.println("Thank me later.");
    }

    public void play() {
        String currentWord = opponent.selectWord();
        this.currentWord = new String[currentWord.length()];
        answer = WordService.toArray(currentWord);

        System.out.println("Bet you can't guess my word, it's " + this.currentWord.length + " characters long.\nI'll even give you a letter.");
        String freebie = opponent.getFreebie(currentWord);
        this.fillIn(freebie);
        StatService.addToGuessed(freebie);

        while (this.keepPlaying()) {
            System.out.println(WordService.toString(this.currentWord));

            System.out.println("What letter would you like to guess? ");

            if (this.guess(scan.next())) {
                System.out.println("Lucky guess!");
            } else {
                System.out.println("Nope!");
            }
        }

        if (this.isComplete()) {
            System.out.println("You must have cheated!");
        } else if (this.isStruckOut()) {
            System.out.println("Didn't really expect much more from you honestly.\nThe word was " + WordService.toString(answer));
        }
    }

    public void start() {
        System.out.println("What would you like to do?");
        System.out.println("Type 'help' for help solving.");
        System.out.println("Type 'play' to try and guess a word.");
        System.out.println("Type 'create' for me to guess your word.");

        switch (scan.next()) {
            case "help":
                this.help();
                break;
            case "play":
                this.play();
                break;
            case "create":
                this.create();
                break;
            default:
                System.out.println("You messed up dumbass!");
        }
    }
}
