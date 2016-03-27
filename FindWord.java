import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class FindWord 
{
	public static void main(String[]args)
	{
		Scanner scan = new Scanner(System.in);
		String letters, words[] = new String[58108];
		double strength[] = new double[words.length];

		try 
		{
			FileInputStream fis = new FileInputStream(new File("C:/Users/jrquick/Desktop/Programs/Hangman/words.txt"));
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
			int cnt = 0;

			while (dis.available() != 0) 
			{
				words[cnt] = dis.readLine();
				cnt++;
			}

			fis.close();
			bis.close();
			dis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("What letters do you have?" );
		letters = scan.nextLine();

		String rem = letters;
		for (int word = 0; word < words.length; word++)
		{
			letters = rem;

			for (int let = 0; let < words[word].length(); let++)
			{
				if (letters.contains("" + words[word].charAt(let)))
				{
					letters = letters.replaceFirst(""+words[word].charAt(let), "");
					strength[word]++;
				}
				else if (letters.contains("-"))
				{
					letters = letters.replaceFirst("-", "");
					strength[word]++;
				}
				else
				{
					//System.out.println(words[word] + "\t" + words[word].charAt(let));
					strength[word] = 0;
					let = 500;
				}
			}
		}

		double max = 0;
		for (int word = 0, best = 0; word < 50; word++, max = 0)
		{
			for (int i = 0; i < words.length; i++)
			{
				if (strength[i] > max)
				{
					best = i;
					max = strength[i];
				}
			}

			if (strength[best] > 0)
				System.out.println(words[best]);

			strength[best] = 0;
		}
	}
}
