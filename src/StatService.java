public class StatService {
    private static String[] lettersGuessed = new String[26];
    private static String[] lettersPossible = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");

    public static void addToGuessed(String letter) {
        for (int i = 0; i < lettersPossible.length; i++) {
            if (letter.equalsIgnoreCase(lettersPossible[i]))
                lettersGuessed[i] = letter;
        }
    }

    public static boolean alreadyGuessed(String letter) {
        for (int i = 0; i < 26; i++) {
            if (letter.equalsIgnoreCase(lettersGuessed[i])) {
                return true;
            }
        }

        return false;
    }

    public static String[] getLettersGuessed() {
        return lettersGuessed;
    }

    public static String[] getLettersPossible() {
        return lettersPossible;
    }
}