class ProgramArgs {
    // The number of command line arguments that are expected.
    private static int expectedNumberOfArgs = 2;
    // The size n of the n*n square.
    private final int n;
    // The letters to be used in the square.
    private final String letters;
    // Are the program arguments valid?
    private boolean valid = true;

    public int getN() { return this.n; }
    public String getLetters() { return this.letters; }
    public boolean getValid() { return this.valid; }

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

        valid = validateLetters(expectedNumberOfArgs, letters) && valid; // both need to be the same
    }

    /*
     * Checks that the number of arguments given is suitable.
     */
    private boolean checkLength(String[] args) {
        if (args.length < expectedNumberOfArgs || args.length > expectedNumberOfArgs) {
            System.out.println(String.format("Unexpected arguments given. Expected %d but got %d", expectedNumberOfArgs, args.length));
            return false;
        } 
        return true;
    }

    /*
     * Checks that the provided letters are suitable. They should all be lower case and a-z only.
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

public class App {
    public static void main(String[] args) {
        
        // construct arguments and check valid
        ProgramArgs programArgs = new ProgramArgs(args);
        if (!programArgs.getValid()) {
            System.exit(1);
        }

        // read in the word list

        System.out.println("Hello, World!");
    }
}
