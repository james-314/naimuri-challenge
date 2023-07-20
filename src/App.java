import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the arguments passed to the program.
 */
class ProgramArgs {
    // The number of command line arguments that are expected.
    private static int expectedNumberOfArgs = 2;
    // The size n of the n*n square.
    private final int n;
    // The letters to be used in the square.
    private final String letters;
    // Only the unique letters in the input.
    private final String uniqueLetters;
    // Are the program arguments valid?
    private boolean valid = true;

    public int getN() { return this.n; }
    public String getLetters() { return this.letters; }
    public String getUniqueLetters() { return this.uniqueLetters; }
    public boolean getValid() { return this.valid; }

    // The parsing etc. should probs be moved out of here
    public ProgramArgs(String[] args) {
        valid = checkLength(args);

        int temp; // avoids moaning about possible final assignment
        try {
            temp = Integer.parseInt(args[0]);
        } catch (Exception e) {
            temp = 0;
            valid = false;
        }

        n = temp;
        letters = args[1];

        valid = validateLetters(n, letters) && valid; // both need to be the same

        // Find the unique letters
        String tempString = "";

        for (int i = 0; i < letters.length(); i++) {
            if (tempString.indexOf(letters.charAt(i)) == -1) {
                tempString += letters.charAt(i);
            }
        }

        uniqueLetters = tempString;
    }

    /**
     * Checks that the number of arguments given is suitable.
     * @param args the command line arguments of the program
     * @return true if the length of the arguments is as expected, false otherwise.
     */
    private boolean checkLength(String[] args) {
        if (args.length < expectedNumberOfArgs || args.length > expectedNumberOfArgs) {
            System.out.println(String.format("Unexpected arguments given. Expected %d but got %d", expectedNumberOfArgs, args.length));
            return false;
        } 
        return true;
    }

    /**
     * Checks that the provided letters are suitable. They should all be lower case and a-z only.
     * @param n the size of each side of the word square
     * @param letterString the letters provided that should be used to form the word square.
     * @return true if there are a sufficient amount of letters and they are all legal, false otherwise.
     */
    private boolean validateLetters(int n, String letterString) {
        if (letterString.length() != n * n) {
            System.out.println(String.format("There are %d letters but %d are required to complete the square given the value of n = %d", letterString.length(), n*n, n));
            return false;
        }
        for (int i = 0; i < letterString.length(); i++) {
            if (!Character.isLowerCase(letterString.charAt(i))) {
                System.out.println("The letters contain a non-lower case character: " + letterString.charAt(i));
                return false;
            }
        }
        return true;
    }
}

/**
 * This is a helper for the tracking the letters that are allowed to be used for a word square. Records the maximum of each letter allowed and how many are currently used in a trial.
 */
class LetterCounter {
    private int[] maximums;
    private int[] used;

    public LetterCounter(String letters) {

        this.maximums = new int[26];
        this.used = new int[26];

        for (int i = 0; i < letters.length(); i++) {
            this.maximums[letters.charAt(i) - 'a'] += 1;
        }

        for (int i = 0; i < used.length; i++) {
            used[i] = 0;
        }
    }

    public LetterCounter(LetterCounter otherCounter) { // Easier than messing around with clone override for a trivial case
        this.maximums = new int[26];
        this.used = new int[26];

        for (int i = 0; i < maximums.length; i++) {
            this.maximums[i] = otherCounter.maximums[i];
            this.used[i] = otherCounter.used[i];
        }
    }
 
    /**
     * Apply an entire word the counter. Prevents having to use single letters. Also confirms if there is sufficient space for the word.
     * @param s the word to apply and check validity.
     * @return true if the word can be used i.e. enough letters, false otherwise.
     */
    public boolean incrementWithWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (isRoom(s.charAt(i))) {
                increment(s.charAt(i));
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Increments the count of a given letter.
     * @param c the letter you one to use.
     */
    public void increment(char c) {
        this.used[c - 'a'] += 1;
    }

    /**
     * Checks if there is space for a given letter in the counter.
     * @param c the letter to check for.
     * @return true if there is space, false otherwise.
     */
    public boolean isRoom(char c) {
        return this.used[c - 'a'] < this.maximums[c - 'a'];
    }

    /**
     * Resets the used letters to 0. Useful instead of reinitialising the obj.
     */
    public void reset() {
        for (int i = 0; i < used.length; i++) {
            this.used[i] = 0;
        }
    }
}

public class App {
    /**
     * Checks if a string contains only the given characters.
     * @param wordToCheck the string that is to be checked for its contents.
     * @param charsMustContain the string of characters that must be found.
     * @return true if the wordTocheck only contains the characters in charsMustContain, false otherwise.
     */
    public static boolean stringContainsSomeButNotOthers(String wordToCheck, String charsMustContain) {
        for (int i = 0; i < 26; i++) { // each letter in alphabet
            int character = i + 'a'; // char offset
            if (wordToCheck.indexOf(character) != -1 && charsMustContain.indexOf(character) == -1) { // character exists in word
                return false;
            }
        }
        return true;
    }

