import java.util.ArrayList;
import java.util.Collections;

public class Opponent {
    WordService wordService;
    ArrayList<String> letters;

    public Opponent() {
        letters = new ArrayList<>();
        String[] lettersArray = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
        Collections.addAll(letters, lettersArray);

        wordService = new WordService();
    }

    public String guessLetter(String board) {
        String letters, words[] = new String[58108];
        double strength[] = new double[words.length];

        for (String word : wordService) {
            for (int i = 1; i <= word.length(); i++) {
                String letter = word.substring(0, i);
            }
        }

        return "";
    }
}