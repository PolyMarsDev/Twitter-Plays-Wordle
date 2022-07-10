import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameManager
{
	private static final Map<Character, String> LETTER_TO_EMOJI = new HashMap<>()
	{{
		//populate letter to emoji hashmap
		this.put('a', "\uD83C\uDDE6");
		this.put('b', "\uD83C\uDDE7");
		this.put('c', "\uD83C\uDDE8");
		this.put('d', "\uD83C\uDDE9");
		this.put('e', "\uD83C\uDDEA");
		this.put('f', "\uD83C\uDDEB");
		this.put('g', "\uD83C\uDDEC");
		this.put('h', "\uD83C\uDDED");
		this.put('i', "\uD83C\uDDEE");
		this.put('j', "\uD83C\uDDEF");
		this.put('k', "\uD83C\uDDF0");
		this.put('l', "\uD83C\uDDF1");
		this.put('m', "\uD83C\uDDF2");
		this.put('n', "\uD83C\uDDF3");
		this.put('o', "\uD83C\uDDF4");
		this.put('p', "\uD83C\uDDF5");
		this.put('q', "\uD83C\uDDF6");
		this.put('r', "\uD83C\uDDF7");
		this.put('s', "\uD83C\uDDF8");
		this.put('t', "\uD83C\uDDF9");
		this.put('u', "\uD83C\uDDFA");
		this.put('v', "\uD83C\uDDFB");
		this.put('w', "\uD83C\uDDFC");
		this.put('x', "\uD83C\uDDFD");
		this.put('y', "\uD83C\uDDFE");
		this.put('z', "\uD83C\uDDFF");
	}};

	private static final Random RAND = new Random(System.currentTimeMillis());

	private String word;
	private int end = 0;

	private List<String> guesses = new ArrayList<>();

	private int round;

	private int currentTurn;

	public GameManager()
	{
		//pick a random word
		this.word = this.getWord();
		this.round = 6;
		this.currentTurn = 1;
	}

	public String display()
	{
		StringBuilder output = new StringBuilder("Wordle #" + this.round + " " + this.currentTurn + "/6\n\n");
		for(String s : this.guesses)
			output.append(this.wordToEmoji(s)).append("\n").append(this.getMatch(s, this.word)).append("\n");
		return output.toString();
	}

	public String run(String input)
	{
		System.out.println(this.word);
		if(this.end > 0)
		{
			this.end = 0;
			this.round++;
			//generate new word, reset guess list, etc
			this.word = this.getWord();
			this.guesses = new ArrayList<>();
			this.currentTurn = 1;
		}

		this.guesses.add(input.equals("skip") ? this.guesses.get(this.guesses.size() - 1) : input);
		this.currentTurn++;

		if(input.equals(this.word))
		{
			this.currentTurn -= 1;
			this.end = 1;
			return this.display() + "\nYou win!";
		}
		else if(this.currentTurn > 6)
		{
			this.end = 2;
			return this.display() + "\nThe word was " + this.word + ".";
		}

		return this.display() + "\nReply with a 5-letter guess!";
	}

	public String getWord()
	{
		List<String> words = getWordList();
		if(words.size() == 0)
			return "";
		return words.get(RAND.nextInt(words.size()));
	}

	public static List<String> getWordList()
	{
		List<String> words = new ArrayList<>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
			String line = reader.readLine();
			while(line != null)
			{
				Collections.addAll(words, line.split(" "));
				line = reader.readLine();
			}
		} catch(Exception e)
		{
			System.out.println("Cannot access file");
		}
		return words;
	}

	public String wordToEmoji(String word)
	{
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < word.length(); i++)
			result.append(LETTER_TO_EMOJI.get(word.charAt(i))).append(" ");
		return result.toString();
	}

	public String getMatch(String guess, String word)
	{
		String[] match = new String[5];
		StringBuilder tempGuess = new StringBuilder();
		for(int i = 0; i < guess.length(); i++)
		{
			if(guess.charAt(i) == word.charAt(i))
			{
				match[i] = "\uD83D\uDFE9";
				tempGuess.append("0");
			}
			else
			{
				match[i] = "⬛";
				tempGuess.append(guess.charAt(i));
			}
		}

		for(int i = 0; i < tempGuess.length(); i++)
			if(word.contains(String.valueOf(tempGuess.charAt(i))) && match[i].equals("⬛"))
				match[i] = "\uD83D\uDFE8";

		StringBuilder matchString = new StringBuilder();
		for(String s : match)
			matchString.append(s).append(" ");
		return matchString.toString();
	}
}