    /**
     * Loads the dictionary of words provided in the exercise and preprocesses them to only contain the appropriate length and letters. TODO this should be separated into separate functions. One for actually loading and another for filtering. TODO could also rule out words that use too many of a given letter.
     * @param n the size of one side of the word square.
     * @param letters the letters that can be used. this can be provided as a unique list as it is only used for filtering out invalid words.
     * @return a list of words that meet the length criteria and only contain the given letters.
     */
    public static List<String> loadWordList(int n, String letters) { // individual arguments for decoupled testing
        ArrayList<String> wordList = new ArrayList<>();
        InputStream is = App.class.getResourceAsStream("resources/words.txt");
        if (is == null) {
            System.out.println("Cannot find resource file!");
            System.exit(1);
        }
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        try {
            String line;            

			while (reader.ready()) {
                line = reader.readLine().trim(); // could be extra stuff at the end
                
                if (line.length() == n && stringContainsSomeButNotOthers(line, letters)) {
                    wordList.add(line);
                }
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println(String.format("%d words loaded", wordList.size()));

        return wordList;
    }

    /**
     * A helper to clone Lists.
     * @param list the list to be cloned.
     * @return a cloned instance of the input.
     */
    public static ArrayList<String> cloneList(List<String> list) {
        ArrayList<String> clone = new ArrayList<String>(list.size());
        for (String item : list) clone.add(String.valueOf(item));
        return clone;
    }

    /**
     * The main useful function. Performs a recursive search of the allowed words to try and form a result set. The algorithm is is effectively an inefficient dfs with a tree structure.
     * @param word the intial word of the word square.
     * @param prefix the current prefix that any valid word must have.
     * @param currentN the depth of the search (i.e. how many letters in are we)
     * @param wordList the list of allowed words that can be used.
     * @param foundWords the list of words that are candidates for the current words square under construction.
     * @param counter the letter counter tracking which letter have been used.
     * @param results the list of results (valid word squares).
     */
    public static void findNextWords(String word, String prefix, int currentN, List<String> wordList, List<String> foundWords, LetterCounter counter, ArrayList<ArrayList<String>> results) {
        // Check if it should be done or not
        if (foundWords.size() == word.length()) { // TODO do I actually need this?
            return;
        }

        String newPrefix = "";
        // Generate the new prefix
        for (int i = 0; i < foundWords.size(); i++) {
            newPrefix += foundWords.get(i).substring(currentN, currentN + 1);
        }
        // Search for the next words
        for (int i = 0; i < wordList.size(); i++) { // TODO use a binary search or something to find the start instead of scanning all words.
            if (wordList.get(i).startsWith(newPrefix)) {
                // check that there is space for it
                LetterCounter newCounter = new LetterCounter(counter);
                if (!newCounter.incrementWithWord(wordList.get(i))) { // there are insufficient letters available for the new word.
                    continue;
                }
                // trial the new word
                foundWords.add(wordList.get(i));
                findNextWords(word, newPrefix, currentN + 1, wordList, foundWords, newCounter, results);
                // if successful then the list of found words should be complete
                if (foundWords.size() == word.length()) { // a complete set of words has been found
                    results.add(cloneList(foundWords));
                    
                }
                foundWords.remove(foundWords.size() - 1); // removes invalid word or allows continuation of a sibling set.
            }
        }
    }

    /**
     * Provides setup for the recursive search.
     * @param initialWord the word that forms the start of the candidate word square/s.
     * @param wordList list of allowed words for the word square.
     * @param counter the letter counter tracking which letter can be used for new words.
     * @return a set of word square that are valid, if any.
     */
    public static ArrayList<ArrayList<String>> findWords(String initialWord, List<String> wordList, LetterCounter counter) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        String prefix = initialWord.substring(0, 1);
        ArrayList<String> foundWords = new ArrayList<>(initialWord.length());
        foundWords.add(initialWord);
        counter.incrementWithWord(initialWord);
        findNextWords(initialWord, prefix, 1, wordList, foundWords, counter, results);
        return results;
    }

    /**
     * Helper to nicely output the results.
     * @param results sets of results to be outputted.
     */
    public static void outputResults(ArrayList<ArrayList<String>> results) {
        if (results == null || results.size() == 0) {
            System.out.println("There were no word squares found.");
        } else if (results.size() == 1) {
            System.out.println("There was 1 word square found.");
            for (String word : results.get(0)) {
                System.out.println(word);
            }
        } else { // multiple results
            System.out.println(String.format("There were %d word squares found.", results.size()));
            for (int index = 0; index < results.size(); index++) {
                System.out.println(String.format("Word square %d:", index + 1));
                for (String word : results.get(index)) {
                System.out.println(word);
            }
            }
        }
    }


    public static void main(String[] args) {
        
        // construct arguments and check valid
        ProgramArgs programArgs = new ProgramArgs(args);
        if (!programArgs.getValid()) { // some problem has appeared that means we can't continue
            System.exit(1);
        }

        // read in the word list
        List<String> filteredWords = loadWordList(programArgs.getN(), programArgs.getUniqueLetters());

        // letters used
        LetterCounter counter = new LetterCounter(programArgs.getLetters());

        // solve the problem
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (String word : filteredWords) {
            results.addAll(findWords(word, filteredWords, counter));
            counter.reset(); // ready for next word
        }
        
        outputResults(results);
    }
}
