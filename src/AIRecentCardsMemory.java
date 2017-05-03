/**
 * AIRecentCardsMemory class creates an object that stores the values of
 * a card's row number, column number, index, and its value assigned to
 * it when it was generated. It allows for the A.I. to access multiple
 * attributes of a card easily and be able to store these values in memory.
 *
 * @author Tyler Hostager
 * @version 12/18/14
 */
public class AIRecentCardsMemory implements Comparable<AIRecentCardsMemory> {
    /** String value of the card's attributes */
    private String data;

    /** Integer values for the row, column, card value, and index */
    private int row, column, cardValue, cardLocation;

    /** Default constructor */
    public AIRecentCardsMemory() {
        row = -1;
        column = -1;
        cardValue = -1;
    }

    /**
     * Constructs a new object that holds the card's attributes in
     * order to be stored in the A.I.'s memory.
     *
     * @param newRow            The new card's row number.
     * @param newColumn         The new card's column number.
     * @param newCardLocation   The index of the new card.
     * @param newCardValue      The assigned value of the new card.
     */
    public AIRecentCardsMemory(int newRow, int newColumn, int newCardLocation, int newCardValue) {
        setRow(newRow);
        setColumn(newColumn);
        setCardLocation(newCardLocation);
        createMemObject(newCardValue, cardLocation);
        setData();
    }

    /***
     * Creates a memory object by setting the card value as well as the index.
     *
     * @param newCardValue      The assigned value of the card to be stored in memory.
     * @param newCardLocation   The index of the card to be stored in memory.
     */
    public void createMemObject(int newCardValue, int newCardLocation) {
        setCardValue(newCardValue);
        setCardLocation(newCardLocation);
    }

    /**
     * Overrides the default compareTo() values in order to store the values
     * in a way that allows for efficient searching when the A.I. searches
     * its memory in order to 'remember' a pair from the cards that have been
     * flipped.
     *
     * @param otherMemory   The other card being compared.
     * @return              The integer value of the comparison.
     */
    @Override
    public int compareTo(AIRecentCardsMemory otherMemory) {
        int compareCardValue, compareRow, compareColumn;

        String currentCardValue = String.valueOf(this.getCardValue()),
                otherMemoryValue = String.valueOf(otherMemory.getCardValue()),
                currentRow = String.valueOf(this.getRow()),
                otherMemoryRow = String.valueOf(otherMemory.getRow()),
                currentColumn = String.valueOf(this.getColumn()),
                otherMemoryColumn = String.valueOf(otherMemory.getColumn());

        compareCardValue = currentCardValue.compareTo(otherMemoryValue);
        if (compareCardValue < 0) {
            return -1;
        } else if (compareCardValue > 0) {
            return 1;
        } else {
            compareRow = currentRow.compareTo(otherMemoryRow);
            if (compareRow < 0) {
                return -1;
            } else if (compareRow > 0) {
                return 1;
            } else {
                compareColumn = currentColumn.compareTo(otherMemoryColumn);
                if (compareColumn < 0) {
                    return -1;
                } else if (compareColumn > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * Assigns the value of the new card to the object's local variable.
     * @param newCardValue  The assigned value of the new card.
     */
    private void setCardValue(int newCardValue) {
        cardValue = newCardValue;
    }

    /**
     * Retrieves the assigned card value of the object.
     * @return  The card value of the object.
     */
    public int getCardValue() {
        return cardValue;
    }

    /**
     * Assigns the data of the card's coordinates to a string, allowing
     * for faster searching for card matches.
     */
    public void setData() {
        String cardIndex = String.valueOf(row) + String.valueOf(column);
        try {
            data = cardIndex;
        } catch (Exception e) {/**/}
    }

    /**
     * Retrieves the string of the card's coordinates.
     * @return  The coordinate string.
     */
    public String getData() {
        return data;
    }

    /**
     * Assigns the row value of the card being added to the object's
     * row number.
     *
     * @param newRow    The row value of the new card.
     */
    public void setRow(int newRow) {
        row = newRow;
    }

    /**
     * Retrieves the row value of the object.
     * @return  The row value.
     */
    public int getRow() {
        return row;
    }

    /**
     * Assigns the column value of the card being added to the object's
     * row number.
     *
     * @param newColumn    The column value of the new card.
     */
    public void setColumn(int newColumn) {
        column = newColumn;
    }

    /**
     * Retrieves the column value of the object.
     * @return  The column value.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Assigns the location of the new card to the object.
     * @param newCardLocation   The card's location.
     */
    public void setCardLocation(int newCardLocation) {
        cardLocation = newCardLocation;
    }

    /**
     * Retrieves the location value of the object.
     * @return  The location value.
     */
    public int getCardLocation() {
        return cardLocation;
    }

    /**
     * Overrides the default toString() values in order to be viewed
     * in a meaningful way.
     *
     * @return  The string value of the memory object.
     */
    @Override
    public String toString() {
        return "\"" + cardValue + "\" at " + "(" + (row + 1) + ", " + (column + 1) + ")";

    }
}
