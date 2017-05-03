import java.util.*;

/**
 * AIPlayer class creates an object of an A.I., and provides the functionality to
 * remember specific cards as well as pairs of cards that have been played. It also
 * has the functionality to predict likely matches by excluding the cards that it
 * knows will not work to be paired.
 *
 * @author Tyler Hostager
 * @version 12/11/14
 */
public class AIPlayer {
    /**
     * Integer values for the card data, as well as the memory size for the number
     * of specific cards remembered, as well as the number of failures remembered.
     */
    private int botRow1, botColumn1, botRow2, botColumn2, numRows, numColumns, difficulty,
            failureMemSize, cardMemSize, numCards;

    /** Default boolean value disabling print statements */
    private boolean debugModeEnabled = false;

    /** Storage to remember the locations of failed pairing attempts */
    private List<AIPairsMemory> nonMatchedPairs;

    /** Memory storage for cards that have been recently played */
    private List<AIRecentCardsMemory> separateCardMemory;

    /** Default constructor */
    public AIPlayer() {
        botRow1 = 0;
        botColumn1 = 0;
        botRow2 = 0;
        botColumn2 = 0;
        failureMemSize = 0;
        nonMatchedPairs = null;
    }

    /**
     * Constructs a new A.I. object using the number of rows and columns, as
     * well as the integer value the user-specified difficulty setting.
     *
     * @param newNumRows        The number of rows in the game.
     * @param newNumColumns     The number of columns in the game.
     * @param newDifficulty     The difficulty setting of the game.
     */
    public AIPlayer(int newNumRows, int newNumColumns, int newDifficulty) {
        setNumRows(newNumRows);
        setNumColumns(newNumColumns);
        setDifficulty(newDifficulty);
        numCards = numRows * numColumns;
        nonMatchedPairs = new ArrayList<AIPairsMemory>();
        separateCardMemory = new ArrayList<AIRecentCardsMemory>();
    }

    /**
     * Creates random integer values to guess for pairs if it is unable to
     * make a move by matching recent card values in its memory.
     */
    public void generateRandomGuess() {
        botRow1 = (int)(Math.random() * getNumRows());
        botColumn1 = (int)(Math.random() * getNumColumns());
        botRow2 = (int)(Math.random() * getNumRows());
        botColumn2 = (int)(Math.random() * getNumColumns());
    }

    /**
     * Adds an individual card to its memory by creating an AIRecentCardsMemory object
     * and adds it the arrayList. If the length of the list equals the maximum amount it
     * should remember, two random AIRecentCardsMemory objects are deleted in order for
     * its memory to be flawed like a human.
     *
     * The difficulty setting specified by the user corresponds with the number of
     * cards the A.I. can remember, and that determines how many cards it can store
     * before remembered cards are removed from its memory.
     *
     * @param row           The row value of the card to be remembered.
     * @param column        The column value of the card to be remembered.
     * @param cardLocation  The index of the card to be remembered.
     * @param cardValue     The card's assigned value of the card to be remembered.
     */
    public void addCardToMemory(int row, int column, int cardLocation, int cardValue) {
        AIRecentCardsMemory newMemory = new AIRecentCardsMemory(row, column, cardLocation, cardValue);
        if (!recallsIndividualCard(row, column, cardLocation, cardValue)) {
            if (debugModeEnabled) {
                System.out.print("\n> AI: Remembering card value \"" + cardValue
                        + "\" at location \"" + cardLocation + "\".");
            } separateCardMemory.add(newMemory);
            if (separateCardMemory.size() >= cardMemSize) {
                forgetRandomCard();
                forgetRandomCard();
            } Collections.sort(separateCardMemory);
            if (debugModeEnabled) {
                System.out.print("\n> AI: Cards remembered: " + separateCardMemory);
            }
        }
    }

    /**
     * Sets the values of the guess it is going to make by searching through its
     * memory of recently-played cards to find matching card values, retrieving
     * the coordinates of the cards that will be a match, and sets its guess values
     * to the ones of the match it just found, as long as the stored values aren't
     * the same card, if the card being stored has become null after being remembered,
     * or if it is unavailable..
     *
     * If there are no pairs of cards in its recent memory, it guesses random locations,
     * just like a human would.
     */
    public void makeIntelligentGuess() {
        boolean remembersMatchLocation = false;
        if (separateCardMemory.size() > 2) {
            for (int i = 0; i < separateCardMemory.size(); i++) {
                AIRecentCardsMemory currentMemory = separateCardMemory.get(i);
                int currentCardValue = currentMemory.getCardValue();
                i++;
                if (i == separateCardMemory.size() - 2) {
                    remembersMatchLocation = false;
                    break;
                } else if (separateCardMemory.get(i).getCardValue() == currentCardValue
                        && !separateCardMemory.get(i).equals(currentMemory)
                        && separateCardMemory.get(i) != null
                        && separateCardMemory.get(i).getCardValue() != -1) {
                    remembersMatchLocation = true;
                    botRow1 = currentMemory.getRow();
                    botColumn1 = currentMemory.getColumn();
                    botRow2 = separateCardMemory.get(i).getRow();
                    botColumn2 = separateCardMemory.get(i).getColumn();
                    break;
                }
            }
        } if (!remembersMatchLocation) {
            generateRandomGuess();
        }
    }

