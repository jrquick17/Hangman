import java.util.ArrayList;

public class Opponent {
    WordService wordService;

    public Opponent() {
        wordService = new WordService();
    }

    public String getFreebie(String word) {
        return wordService.getLetter(word);
    }

    public String guessLetter(String board) {
        String letters, words[] = new String[58108];
        double strength[] = new double[words.length];

        for (String word : wordService) {
            for (int i = 0; i < word.length(); i++) {
                String letter = word.substring(i, i+1);
            }
        }

        return "";
    }

    public ArrayList<String> getWords() {
        return wordService.getWords();
    }

    public String selectWord() {
        return wordService.getWord();
    }

    public void setWords(ArrayList<String> words) {
        this.wordService.setWords(words);
    }

    public void giveWordLength(int wordLength) {
        wordService.trimByLength(wordLength);
    }

    public void giveCurrentWord(String[] currentWord) {
        wordService.trimByCurrentWord(currentWord);
    }
}