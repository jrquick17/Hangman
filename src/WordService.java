import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class WordService implements Iterable<String> {
    ArrayList<String> words;

    public WordService() {
        words = this.loadWords();
    }

    public ArrayList<String> loadWords() {
        ArrayList<String> incomingWords = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File("C:\\xampp\\htdocs\\Encounting Software\\jrq\\hangman\\resources\\words.txt"));
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