    /**
     * Removes an specific card from the recent cards memory from the location
     * by creating a memory object, checking if there is an object identical to
     * it, and removes that card.
     *
     * @param row       The row of the card to delete.
     * @param column    The column of the card to delete.
     * @param location  The index of the card to delete.
     * @param cardValue The assigned value of the card to delete.
     */
    public void forgetIndividualCard(int row, int column, int location, int cardValue) {
        AIRecentCardsMemory testMemory = new AIRecentCardsMemory(row, column, location, cardValue);
        for (int i = 0; i < separateCardMemory.size(); i++) {
            if (separateCardMemory.get(i).toString().equals(testMemory.toString())) {
                separateCardMemory.remove(i);
            }
        }
    }

    /**
     * Checks its recent card memory to see if it remembers the a specific card
     * by creating a new memory object from the data passed in, then checking the
     * memory objects for an object that exactly matches it.
     *
     * @param row       The row of the card to delete.
     * @param column    The column of the card to delete.
     * @param location  The index of the card to delete.
     * @param cardValue The assigned value of the card to delete.
     * @return  True if card is stored in recent memory.
     */
    public boolean recallsIndividualCard(int row, int column, int location, int cardValue) {
        AIRecentCardsMemory memoryChecker = new AIRecentCardsMemory(row, column, location, cardValue);
        for (int i = 0; i < separateCardMemory.size(); i++) {
            if (separateCardMemory.get(i).toString().equals(memoryChecker.toString())) {
                return true;
            }
        } return false;
    }

    /**
     * Stores the locations of a specific pair of cards in its memory
     * list for pairs that do not match.
     *
     * @param row1      The row of the first card to remember.
     * @param column1   The column of the first card to remember.
     * @param row2      The row of the second card to remember.
     * @param column2   The column of the second card to remember.
     */
    public void storeFailedPair(int row1, int column1, int row2, int column2) {
        int card1 = (row1 * numColumns) + column1;
        int card2 = (row2 * numColumns) + column2;
        String pairLocation = String.valueOf(card1) + String.valueOf(card2);
        String revPLocation = String.valueOf(card2) + String.valueOf(card1);
        AIPairsMemory newPair = new AIPairsMemory(card1, card2);
        if (nonMatchedPairs.isEmpty()) {
            nonMatchedPairs.add(newPair);
        } else {
            boolean addData = true;
            int counter = 0;
            while (counter < nonMatchedPairs.size()) {
                if (nonMatchedPairs.get(counter).getData().equals(pairLocation)
                        || nonMatchedPairs.get(counter).getData().equals(revPLocation)) {
                    addData = false;
                } counter++;
            } if (addData) {
                if (debugModeEnabled) {
                    System.out.print("\n> AI: Remembering unsuccessful match at indexes \"" + card1 +
                            "\" and \"" + card2 + "\".");
                } nonMatchedPairs.add(newPair);
            }
        } if (nonMatchedPairs.size() == getFailureMemSize()
                && row1 != -1 && column1 != -1 && row2 != -1 && column2 != -1) {
            forgetOldestFailPair();
        }
    }

    /**
     * Search the memory of failed pairs in order to see if a pair in
     * question is in its memory.
     *
     * @param card1 The first card location to check for.
     * @param card2 The second card location to check for.
     * @return  True if the cards are in its memory.
     */
    public boolean remembersFailedPair(int card1, int card2) {
        String pairToFind = String.valueOf(card1) + String.valueOf(card2);
        String revPairToFind = String.valueOf(card2) + String.valueOf(card1);
        int index = 0;
        while (index < nonMatchedPairs.size()) {
            if (nonMatchedPairs.get(index).getData().equals(pairToFind)
                    || nonMatchedPairs.get(index).getData().equals(revPairToFind)) {
                if (debugModeEnabled) {
                    System.out.print("\n> AI: Avoiding pair \"" + nonMatchedPairs.get(index).getCard1() +
                            "\" and \"" + nonMatchedPairs.get(index).getCard2() + "\".");
                } return true;
            } index++;
        } return false;
    }

