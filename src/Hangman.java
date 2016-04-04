import java.util.Scanner;

public class Hangman {
    int strikes;

    Scanner scan = new Scanner(System.in);

    String[] answer, currentWord, lettersPossible, lettersGuessed;
    Opponent opponent;

    public Hangman() {
        opponent = new Opponent();

        lettersPossible = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
        lettersGuessed = new String[26];
    }

    private boolean addStrike() {
        strikes++;

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

    private void addToGuessed(String letter) {
        for (int i = 0; i < lettersPossible.length; i++) {
            if (letter.equalsIgnoreCase(lettersPossible[i]))
                lettersGuessed[i] = letter;
        }
    }

    private boolean alreadyGuessed(String letter) {
        for (int i = 0; i < 26; i++) {
            if (letter.equalsIgnoreCase(lettersGuessed[i])) {
                return true;
            }
        }

        return false;
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
        if (!this.alreadyGuessed(letter)) {
            this.addToGuessed(letter);

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

    private void showBestOptions() {
        double letterPercents[] = new double[26];
        for (int let = 0, num = 0; let < 26; let++, num = 0) {
            for (int i = 0; i < opponent.getWords().size(); i++) {
                if (opponent.getWords().get(i).contains(lettersPossible[let]))
                    num++;
            }

            letterPercents[let] = ((double)num/opponent.getWords().size()*100);

            System.out.println(lettersPossible[let] + ":\t" + letterPercents[let]);
        }
    }

//    public void create() {
//        System.out.println("How many letters are in your word? ");
//        currentWord = new String[scan.nextInt()];
//        opponent.setWords(removeByLength(currentWord.length));
//
//        System.out.println("And what letter are you giving me? ");
//        String letter = scan.next();
//        if (!letter.equalsIgnoreCase("None")) {
//            System.out.println("Where is the letter located? ");
//            placement = scan.nextInt();
//            opponent.setWords(removeByCorrectLetter(letter, placement));
//            addToGuessed(letter);
//            currentWord[placement] = letter;
//        }
//
//        while (!this.isComplete() && !this.isStruckOut()) {
//            System.out.println(WordService.toString(currentWord));
//
//            letter = showBestOptions(false);
//
//            System.out.println("Was I correct? ");
//            answered = scan.next();
//
//            if (answered.equalsIgnoreCase("Yes")) {
//                multLetter = true;
//                while (multLetter) {
//                    System.out.println("Where is the letter located? ");
//                    placement = scan.nextInt();
//                    opponent.setWords(removeByCorrectLetter(letter, placement));
//                    currentWord[placement] = letter;
//
//                    if (!isComplete()) {
//                        System.out.println("Is there multiple places for that letter? ");
//                        answered = scan.next();
//                        if (answered.equalsIgnoreCase("no"))
//                            multLetter = false;
//                    } else {
//                        multLetter = false;
//                    }
//                }
//            } else if (answered.equalsIgnoreCase("no")) {
//                opponent.setWords(removeByWrongLetter(letter));
//                struckOut = addStrike();
//            } else {
//                System.out.println("Screwing up, eh?");
//            }
//
//            isComplete = isComplete();
//        }
//
//        if (isComplete)
//            System.out.println("Suck on that fool!");
//        else if (struckOut)
//            System.out.println("I'm not sure how, but we both know you're a cheating bastard!");
//    }

    public void help() {
        System.out.println("What is your word?");
        answer = WordService.toArray(scan.next());
        currentWord = new String[answer.length];

        opponent.giveWordLength(answer.length);

        System.out.println("Enter the known letter: ");
        String answer = scan.next();
        this.fillIn(answer);
        addToGuessed(answer);

        opponent.giveCurrentWord(currentWord);

        while (!isComplete()) {
            System.out.println(WordService.toString(currentWord));

            showBestOptions();

            System.out.println("Guess a letter?");
            answer = scan.next();
            if (answer.equalsIgnoreCase("Show")) {
                for (int i = 0; i < opponent.getWords().size(); i++) {
                    System.out.println(opponent.getWords().get(i));
                }
            } else {
                this.addToGuessed(answer);
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
        this.addToGuessed(freebie);

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
}
