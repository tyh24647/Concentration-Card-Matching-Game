/**
 * AIPairsMemory class is an object that stores two cards as a single unit,
 * which allows for easier searching for the A.I. when remembering invalid pairs,
 * which is necessary for the A.I. to avoid guessing card pairs that have already
 * been guessed, and are not successful pairs.
 *
 * @author Tyler Hostager
 * @version 12/18/14
 */
public class AIPairsMemory {
    /**
     * Index for the two cards in the object, as well
     * as the number of rows and columns.
     */
    private int card1, card2, numRows, numColumns;

    /** The string value of a specified pair (used for efficient searching) */
    private String data;

    /** Default constructor */
    public AIPairsMemory() {
        card1 = -1;
        card2 = -1;
    }

    /**
     * Constructs a new card pair object to be stored in the A.I.'s memory.
     *
     * @param newCard1  The index of card 1.
     * @param newCard2  The index of card 2.
     */
    public AIPairsMemory(int newCard1, int newCard2) {
        setCard1(newCard1);
        setCard2(newCard2);
        setData();
    }

    /**
     * Converts the values of both of the cards' indexes, then assigns
     * them together into one string (i.e. "36" would be the locations
     * "3" and "6".
     */
    public void setData() {
        String cardIndex = String.valueOf(card1) + String.valueOf(card2);
        try {
            data = cardIndex;
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Assigns the location of the first card to a local variable.
     * @param newCard1  The index of card 1.
     */
    private void setCard1(int newCard1) {
        card1 = newCard1;
    }

    /**
     * Retrieves the index of the first card.
     * @return  The index of card 1.
     */
    public int getCard1() {
        return card1;
    }

    /**
     * Assigns the location of the first card to a local variable.
     * @param newCard2  The index of card 1.
     */
    private void setCard2(int newCard2) {
        card2 = newCard2;
    }

    /**
     * Retrieves the index of the first card.
     * @return  The index of card 1.
     */
    public int getCard2() {
        return card2;
    }

    /**
     * Assigns the number of rows to a local variable.
     * @param newNumRows    The number of rows in the game.
     */
    public void setNumRows(int newNumRows) {
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
     * Assigns the number of columns to a local variable.
     * @param newNumColumns The number of columns in the game.
     */
    public void setNumColumns(int newNumColumns) {
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
     * Retrieves the string value of the pair's indexes.
     * @return  The pair's indexes.
     */
    public String getData() {
        return data;
    }

    /**
     * Overrides the default toString() method in order to
     * print in a meaningful way.
     * @return  The overridden string values.
     */
    @Override
    public String toString() {
        return "[" + card1 + ", " + card2 + "] ";
    }
}
