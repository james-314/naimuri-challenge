## How to run

### Compile (Windows)
(install required dependencies - see below)
```
javac -d .\bin src\*.java -cp "src\resources\words.txt;lib\hamcrest-core-1.3.jar;lib\junit-4.13.2.jar;"
```

### Run
An example of how to run the program:
```
java -cp "bin;bin\resources\words.txt;" App 4 eeeeddoonnnsssrv
```

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Dependencies

- hamcrest-core
- junit-4

## First thoughts
- number of letters need to be n*n
- letters should only contain lower case
- no special chars or uppercase
- is there always a unique solution? answer found later is no
- is there always even a solution? for given case obviously but in general?
- if there is a unique letter then it must be on the main diagonal but not meant to use array form

## Solution approach
- get input and confirm suitability
- import word set and reduce. can use word length and letters required to shrink the set significantly probably
- apply the tbd algorithm to create a valid square. maybe some tree structure?
- ok due to time I have gone with an iterative search but this is basically just an inefficient tree dfs

## Continued notes
- After doing the filtering there are still quite a lot of words ~10-100s. Brute force to check them all seems less than ideal.
- Thinking a prefix tree (apparently called Trie?) could be a good idea but not exactly trivial. If going for this route we could choose a word as the root node, subtract these letters from the selection and continue. 
- For some of the cases the amount of words is quite large ~650. Algorithm must be somewhere around O(n2) lmao. This is a lot of work. 
- Longest run time of tests is around 10s which is not ideal but as a one off meh

## Possible optimisations
- begin trie with letters that only occur once
- use a binary search to narrow words to be searched faster
- perhaps instead of searching words with the allowed letter I should continuously filter out bad options?
- all the usual parallel stuff would probably help, problems approach looks to lend itself to this well but no time for this here
- could probably cache results as repeated letter will yield the same results etc.

## Other changes
- tests should be re-thought and improved, lots of copy-pasted code, not very good coverage