    /**
     * Removes the remembered pair at the beginning of the list, which
     * will always be the oldest card in its memory. Deleting this card
     * allows for realistic forgetfulness.
     */
    void forgetOldestFailPair() {
        if (debugModeEnabled) {
            System.out.print("\n> AI: Forgetting pair \"" + nonMatchedPairs.get(0).getCard1() + "\" " +
                    "and \"" + nonMatchedPairs.get(0).getCard2() + "\".");
        } nonMatchedPairs.remove(0);
    }

    /**
     * Forgets a random card to simulate forgetfulness of people by forgetting
     * random specific cards that have been played.
     */
    public void forgetRandomCard() {
        int randomLocation = (int)(Math.random() * separateCardMemory.size());
        if (randomLocation < separateCardMemory.size()) {
            if (debugModeEnabled) {
                System.out.print("\n> AI: Forgetting card \"" + separateCardMemory.get(randomLocation) +
                        "\" at index \"" + randomLocation + "\".");
            } separateCardMemory.remove(randomLocation);
        } else {
            forgetRandomCard();
        }
    }

    /**
     * Assigns the string value for the specified difficulty, as well
     * as call functions to assign the number of cards to store in the
     * pairs and individual cards memory according to the difficulty.
     * The higher the difficulty, the more cards the A.I. remembers.
     *
     * @param newDifficulty The integer value of the difficulty.
     */
    public void setDifficulty(int newDifficulty) {
        difficulty = newDifficulty;
        String difficultyString;
        if (difficulty == 1) {
            difficultyString = "Pathetic";
        } else if (difficulty == 2) {
            difficultyString = "Easy";
        } else if (difficulty == 3) {
            difficultyString = "Medium";
        } else if (difficulty == 4) {
            difficultyString = "Hard";
        } else {
            difficultyString = "Insane";
        } if (debugModeEnabled) {
            System.out.println("\n> AI: Difficulty set to \'"
                    + difficultyString + "\'.");
        } setTotalCardsRemembered();
        setNumFailsRecalled();
    }

    /**
     * Retrieves the value of the difficulty setting.
     * @return  The difficulty setting.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Assigns the memory size according to the
     * difficulty specified by the user.
     */
    private void setNumFailsRecalled() {
        if (getDifficulty() == 0) {
            failureMemSize = 0;
        } else if (getDifficulty() == 1) {
            failureMemSize = 3;
        } else if (getDifficulty() == 2) {
            failureMemSize = 5;
        } else if (getDifficulty() == 3) {
            failureMemSize = 10;
        } else {
            failureMemSize = 10000;
        }
    }

    /**
     * Assigns the total amount of cards remembered
     * according to the difficulty.
     */
    private void setTotalCardsRemembered() {
        if (getDifficulty() == 0) {
            cardMemSize = 2;
        } else if (getDifficulty() == 1) {
            cardMemSize = 6;
        } else if (getDifficulty() == 2) {
            cardMemSize = 10;
        } else if (getDifficulty() == 3) {
            cardMemSize = 16;
        } else {
            cardMemSize = 10000;
        }
    }

    /** Allows printing to console when called */
    public void enableDebugMode() {
        debugModeEnabled = true;
    }

    /**
     * Retrieves A.I. memory list when called
     * @return  memory list
     */
    public List<AIRecentCardsMemory> getBotMemory() {
        return separateCardMemory;
    }

    /**
     * Retrieves the memory size of the failed pairs
     * @return  Memory size.
     */
    public int getFailureMemSize() {
        return failureMemSize;
    }

    /**
     * Assigns the number of rows to a local variable
     * @param newNumRows    the nubmer of rows in the game.
     */
    private void setNumRows(int newNumRows) {
        numRows = newNumRows;
    }

    /**
     * Retrieves the number of rows in the game.
     * @return  The number of rows.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Assigns the number of columns in the game to a local variable.
     * @param newNumColumns The number of columns in the game.
     */
    private void setNumColumns(int newNumColumns) {
        numColumns = newNumColumns;
    }

    /**
     * Retrieves the number of columns in the game.
     * @return  The number of columns.
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Retrieves the A.I. object's row coordinate for its first guess
     * @return  The row value for the first guess
     */
    public int getRow1Guess() {
        return botRow1;
    }

    /**
     * Retrieves the A.I. object's column coordinate for its first guess
     * @return  The column value for the first guess
     */
    public int getColumn1Guess() {
        return botColumn1;
    }

    /**
     * Retrieves the A.I. object's row coordinate for its second guess
     * @return  The row value for the second guess
     */
    public int getRow2Guess() {
        return botRow2;
    }

    /**
     * Retrieves the A.I. object's column coordinate for its second guess
     * @return  The column value for the second guess
     */
    public int getColumn2Guess() {
        return botColumn2;
    }
}
