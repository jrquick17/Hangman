import java.util.ArrayList;

public class Opponent {
    WordService wordService;

    public Opponent() {
        wordService = new WordService();
    }

    public String getFreebie(String word) {
        return wordService.getLetter(word);
    }

    public double[] getLetterPercents() {
        double letterPercents[] = new double[26];
        for (int let = 0, num = 0; let < 26; let++, num = 0) {
            for (int i = 0; i < this.getWords().size(); i++) {
                if (this.getWords().get(i).contains(StatService.getLettersPossible()[let]))
                    num++;
            }

            letterPercents[let] = ((double)num/this.getWords().size()*100);
        }

        return letterPercents;
    }

    public ArrayList<String> getWords() {
        return wordService.getWords();
    }

    public void giveCurrentWord(String[] currentWord) {
        wordService.trimByCurrentWord(currentWord);
    }

    public void giveNotLetter(String currentWord) {
        wordService.trimByNotLetter(currentWord);
    }

    public void giveWordLength(int wordLength) {
        wordService.trimByLength(wordLength);
    }

    public String guessLetter() {
        String[] guessedLetters = StatService.getLettersGuessed();
        double[] letterPercent = this.getLetterPercents();

        String bestBet = "";
        for (double i = 0, high = Double.MIN_VALUE; i < letterPercent.length; i++) {
            if (guessedLetters[(int) i] == null) {
                if (letterPercent[(int) i] > high) {
                    bestBet = StatService.getLettersPossible()[(int) i];
                    high = letterPercent[(int) i];
                }
            }
        }

        System.out.println("How about '" + bestBet + "'?");

        return bestBet;
    }

    public String selectWord() {
        return wordService.getWord();
    }
}