import java.util.ArrayList;
import java.util.List;

/**
 * MatchingModel is the model class that provides the functionality of
 * the matching game.
 *
 * @author Tyler Hostager
 * @version 12/2/14
 */
public class MatchingModel {
    /** Array to contain card values */
    private List<Integer> gameArray;

    /** Set debug text to null as default */
    private boolean debugModeEnabled = false;

    /** The current player's number (1 or 2) */
    private int currentPlayer;

    /** The scores for player 1 and 2 */
    private int player1Score, player2Score;

    /**
     * Storage for a card pair, the number of rows,
     * The number of columns, the number of cards remaining,
     * and the number of total cards.
     */
    private int card1, card2, numRows, numColumns, cardsRemaining, numCards;

    /** Default constructor */
    public MatchingModel() {
        gameArray = null;
        currentPlayer = 0;
        player1Score = 0;
        player2Score = 0;
        card1 = 0;
        numRows = 0;
        numColumns = 0;
        cardsRemaining = 0;
        numCards = 0;
    }

    /**
     * Constructs a model object using the the board dimensions
     * provided by the user.
     *
     * @param newNumRows        The number of rows in the game board.
     * @param newNumColumns     The number of columns in the game board.
     * @throws Exception        If the user submits invalid dimensions.
     */
    public MatchingModel(int newNumRows, int newNumColumns) throws Exception {
        gameArray = new ArrayList<Integer>();
        numCards = newNumRows * newNumColumns;
        if (numCards % 2 != 0) {
            throw new Exception("\n\t* Error: Total number of cards must be even. *" +
                    "\n\t* Please restart program and try another board size. *");
        } else {
            setNumRows(newNumRows);
            setNumColumns(newNumColumns);
            cardsRemaining = numCards;
            createEmptyBoard();
            populateBoard();
        }
    }

