import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AppTest {
    
    /*
     * The argument n is compatible with the length of letters.
     */
    @Test
    public void Args_Length_Match() {
        ProgramArgs args = new ProgramArgs(new String[]{"2", "abcd"});
        assertTrue(args.getValid());
    }

    /*
     * The argument n does not match with the given number of letters.
     */
    @Test
    public void Args_Length_Mismatch() {
        ProgramArgs args = new ProgramArgs(new String[]{"2", "abcde"});
        assertFalse(args.getValid());
    }

    /*
     * The letters contain an illegal capital letter.
     */
    @Test
    public void Letters_Contain_Capital() {
        ProgramArgs args = new ProgramArgs(new String[]{"2", "aBcd"});
        assertFalse(args.getValid());
    }

    /*
     * The letters contain an illegal special character.
     */
    @Test
    public void Letters_Contain_Special() {
        ProgramArgs args = new ProgramArgs(new String[]{"2", "ab#d"});
        assertFalse(args.getValid());
    }

    /*
     * Determine of the length filter works correctly.
     */
    @Test
    public void Count_4_Letter_Words(){
        List<String> filteredWords = App.loadWordList(4, "abcdefghijklmnopqrstuvwxyz");
        System.out.println(String.format("There are %d words of length %d", filteredWords.size(), 4));
        assertEquals(3903, filteredWords.size());
    }

    @Test
    public void Find_Words() {
        LetterCounter counter = new LetterCounter("eeeeddoonnnsssrv");
        ArrayList<String> wordList = new ArrayList<String>() {
            { 
                add("rose");
                add("oven");
                add("send");
                add("ends");
            }
        };
        ArrayList<ArrayList<String>> results = App.findWords("rose", wordList, counter);
        assertEquals(4, results.get(0).size());
    }

    @Test
    public void Example_4() {
        List<String> filteredWords = App.loadWordList(4, "acdemno");

        ArrayList<String> wordSqaure = new ArrayList<>();
        LetterCounter counter = new LetterCounter("aaccdeeeemmnnnoo");
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (String word : filteredWords) {
            wordSqaure.clear();
            counter.reset();
            results.addAll(App.findWords(word, filteredWords, counter));
        }
        
        ArrayList<String> answers = new ArrayList<String>() {
            { 
                add("moan");
                add("once");
                add("acme");
                add("need");
            }
        };

        for (int i = 0; i < results.size(); i++) {
            if (answers.equals(results.get(i))) {
                assertEquals(answers, results.get(i));
            }
        }
    }

        @Test
    public void Example_5() {
        List<String> filteredWords = App.loadWordList(5, "aefhmonsrtw");

        ArrayList<String> wordSqaure = new ArrayList<>();
        LetterCounter counter = new LetterCounter("aaaeeeefhhmoonssrrrrttttw");
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (String word : filteredWords) {
            wordSqaure.clear();
            counter.reset();
            results.addAll(App.findWords(word, filteredWords, counter));
        }
        
        ArrayList<String> answers = new ArrayList<String>() {
            { 
                add("feast");
                add("earth");
                add("armor");
                add("stone");
                add("threw");
            }
        };

        for (int i = 0; i < results.size(); i++) {
            if (answers.equals(results.get(i))) {
                assertEquals(answers, results.get(i));
            }
        }
    }

        @Test
    public void Example_55() {
        List<String> filteredWords = App.loadWordList(5, "abehmosrutv");

        ArrayList<String> wordSqaure = new ArrayList<>();
        LetterCounter counter = new LetterCounter("aabbeeeeeeeehmosrrrruttvv");
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (String word : filteredWords) {
            wordSqaure.clear();
            counter.reset();
            results.addAll(App.findWords(word, filteredWords, counter));
        }
        
        ArrayList<String> answers = new ArrayList<String>() {
            { 
                add("heart");
                add("ember");
                add("above");
                add("revue");
                add("trees");
            }
        };

        for (int i = 0; i < results.size(); i++) {
            if (answers.equals(results.get(i))) {
                assertEquals(answers, results.get(i));
            }
        }
    }

        @Test
    public void Example_7() {
        List<String> filteredWords = App.loadWordList(7, "abedgmlonsruvy");

        ArrayList<String> wordSqaure = new ArrayList<>();
        LetterCounter counter = new LetterCounter("aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy");
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (String word : filteredWords) {
            wordSqaure.clear();
            counter.reset();
            results.addAll(App.findWords(word, filteredWords, counter));
            if (wordSqaure.size() == 7) {
                break;
            }
        }
        
        ArrayList<String> answers = new ArrayList<String>() {
            { 
                add("bravado");
                add("renamed");
                add("analogy");
                add("valuers");
                add("amoebas");
                add("degrade");
                add("odyssey");
            }
        };

        for (int i = 0; i < results.size(); i++) {
            if (answers.equals(results.get(i))) {
                assertEquals(answers, results.get(i));
            }
        }
    }

        @Test
    public void Example_1() {
        List<String> filteredWords = App.loadWordList(4, "acdemno");

        ArrayList<String> wordSqaure = new ArrayList<>();
        LetterCounter counter = new LetterCounter("aaccdeeeemmnnnoo");
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (String word : filteredWords) {
            wordSqaure.clear();
            counter.reset();
            results.addAll(App.findWords(word, filteredWords, counter));
            if (wordSqaure.size() == 4) {
                break;
            }
        }
        
        ArrayList<String> answers = new ArrayList<String>() {
            { 
                add("moan");
                add("once");
                add("acme");
                add("need");
            }
        };

        for (int i = 0; i < results.size(); i++) {
            if (answers.equals(results.get(i))) {
                assertEquals(answers, results.get(i));
            }
        }
    }
}
