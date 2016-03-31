import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.Consumer;

public class WordService implements Iterable<String> {
    Random randomGenerator = new Random();

    ArrayList<String> words;

    public WordService() {
        words = this.loadWords();
    }

    public String getLetter(String word) {
        int random = randomGenerator.nextInt(word.length());
        return word.substring(random, random+1);
    }

    public String getWord() {
        return words.get(randomGenerator.nextInt(this.size()));
    }

    public ArrayList<String> getWords() {
        return this.words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> loadWords() {
        ArrayList<String> incomingWords = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File("C:\\Users\\j.quick\\hangman\\resources\\words.txt"));
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            while (dis.available() != 0) {
                incomingWords.add(dis.readLine());
            }

            fis.close();
            bis.close();
            dis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return incomingWords;
    }

    public int size() {
        return words.size();
    }

    public static String[] toArray(String str) {
        String[] returnVar = new String[str.length()];
        for (int i = 0; i < str.length(); i++) {
            returnVar[i] = str.substring(i, i + 1);
        }

        return returnVar;
    }

    public static String toString(String[] array) {
        String returnVar = "";
        for (String letter : array) {
            returnVar += (letter != null) ? letter : "-";
        }

        return returnVar;
    }

    @Override
    public Iterator<String> iterator() {
        return words.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        words.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return words.spliterator();
    }
}