    /**
     * Generates game board and fills with a value of -1
     * as a placeholder.
     */
    public void createEmptyBoard() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                gameArray.add(-1);
            }
        }
    }

    /**
     * Populates game board with with pairs of integers at random
     * ArrayList indexes if the value in the random location is still
     * the placeholder value (-1). If the space is already full, it
     * loops until it finds places that are empty.
     */
    public void populateBoard() {
        ArrayList<Integer> cardValues = new ArrayList<Integer>();
        int cardValue = 1;
        int iterations = 0;
        int arrayValue;
        while (cardValues.size() < numCards / 2) {
            cardValues.add(cardValue);
            cardValue++;
        } while (iterations < numCards / 2) {
            arrayValue = cardValues.get(iterations);
            int randR1 = (int) (Math.random() * (numRows));
            int randC1 = (int) (Math.random() * (numColumns));
            int randR2 = (int) (Math.random() * (numRows));
            int randC2 = (int) (Math.random() * (numColumns));
            int c1Index = (randR1 * numColumns) + randC1;
            int c2Index = (randR2 * numColumns) + randC2;
            if (getCardData(randR1, randC1) == -1
                    && getCardData(randR2, randC2) == -1 && c1Index != c2Index) {   // Set value if -1
                setCardData(randR1, randC1, arrayValue);
                setCardData(randR2, randC2, arrayValue);
                iterations++;
            }
        }
    }

    /**
     * Makes a move in the game if the user's input is valid by sending
     * the card values to be stored as local variables. It also prints out
     * the current game board to the console.
     *
     * @param row1      The coordinate value of the guess' card 1 row.
     * @param column1   The coordinate value of the guess' card 1 column.
     * @param row2      The coordinate value of the guess' card 2 row.
     * @param column2   The coordinate value of the guess' card 2 column.
     * @throws Exception If the submission is invalid.
     */
    public void makeMove(int row1, int column1, int row2, int column2) throws Exception {
        try {
            if (isValidMove(row1, column1, row2, column2)) {
                setGuess(row1, column1, row2, column2);
                printBoard(row1, column1, row2, column2);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\n\t* Please make a valid submission. *\n");
        }
    }

    /**
     * Stores the user's guess card locations to local variables
     * in order to be accessed by local functions.
     *
     * @param row1      The coordinate row number of the first guess.
     * @param column1   The coordinate column number of the first guess.
     * @param row2      The coordinate row number of the second guess.
     * @param column2   The coordinate column number of the first guess.
     */
    public void setGuess(int row1, int column1, int row2, int column2) {
        card1 = getCardData(row1, column1);
        card2 = getCardData(row2, column2);
    }

    /**
     * Checks both cards to see if their values match. It also
     * increases the user's score if the pair is a match.
     *
     * @return  True if both cards match.
     */
    public boolean matchedPair() {
        if (card1 == card2) {
            cardsRemaining -= 2;
            if (getCurrentPlayer() == 1) {
                player1Score++;
            } else {
                player2Score++;
            } return true;
        } currentPlayer++;
        return false;
    }

    /**
     * Retrieves the integer value of the current player (either 1 or 2).
     * @return the integer value of the current player.
     */
    public int getCurrentPlayer() {
        if (currentPlayer == 0) {
            return 1;
        } else if (currentPlayer % 2 != 0) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Checks if the user's move is valid by checking if the input values
     * are out of bounds, if a guessed card has already been matched with
     * another card, or if the user guessed the same location for both
     * guesses.
     *
     * @param row1      The coordinate row number of the first guess.
     * @param column1   The coordinate column number of the first guess.
     * @param row2      The coordinate row number of the second guess.
     * @param column2   The coordinate column number of the second guess.
     * @return          True if the move is valid.
     * @throws Exception If the move is invalid.
     */
    public boolean isValidMove(int row1, int column1, int row2, int column2) throws Exception {
        if (row1 > numRows - 1 || column1 > numColumns - 1
                || row2 > numRows - 1 || column2 > numColumns - 1) {
            if (getCurrentPlayer() == 1) {
                throw new Exception("\n\t* Error: The card requested is out of bounds. *");
            } else {
                return false;
            }
        } else if (getCardData(row1, column1) == -1 || getCardData(row2, column2) == -1) {
            if (getCurrentPlayer() == 1) {
                throw new Exception("\n\t* Error: The card requested is no longer available. *");
            } else {
                return false;
            }
        } else if (row1 == row2 && column1 == column2) {
            if (getCurrentPlayer() == 1) {
                throw new Exception("\n\t* Error: Guesses cannot be the same card. *");
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Retrieves the integer value of the winning player.
     *
     * @return              The winner's associated number.
     * @throws Exception    If the game is not over.
     */
    public int getWinner() throws Exception {
        if (!gameOver()) {
            throw new Exception("\n\t* Error: Cannot calculate winner until " +
                    "all matches are found. *");
        } else {
            if (player1Score > player2Score) {
                return 1;
            } else if (player1Score < player2Score) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    /**
     * Prints out the game board with the current guess cards exposed.
     *
     * @param row1      The coordinate row number of the first guess.
     * @param column1   The coordinate column number of the first guess.
     * @param row2      The coordinate row number of the second guess.
     * @param column2   The coordinate column number of the second guess.
     */
    public void printBoard(int row1, int column1, int row2, int column2) {
        if (debugModeEnabled) {
            System.out.print("\n\n- Current Move -");

            // Placeholders for the print string, the default card value
            // to be displayed, and the placeholder for an unavailable card
            String printString = "";
            String defaultCard = " #";
            String unavailableCard = " -";

            // Iterates through each value and prints the appropriate value
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    if (gameArray.get((i * numColumns) + j) == -1) {
                        if (j == numColumns - 1) {
                            printString += unavailableCard + "\n";
                        } else {
                            printString += unavailableCard;
                        }
                    } else if ((i * numColumns) + j == (row1 * numColumns) + column1
                            || (i * numColumns) + j == (row2 * numColumns) + column2) {
                        if (j == numColumns - 1) {
                            printString += " " + getCardData(i, j) + "\n";
                        } else {
                            printString += " " + getCardData(i, j);
                        }
                    } else {
                        if (j == numColumns - 1) {
                            printString += defaultCard + "\n";
                        } else {
                            printString += defaultCard;
                        }
                    }
                }
            }
            System.out.print("\n" + printString + "\n");
        }
    }

    /**
     * Prints out the game board with all of the card values revealed.
     * @return  The string of revealed cards to be printed to the console.
     */
    public String revealAllCards() {
        String printString = "";
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j == numColumns - 1) {
                    printString += " " + getCardData(i, j) + "\n";
                } else {
                    printString += " " + getCardData(i, j);
                }
            }
        } return printString;
    }

    /**
     * Assigns the data of a specified card to the value taken in
     * from the populateBoard() method
     *
     * @param newRow        The row number of the specified card.
     * @param newColumn     The column number of the specified card.
     * @param newValue      The value to be assigned to the specified location.
     */
    public void setCardData(int newRow, int newColumn, int newValue) {
        gameArray.set((newRow * numColumns) + newColumn, newValue);
    }

    /**
     * Retrieves the data of a card at the specified location.
     *
     * @param newRow        The row number of the specified card.
     * @param newColumn     The column number of the specified card.
     * @return              The integer value of the specified card.
     */
    public int getCardData(int newRow, int newColumn) {
        return gameArray.get((newRow * numColumns) + newColumn);
    }

    /**
     * Assigns the card at the specified location equal to -1,
     * which is the value for an unavailable card.
     *
     * @param newRow        The row number of the specified card.
     * @param newColumn     The column number of the specified card.
     */
    public void nullifyCard(int newRow, int newColumn) {
        setCardData(newRow, newColumn, -1);
    }

    /**
     * Returns the condition of the current game.
     * @return  True if the game is over.
     */
    public boolean gameOver() {
        return cardsRemaining == 0;
    }

    /**
     * Assigns the number of rows in the game board to the
     * number specified by the user.
     *
     * @param newNumRows    The user-specified number of rows
     */
    public void setNumRows(int newNumRows) {
        numRows = newNumRows;
    }

    /**
     * Retrieves the number of rows in the current game board.
     * @return  The number of rows.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Assigns the number of rows in the game board to the
     * number specified by the user.
     *
     * @param newNumColumns The user-specified number of columns.
     */
    public void setNumColumns(int newNumColumns) {
        numColumns = newNumColumns;
    }

    /**
     * Retrieves the number of columns in the current game board.
     * @return  The number of columns.
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Retrieves the score of player 1.
     * @return  Player 1's score.
     */
    public int getPlayer1Score() {
        return player1Score;
    }

    /**
     * Retrieves the score of player 2.
     * @return  Player 2's score.
     */
    public int getPlayer2Score() {
        return player2Score;
    }

    /**
     * Set's the boolean value for debug mode to true, which allows
     * for the print statements to be displayed to the console.
     */
    public void enableDebugMode() {
        debugModeEnabled = true;
    }
}



