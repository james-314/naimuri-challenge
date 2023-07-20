import static org.junit.Assert.*;

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
}
