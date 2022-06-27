//import Game.*;
import Util.Randomizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameManager
{
    private String word;
    int end = 0;

    HashMap<Character, String> letterToEmoji;

    List<String> guesses;

    int round;

    int currentTurn;

    public GameManager()
    {
       //pick a random word
        word = getWord();

        guesses = new ArrayList<String>();

        round = 6;

        currentTurn = 1;

        //populate letter to emoji hashmap
        letterToEmoji = new HashMap<Character, String>();
        letterToEmoji.put('a', "\uD83C\uDDE6");
        letterToEmoji.put('b', "\uD83C\uDDE7");
        letterToEmoji.put('c', "\uD83C\uDDE8");
        letterToEmoji.put('d', "\uD83C\uDDE9");
        letterToEmoji.put('e', "\uD83C\uDDEA");
        letterToEmoji.put('f', "\uD83C\uDDEB");
        letterToEmoji.put('g', "\uD83C\uDDEC");
        letterToEmoji.put('h', "\uD83C\uDDED");
        letterToEmoji.put('i', "\uD83C\uDDEE");
        letterToEmoji.put('j', "\uD83C\uDDEF");
        letterToEmoji.put('k', "\uD83C\uDDF0");
        letterToEmoji.put('l', "\uD83C\uDDF1");
        letterToEmoji.put('m', "\uD83C\uDDF2");
        letterToEmoji.put('n', "\uD83C\uDDF3");
        letterToEmoji.put('o', "\uD83C\uDDF4");
        letterToEmoji.put('p', "\uD83C\uDDF5");
        letterToEmoji.put('q', "\uD83C\uDDF6");
        letterToEmoji.put('r', "\uD83C\uDDF7");
        letterToEmoji.put('s', "\uD83C\uDDF8");
        letterToEmoji.put('t', "\uD83C\uDDF9");
        letterToEmoji.put('u', "\uD83C\uDDFA");
        letterToEmoji.put('v', "\uD83C\uDDFB");
        letterToEmoji.put('w', "\uD83C\uDDFC");
        letterToEmoji.put('x', "\uD83C\uDDFD");
        letterToEmoji.put('y', "\uD83C\uDDFE");
        letterToEmoji.put('z', "\uD83C\uDDFF");
    }

    public String display()
    {
        String output = "Wordle #" + String.valueOf(round) + " " + currentTurn + "/6\n\n";
        for (String s : guesses)
        {
            output += wordToEmoji(s) + "\n" + getMatch(s, word) + "\n";
        }
        return output;
    }
    public String run(String input)
    {
        System.out.println(word);
        if (end > 0)
        {
            end = 0;
            round++;
            //generate new word, reset guess list, etc
            word = getWord();
            guesses = new ArrayList<String>();
            currentTurn = 1;
        }
        if (!input.equals("skip")) {
            guesses.add(input);
            currentTurn++;
        }
        else
        {
            guesses.add(guesses.get(guesses.size() - 1));
            currentTurn++;
        }

        if (input.equals(word))
        {
            currentTurn -= 1;
            end = 1;
            return display() + "\nYou win!";
        }
        else if (currentTurn > 6)
        {
            end = 2;
            return display() + "\nThe word was " + word + ".";
        }

        return display() + "\nReply with a 5-letter guess!";
    }

    public String getWord()
    {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
            String line = reader.readLine();
            List<String> words = new ArrayList<String>();
            while(line != null) {
                String[] wordsLine = line.split(" ");
                for(String word : wordsLine) {
                    words.add(word);
                }
                line = reader.readLine();
            }

            Random rand = new Random(System.currentTimeMillis());
            String randomWord = words.get(rand.nextInt(words.size()));
            return randomWord;
        } catch (Exception e) {
            System.out.println("Cannot access file");
        }
        return "";
    }

    public static List<String> getWordList()
    {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
            String line = reader.readLine();
            List<String> words = new ArrayList<String>();
            while(line != null) {
                String[] wordsLine = line.split(" ");
                for(String word : wordsLine) {
                    words.add(word);
                }
                line = reader.readLine();
            }
            return words;
        } catch (Exception e) {
            System.out.println("Cannot access file");
        }
        return null;
    }

    public String wordToEmoji(String word)
    {
        String result = "";
        for (int i = 0; i < word.length(); i++)
        {
            result += letterToEmoji.get(word.charAt(i)) + " ";
        }
        return result;
    }

    public String getMatch(String guess, String word)
    {
        String[] match = new String[5];
        String tempGuess = "";
        for (int i = 0; i < guess.length(); i++)
        {
            if (guess.charAt(i) == word.charAt(i))
            {
                match[i] = "\uD83D\uDFE9";
                tempGuess += "0";
            }
            else
            {
                match[i] = "⬛";
                tempGuess += guess.charAt(i);
            }
        }
        for (int i = 0; i < tempGuess.length(); i++)
        {
            if (word.contains(String.valueOf(tempGuess.charAt(i))))
            {
                if (match[i] == "⬛")
                {
                    match[i] = "\uD83D\uDFE8";
                }
            }
        }
        String matchString = "";
        for (String s : match) matchString += s + " ";
        return matchString;
    }
}
