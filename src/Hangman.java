import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
	static File file = new File("C:\\Users\\j.quick\\hangman\\resources\\words.txt");
	static FileInputStream fis = null;
	static BufferedInputStream bis = null;
	static DataInputStream dis = null;
	static LinkedList<String> wordList;
	static String answered, guessed[] = new String[26], letter, currentWord[],
	posLetters[] = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
	static boolean isComplete = false, multLetter, struckOut = false;
	static int placement, strikes;
	static double perLetters[] = new double[26];

    Random gen = new Random();
    Scanner scan = new Scanner(System.in);

    public Hangman() {
        Opponent opponent = new Opponent();
    }

	public String findNextGuess(boolean show) {
		if (show) {
            System.out.println("Letter\tChance");
        }
		for (int let = 0, num = 0; let < 26; let++, num = 0)
		{
			for (int i = 0; i < wordList.size(); i++)
			{
				if (wordList.get(i).contains(posLetters[let]))
					num++;
			}

			perLetters[let] = ((double)num/wordList.size()*100);

			if (show)
				System.out.println(posLetters[let] + ":\t" + perLetters[let]);
		}

		if (!show)
		{
			double maxAmt = -1;
			int maxInc = 0; 

			for (int i = 0; i < 26; i++)
			{
				if (maxAmt < perLetters[i] && guessed[i] == null)
				{
					maxAmt = perLetters[i];
					maxInc = i;
				}
			}

			guessed[maxInc] = posLetters[maxInc];
			System.out.println("I guess the letter " + posLetters[maxInc]);

			return posLetters[maxInc];
		}
		else
			return null;
	}

	public LinkedList<String> removeByLength(LinkedList<String> wordList, int length)
	{
		for (int i = 0; i < wordList.size(); i++)
		{
			if (wordList.get(i).length() > length || wordList.get(i).length() < length)
			{
				wordList.remove(i);
				i--;
			}
		}

		return wordList;
	}

	public LinkedList<String> removeByCorrectLetter(LinkedList<String> wordList, String letter, int placement)
	{
		for (int i = 0; i < wordList.size(); i++)
		{
			if (!wordList.get(i).substring(placement, placement+1).contains(letter))
			{
				wordList.remove(i);
				i--;
			}
		}

		return wordList;
	}

	public void printCurrentWord() {
        for (String currentLetter : currentWord) {
            if (currentLetter != null) {
                System.out.println(currentLetter);
            } else {
                System.out.println("-");
            }
        }
	}

	public LinkedList<String> removeByWrongLetter(LinkedList<String> wordList, String letter)
	{
		for (int i = 0; i < wordList.size(); i++)
		{
			if (wordList.get(i).contains(letter))
			{
				wordList.remove(i);
				i--;
			}
		}

		return wordList;
	}

	public boolean alreadyGuessed() {
		for (int i = 0; i < 26; i++) {
            if (letter.equalsIgnoreCase(guessed[i])) {
                return true;
            }
        }

		return false;
	}

	public void addToGuessed(String letter)
	{
		for (int i = 0; i < posLetters.length; i++)
		{
			if (letter.equalsIgnoreCase(posLetters[i]))
				guessed[i] = letter;
		}
	}

	public void help() {
		System.out.println("How long is the word? ");
		currentWord = new String[scan.nextInt()];
		wordList = removeByLength(wordList, currentWord.length);

		System.out.println("Enter the known letter: ");
		letter = scan.next();
		System.out.println("Enter the placement: ");
		placement = scan.nextInt();
		currentWord[placement] = letter;
		addToGuessed(letter);

		wordList = removeByCorrectLetter(wordList, letter, placement);

		while (!isComplete) {
			printCurrentWord();

			findNextGuess(true);

			System.out.println("Did your guess work? ");
			answered = scan.next();
			if (answered.equalsIgnoreCase("No"))
			{
				System.out.println("What letter did you guess? ");
				letter = scan.next();
				wordList = removeByWrongLetter(wordList, letter);
			}
			else if (answered.equalsIgnoreCase("Yes"))
			{
				System.out.println("Enter the known letter: ");
				letter = scan.next();
				System.out.println("Enter the placement: ");
				placement = scan.nextInt();
				currentWord[placement] = letter;

				wordList = removeByCorrectLetter(wordList, letter, placement);
			}
			else if (answered.equalsIgnoreCase("Show"))
			{
				for (int i = 0; i < wordList.size(); i++)
				{
					System.out.println(wordList.get(i));
				}
			}

			isComplete = checkIfComplete();
		}

		System.out.println("Thank me later.");
	}

	private void create() {
		System.out.println("How many letters are in your word? ");
		currentWord = new String[scan.nextInt()];
		wordList = removeByLength(wordList, currentWord.length);

		System.out.println("And what letter are you giving me? ");
		letter = scan.next();
		if (!letter.equalsIgnoreCase("None"))
		{
			System.out.println("Where is the letter located? ");
			placement = scan.nextInt();
			wordList = removeByCorrectLetter(wordList, letter, placement);
			addToGuessed(letter);
			currentWord[placement] = letter;
		}

		while (!isComplete && !struckOut)
		{
			printCurrentWord();

			letter = findNextGuess(false);

			System.out.println("Was I correct? ");
			answered = scan.next();

			if (answered.equalsIgnoreCase("Yes"))
			{
				multLetter = true;
				while (multLetter)
				{
					System.out.println("Where is the letter located? ");
					placement = scan.nextInt();
					wordList = removeByCorrectLetter(wordList, letter, placement);
					currentWord[placement] = letter;

					if (!checkIfComplete())
					{
						System.out.println("Is there multiple places for that letter? ");
						answered = scan.next();
						if (answered.equalsIgnoreCase("no"))
							multLetter = false;
					}
					else
					{
						multLetter = false;
					}
				}
			}
			else if (answered.equalsIgnoreCase("no"))
			{
				wordList = removeByWrongLetter(wordList, letter);
				struckOut = addToStrikes();
			}
			else
				System.out.println("Screwing up, eh?");

			isComplete = checkIfComplete();
		}

		if (isComplete)
			System.out.println("Suck on that fool!");
		else if (struckOut)
			System.out.println("I'm not sure how, but we both know you're a cheating bastard!");
	}

	public boolean checkIfComplete() {
		for (int i = 0; i < currentWord.length; i++) {
			if (currentWord[i] == null) {
                return false;
            }
		}

		return true;
	}

	public boolean addToStrikes() {
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

	public void play() {
		String answer[];

		String word = getWord(gen.nextInt(wordList.size()));
		currentWord = new String[word.length()];
		answer = new String[word.length()];

		for (int i = 0; i < word.length(); i++)
			answer[i] = word.substring(i, i+1);

		System.out.println("Bet you can't guess my word, it's " + currentWord.length + " characters " +
		"long.\nI'll even give you a letter.");
		int given = gen.nextInt(currentWord.length);
		currentWord[given] = answer[given];

		while (!isComplete && !struckOut) {
			this.printCurrentWord();
			System.out.println("What letter would you like to guess? ");
			letter = scan.next();

			if (!this.alreadyGuessed()) {
				for (int i = 0, count = 0; i < answer.length; i++) {
					if (answer[i].equalsIgnoreCase(letter)) {
						currentWord[i] = answer[i];
						System.out.println("Lucky guess!");
					} else {
						count++;
						if (count == answer.length)
							struckOut = addToStrikes();
					}
				}

                addToGuessed(letter);
                isComplete = checkIfComplete();
			}
			else {
				System.out.println("Cute, but you already guessed that.");
			}
		}

		if (isComplete)
			System.out.println("You must have cheated!");
		else if (struckOut)
			System.out.println("Didn't really expect much more from you honestly." +
					"\nThe word was " + showAnswer(answer));
	}

	public String showAnswer(String[] answer) {
		String str = "";
		
		for (int i = 0; i < answer.length; i++)
			str += answer[i];
		
		return str;
	}

	public String getWord(int i) {
		System.out.println(i);
		int count = 0;

		try 
		{
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			while (dis.available() != 0) {
				if (i == count) {
					return (dis.readLine());
				}
				dis.readLine();
				count++;
			}

			fis.close();
			bis.close();
			dis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

    public static void main(String[]args) {
        Hangman hangman = new Hangman();
        Scanner scan = new Scanner(System.in);
        String option;

        System.out.println("What would you like to do? ");
        System.out.println("Type 'help' for help solving.");
        System.out.println("Type 'play' to try and guess a word.");
        System.out.println("Type 'create' for me to guess your word.");

        option = scan.next();

        switch (scan.next()) {
            case "help":
                hangman.help();
                break;
            case "play":
                hangman.play();
                break;
            case "create":
                hangman.create();
                break;
            default:
                System.out.println("You messed up dumbass!");
        }
    }
}
