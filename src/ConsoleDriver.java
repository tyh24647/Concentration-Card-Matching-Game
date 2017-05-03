import java.util.Scanner;

/**
 * ConsoleDriver class allows for the matching game to be played on
 * the console, rather than using a GUI.
 *
 * @author Tyler Hostager
 * @version 12/12/14
 */
public class ConsoleDriver {
    public static void main(String[] args) {
        boolean restartGame = true;
        while (restartGame) {
            Scanner input = new Scanner(System.in);
            MatchingModel game;
            AIPlayer bot;
            AIPairsMemory memory = new AIPairsMemory();
            boolean noWinner = true;
            int playerTurn = 0;
            int maxGuesses = 2;
            boolean displayScore = true;
            System.out.print("\n" +
                    "_____________________\n" +
                    "|                   |\n" +
                    "| - CONCENTRATION - |\n" +
                    "|                   |\n" +
                    "¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n" +
                    "Please submit desired game board dimensions...");
            System.out.print("\n» Number of rows: ");
            int numRows = input.nextInt();
            System.out.print("» Number of columns: ");
            int numColumns = input.nextInt();
            System.out.print("\nPlease select the difficulty..." +
                    "\n» 1 = Pathetic, 2 = Easy, 3 = Medium, 4 = Hard, 5 = Insane: ");
            int newDifficulty = input.nextInt();
            memory.setNumRows(numRows);
            memory.setNumColumns(numColumns);
            try {
                game = new MatchingModel(numRows, numColumns);
                bot = new AIPlayer(numRows, numColumns, newDifficulty);
                while (noWinner) {
                    int guessCounter = 0;
                    while (guessCounter < maxGuesses) {
                        try {
                            if (playerTurn == 0) {
                                displayScore = false;
                                System.out.print("\n\n______________\n|  Player " + game.getCurrentPlayer() +
                                        "  |\n¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n");
                            } if (displayScore) {
                                System.out.print("\nSCORE:\n¯¯¯¯¯¯¯¯¯¯¯\nPlayer 1: " + game.getPlayer1Score() +
                                        "\nPlayer 2: " + game.getPlayer2Score());
                                Thread.sleep(100);
                                System.out.print("\n\n\n______________\n|  Player " + game.getCurrentPlayer() +
                                        "  |\n¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n");
                            } if (game.getCurrentPlayer() == 1) {
                                displayScore = true;
                                guessCounter++;             // TODO Fix - should always either be 1 or 2
                                System.out.print("\nGuess #" + guessCounter + "\n» Enter desired row: ");
                                int row1 = input.nextInt() - 1;
                                System.out.print("» Enter desired column: ");
                                int column1 = input.nextInt() - 1;
                                guessCounter++;
                                System.out.print("\nGuess #" + guessCounter + " \n» Enter desired row: ");
                                int row2 = input.nextInt() - 1;
                                System.out.print("» Enter desired column: ");
                                int column2 = input.nextInt() - 1;
                                Thread.sleep(300);
                                game.makeMove(row1, column1, row2, column2);
                                game.printBoard(row1, column1, row2, column2);
                                int cardValue1 = game.getCardData(row1, column1);
                                int cardValue2 = game.getCardData(row2, column2);
                                int card1Location = (row1 * numColumns) + column1;
                                int card2Location = (row2 * numColumns) + column2;
                                if (!bot.recallsIndividualCard(row1, column1, card1Location, cardValue1)
                                        && game.getCardData(row1, column1) != -1) {
                                    bot.addCardToMemory(row1, column1, card1Location, cardValue1);
                                } if (!bot.recallsIndividualCard(row2, column2, card2Location, cardValue2)
                                        && game.getCardData(row2, column2) != -1) {
                                    bot.addCardToMemory(row2, column2, card2Location, cardValue2);
                                } Thread.sleep(600);
                                if (game.matchedPair()) {
                                    System.out.print("\n\t* It's a match! *\n\n");
                                    if (game.gameOver()) {
                                        noWinner = false;
                                        break;
                                    } else {
                                        game.nullifyCard(row1, column1);
                                        game.nullifyCard(row2, column2);
                                        bot.forgetIndividualCard(row1, column1, card1Location, cardValue1);
                                        bot.forgetIndividualCard(row2, column2, card2Location, cardValue2);
                                        maxGuesses++;
                                        break;
                                    }
                                } else {
                                    bot.storeFailedPair(row1, column1, row2, column2);
                                    System.out.print("\n\t* Sorry, not a match. *\n\n");
                                    playerTurn++;
                                    guessCounter = 0;
                                    break;
                                }
                            } else {    // <-- A.I. options
                                try {
                                    int botRow1 = bot.getRow1Guess();
                                    int botRow2 = bot.getRow2Guess();
                                    int botColumn1 = bot.getColumn1Guess();
                                    int botColumn2 = bot.getColumn2Guess();
                                    int card1Location = (botRow1 * numColumns) + botColumn1;
                                    int card2location = (botRow2 * numColumns) + botColumn2;
                                    boolean logicalGuess = false;
                                    while (!logicalGuess) {
                                        bot.makeIntelligentGuess();
                                        botRow1 = bot.getRow1Guess();
                                        botColumn1 = bot.getColumn1Guess();
                                        botRow2 = bot.getRow2Guess();
                                        botColumn2 = bot.getColumn2Guess();
                                        card1Location = (botRow1 * numColumns) + botColumn1;
                                        card2location = (botRow2 * numColumns) + botColumn2;
                                        if (!bot.remembersFailedPair(card1Location, card2location)) {
                                            logicalGuess = true;
                                        }
                                    } displayScore = true;
                                    guessCounter++;
                                    System.out.print("\nGuess #" + guessCounter + "\n» Enter desired row: ");
                                    Thread.sleep(1000);
                                    System.out.print(botRow1 + 1);
                                    Thread.sleep(100);
                                    System.out.print("\n» Enter desired column: ");
                                    Thread.sleep(1000);
                                    System.out.print(botColumn1 + 1);
                                    Thread.sleep(100);
                                    guessCounter++;
                                    System.out.print("\n\nGuess #" + guessCounter + "\n» Enter desired row: ");
                                    Thread.sleep(1000);
                                    System.out.print(botRow2 + 1);
                                    Thread.sleep(100);
                                    System.out.print("\n» Enter desired column: ");
                                    Thread.sleep(1000);
                                    System.out.print((botColumn2 + 1) + "\n");
                                    Thread.sleep(300);
                                    game.makeMove(botRow1, botColumn1, botRow2, botColumn2);
                                    game.printBoard(botRow1, botColumn1, botRow2, botColumn2);
                                    int cardValue1 = game.getCardData(botRow1, botColumn1);
                                    int cardValue2 = game.getCardData(botRow2, botColumn2);
                                    if (!bot.recallsIndividualCard(botRow1, botColumn1, card1Location, cardValue1)
                                            && game.getCardData(botRow1, botColumn1) != -1) {
                                        bot.addCardToMemory(botRow1, botColumn1, card1Location, cardValue1);
                                    } if (!bot.recallsIndividualCard(botRow2, botColumn2, card2location, cardValue2)
                                            && game.getCardData(botRow2, botColumn2) != -1) {
                                        bot.addCardToMemory(botRow2, botColumn2, card2location, cardValue2);
                                    }
                                    Thread.sleep(600);
                                    if (game.matchedPair()) {
                                        System.out.print("\n\t* It's a match! *\n\n");
                                        if (game.gameOver()) {
                                            noWinner = false;
                                            break;
                                        } else {
                                            game.nullifyCard(botRow1, botColumn1);
                                            game.nullifyCard(botRow2, botColumn2);
                                            bot.forgetIndividualCard(botRow1, botColumn1,
                                                    card1Location, cardValue1);
                                            bot.forgetIndividualCard(botRow2, botColumn2,
                                                    card2location, cardValue2);
                                            maxGuesses++;
                                            break;
                                        }
                                    } else {
                                        bot.storeFailedPair(botRow1, botColumn1, botRow2, botColumn2);
                                        System.out.print("\n\t* Sorry, not a match. *\n\n");
                                        playerTurn++;
                                        guessCounter = 0;
                                        break;
                                    }
                                } catch (Exception e) {
                                    System.out.print("\n" + e.getMessage() + "\n");
                                }
                                displayScore = false;
                            }
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }
                } System.out.print("\n     _______________\n     |  Game Over  |     \n     ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
                if (game.getWinner() == 0) {
                    System.out.print("\n» The game was a tie!\n");
                } else {
                    Thread.sleep(300);
                    System.out.print("\n» The winner is");
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(300);
                        System.out.print(".");
                        Thread.sleep(600);
                    } System.out.print(" Player ");
                    Thread.sleep(198);
                    System.out.print(game.getWinner() + "!\n");
                } System.out.print("Would you like to try again? (1 = \"Yes\"), (2 = \"No\"): ");
                int decision = input.nextInt();
                if (decision == 2) {
                    Thread.sleep(1000);
                    System.out.print("Exiting program.\n");
                    restartGame = false;
                    System.exit(0);
                } else {
                    restartGame = true;
                    System.out.print("\n\n\n\n\n");
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
}
