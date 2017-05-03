import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * ViewController class is the graphic user interface for the program, which provides
 * the view for the user, as well as controller aspects.
 *
 * @author Tyler Hostager
 * @version 12/2/14
 */
public class ViewController extends JFrame implements ActionListener {
    /** Card button list */
    private final List<JButton> cardButtons = new ArrayList<JButton>();

    /** AI object */
    private AIPlayer bot;

    /** Submit button */
    private JButton submitButton;

    /** Initialize panels */
    private JPanel backgroundPanel, mainPanel, displayCards, dataView, centerPanel;

    /** Layered pane to allow backgrounds */
    private JLayeredPane layeredViewPane;

    /** Integer placeholders for user-specified attributes */
    private int numCards, numRows, numColumns, row1, column1, row2, column2, gameType, rowsDefault, columnsDefault,
            guessCounter, botRow1, botColumn1, botRow2, botColumn2, cardFlipTimer, difficulty;

    /** Layout for card buttons */
    private GridLayout cardsLayout;

    /** Strings to contain default and user-specified attributes */
    private String player1Name, player2Name, cardBackground, playerTextDefault, rowTextDefault, columnTextDefault;

    /** Model object */
    private MatchingModel game;

    /** Labels for the GUI */
    private JLabel topBanner, playerDisplayLabel, resultLabel, p1ScoreLabel, p2ScoreLabel, difficultyLabel,
            showResultLabel, playerLabel, scoreLabel, rowsLabel, columnsLabel, nameLabel, nameLabel2;

    /** Text fields for user input */
    private JTextField player1NameTF, player2NameTF, numRowsTF, numColumnsTF;

    /** Stores if debug mode is enabled */
    private boolean debugModeEnabled;

    /** Menu items for the top menu */
    private JMenuItem newGame, exitGame, redCards, blueCards, blackCards, goldCards,
            debugMode, defaultTime, extendedTime, longTime, tropical, western, defaultWP;

    /** Combo boxes allowing for different user decisions */
    private JComboBox gameTypeCBox, difficultyCBox;

    /** Custom colors to be accessed in multiple methods */
    private Color darkGreen = new Color(10, 25, 0),
            lightGold = new Color(245, 230, 150),
            darkBrown = new Color(40, 30, 0),
            darkBlue = new Color(10, 30, 50),
            lightBlue = new Color(200, 250, 250);

    /** Custom fonts to use for themes */
    private Font defaultFont, westernFont, tropicalFont;

    /** Check boxes for sizing specification */
    private JCheckBox defaultSizeCheckBox, customSizeCheckBox;

    /** MenuBarObj object for custom menu bar */
    private MenuBarObj menuBarObj;

    /** Opening dialog */
    private OptionsDialog dialog;

    /** Console view */
    private JDialog consoleView;

    /** Constructor */
    public ViewController() {

        // Initialize Panels
        game = null;
        bot = null;
        debugModeEnabled = false;
        layeredViewPane = new JLayeredPane();
        layeredViewPane.setLayout(new BorderLayout());
        mainPanel = new JPanel(new BorderLayout());
        backgroundPanel = new JBackgroundPanel();
        dialog = new OptionsDialog();
        dataView = new JPanel(new GridLayout(11, 0));
        consoleView = new JDialog();

        // Shows opening dialog
        showOptionsDialog();

        // Creates menu bar
        menuBarObj = new MenuBarObj();
        getMenuBarData();

        // Create GUI elements for main frame
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setOpaque(false);
        topBanner = new JLabel("♥               ♠            - " +
                "CONCENTRATION -            ♦               ♣");
        topBanner.setPreferredSize(new Dimension(0, 60));
        topBanner.setHorizontalAlignment(SwingConstants.CENTER);
        topBanner.setVerticalAlignment(SwingConstants.CENTER);
        topBanner.setBackground(darkGreen);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, lightGold);
        topBanner.setBorder(bottomBorder);
        topBanner.setOpaque(true);
        topBanner.setForeground(lightGold);
        defaultFont = topBanner.getFont();
        topBanner.setFont(topBanner.getFont().deriveFont(26.0f));
        topPanel.add(topBanner);
        topPanel.add(new JLabel());     // Spacer
        JLabel bottomLabel = new JLabel();
        bottomLabel.setOpaque(false);
        bottomLabel.setPreferredSize(new Dimension(0, 25));
        JLabel leftLabel = new JLabel();
        leftLabel.setOpaque(false);
        leftLabel.setPreferredSize(new Dimension(25, 0));
        JLabel rightLabel = new JLabel();
        rightLabel.setOpaque(false);
        rightLabel.setPreferredSize(new Dimension(25, 0));
        displayCards.setOpaque(true);
        displayCards.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, darkGreen));
        displayCards.setPreferredSize(new Dimension(590, 535));
        displayCards.setBackground(darkGreen);
        dataView.setPreferredSize(new Dimension(300, 500));
        dataView.setOpaque(false);
        dataView.setBackground(darkGreen);
        playerLabel = new JLabel("»  Current Player  «");
        playerLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, lightGold));
        playerLabel.setFont(playerLabel.getFont().deriveFont(18.0f));
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerLabel.setForeground(lightGold);
        playerLabel.setBackground(darkGreen);
        playerLabel.setOpaque(true);
        playerDisplayLabel = new JLabel(getPlayerName());
        playerDisplayLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, lightGold));
        playerDisplayLabel.setFont(playerDisplayLabel.getFont().deriveFont(16.0f));
        playerDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerDisplayLabel.setForeground(lightGold);
        playerDisplayLabel.setBackground(darkGreen);
        playerDisplayLabel.setOpaque(true);
        scoreLabel = new JLabel("»  Scoreboard  «");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(18.0f));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(darkGreen);
        scoreLabel.setForeground(lightGold);
        scoreLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, lightGold));
        p1ScoreLabel = new JLabel("  ›  Player 1: " + game.getPlayer1Score());
        p1ScoreLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, lightGold));
        p1ScoreLabel.setForeground(lightGold);
        p1ScoreLabel.setBackground(darkGreen);
        p1ScoreLabel.setOpaque(true);
        p2ScoreLabel = new JLabel("  ›  Player 2: " + game.getPlayer2Score());
        p2ScoreLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, lightGold));
        p2ScoreLabel.setForeground(lightGold);
        p2ScoreLabel.setBackground(darkGreen);
        p2ScoreLabel.setOpaque(true);
        resultLabel = new JLabel("»  Current Guess Result:  «");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, lightGold));
        resultLabel.setFont(resultLabel.getFont().deriveFont(18.0f));
        resultLabel.setForeground(lightGold);
        resultLabel.setBackground(darkGreen);
        resultLabel.setOpaque(true);
        showResultLabel = new JLabel("  ›  Select a card to begin game...");
        showResultLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, lightGold));
        showResultLabel.setForeground(lightGold);
        showResultLabel.setBackground(darkGreen);
        showResultLabel.setOpaque(true);

        // Add options to userOption panel
        dataView.add(playerLabel);
        dataView.add(playerDisplayLabel);
        dataView.add(new JLabel());
        dataView.add(new JLabel());
        dataView.add(resultLabel);
        dataView.add(showResultLabel);
        dataView.add(new JLabel());
        dataView.add(new JLabel());
        dataView.add(scoreLabel);
        dataView.add(p1ScoreLabel);
        dataView.add(p2ScoreLabel);
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(100, 0));
        spacer.setOpaque(false);
        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.add(displayCards);
        centerPanel.add(spacer);
        centerPanel.add(dataView);
        centerPanel.setBackground(darkGreen);

        // Set mainPanel attributes
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new Dimension(1100, 725));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftLabel, BorderLayout.WEST);
        mainPanel.add(rightLabel, BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add panels to Layered pane
        backgroundPanel.setOpaque(false);
        backgroundPanel.setBounds(0, 0, 1100, 725);
        layeredViewPane.add(backgroundPanel, BorderLayout.CENTER);
        layeredViewPane.setLayer(backgroundPanel, 0);
        layeredViewPane.add(mainPanel, BorderLayout.CENTER);
        layeredViewPane.setLayer(mainPanel, 1);

        // Prevents console from being seen until true
        consoleView.setVisible(false);

        validate();
        repaint();
        add(layeredViewPane);
        setJMenuBar(menuBarObj);
        setTitle("Memory Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    /** Creates debug window to display console output */
    private void viewDebugConsole() {
        consoleView.setAlwaysOnTop(true);
        consoleView.setTitle("Debug Console");
        consoleView.setLayout(new BorderLayout());
        JTextArea console = new JTextArea();
        console.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(console);
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        ); console.select(Integer.MAX_VALUE, 0);
        console.setFont(new Font("Lucida Console", Font.PLAIN, 11));
        PrintStream output = new PrintStream(new ConsoleOutput(console));
        System.setOut(output);
        System.setErr(output);
        consoleView.add(scrollPane);
        consoleView.setPreferredSize(new Dimension(600, 400));
        consoleView.setResizable(false);
        consoleView.setLocationRelativeTo(null);
        consoleView.pack();
        consoleView.setVisible(true);
    }

    /** Creates options JDialogs */
    private void showOptionsDialog() {
        final String[] gameTypeOptions = {
                "- No Selection -",
                "Single-Player",
                "Multiplayer"
        }; gameTypeCBox = new JComboBox(gameTypeOptions);
        gameTypeCBox.addActionListener(this);
        dialog.setGameTypeOptions(gameTypeOptions);
        final String[] difficultyOptions = {
                "- No Selection -",
                "Pathetic", "Easy", "Medium",
                "Hard", "Extreme"
        }; difficultyCBox = new JComboBox(difficultyOptions);
        difficultyCBox.addActionListener(this);
        dialog.setDifficultyOptions(difficultyOptions);
        dialog.generateComboBoxes(gameTypeCBox, difficultyCBox);
        dialog.setSCenterPanel();
        dialog.setBottomPanel();
        getDialogData();
        setEnabledData(gameTypeCBox);
        setEnabledData(defaultSizeCheckBox);
        dialog.populateStartupPanel();
        dialog.addStartupPanel();
    }

    /** Retrievs data from menu bar and assigns to local variables */
    private void getMenuBarData() {
        defaultTime = menuBarObj.getDefaultTime();
        defaultTime.addActionListener(this);
        extendedTime = menuBarObj.getExtendedTime();
        extendedTime.addActionListener(this);
        longTime = menuBarObj.getLongTime();
        longTime.addActionListener(this);
        newGame = menuBarObj.getNewGame();
        newGame.addActionListener(this);
        exitGame = menuBarObj.getExitGame();
        exitGame.addActionListener(this);
        defaultWP = menuBarObj.getDefaultWP();
        defaultWP.addActionListener(this);
        tropical = menuBarObj.getTropical();
        tropical.addActionListener(this);
        western = menuBarObj.getWestern();
        western.addActionListener(this);
        redCards = menuBarObj.getRedCards();
        redCards.addActionListener(this);
        blueCards = menuBarObj.getBlueCards();
        blueCards.addActionListener(this);
        blackCards = menuBarObj.getBlackCards();
        blackCards.addActionListener(this);
        goldCards = menuBarObj.getGoldCards();
        goldCards.addActionListener(this);
        debugMode = menuBarObj.getDebugMode();
        debugMode.addActionListener(this);
    }

    /** Retrieves data from dialog box and assigns it to local variables */
    private void getDialogData() {
        rowsLabel = dialog.getRowsLabel();
        columnsLabel = dialog.getColumnsLabel();
        rowsDefault = dialog.getRowsDefault();
        columnsDefault = dialog.getColumnsDefault();
        playerTextDefault = dialog.getPlayerTextDefault();
        rowTextDefault = dialog.getRowTextDefault();
        columnTextDefault = dialog.getColumnTextDefault();
        player1NameTF = dialog.getPlayer1NameTF();
        player2NameTF = dialog.getPlayer2NameTF();
        numRowsTF = dialog.getNumRowsTF();
        numColumnsTF = dialog.getNumColumnsTF();
        nameLabel = dialog.getNameLabel();
        nameLabel2 = dialog.getNameLabel2();
        difficultyLabel = dialog.getDifficultyLabel();
        submitButton = dialog.getSubmitButton();
        submitButton.addActionListener(this);
        defaultSizeCheckBox = dialog.getDefaultSizeCheckBox();
        defaultSizeCheckBox.addActionListener(this);
        customSizeCheckBox = dialog.getCustomSizeCheckBox();
        customSizeCheckBox.addActionListener(this);
    }

    /**
     * Conducts the appropriate action when a specific action trigger occurs
     * @param e The event that triggered the class.
     */
    public void actionPerformed(ActionEvent e) {
        Object actionSource = e.getSource();
        try {
            if (actionSource == submitButton) {
                createGame();
            } else if (actionSource == customSizeCheckBox
                    || actionSource == defaultSizeCheckBox) {
                setEnabledData(actionSource);
            } else if (cardButtons.contains(actionSource)) {
                makeUserGuess(actionSource);
            } else if (actionSource == defaultTime) {
                setDefaultTimer(1);
            } else if (actionSource == extendedTime) {
                setDefaultTimer(2);
            } else if (actionSource == longTime) {
                setDefaultTimer(5);
            } else if (actionSource == newGame) {
                resetGame();
            } else if (actionSource == debugMode) {
                enableDebugMode();
            } else if (actionSource == defaultWP) {
                setAppBackground(defaultWP.getText());
            } else if (actionSource == tropical) {
                setAppBackground(tropical.getText());
            } else if (actionSource == western) {
                setAppBackground(western.getText());
            } else if (actionSource == redCards) {
                setDeckTheme("Red");
            } else if (actionSource == blueCards) {
                setDeckTheme("Blue");
            } else if (actionSource == blackCards) {
                setDeckTheme("Black");
            } else if (actionSource == goldCards) {
                setDeckTheme("Gold");
            } else if (actionSource == exitGame) {
                System.exit(0);
            }  else if (actionSource == gameTypeCBox) {
                gameType = gameTypeCBox.getSelectedIndex();
                setEnabledData(gameTypeCBox);
            } else if (actionSource == difficultyCBox) {
                difficulty = difficultyCBox.getSelectedIndex();
                setEnabledData(actionSource);
            } validate();
            repaint();
        } catch (Exception a) {
            a.printStackTrace();
            JOptionPane.showMessageDialog(null, a.getMessage());
        }
    }

    /**
     * Updates the dialog to change the enabled JComponents
     * @param jComponent
     */
    private void setEnabledData(final Object jComponent) {
        if (jComponent.getClass() == JCheckBox.class) {
            JCheckBox checkBox = ((JCheckBox) jComponent);
            updateUICheckBox(checkBox);
        } else if (jComponent.getClass() == JComboBox.class) {
            JComboBox comboBox = ((JComboBox) jComponent);
            updateUIComboBox(comboBox);
        } else if (jComponent.getClass() == JTextField.class) {
            JTextField textField = ((JTextField) jComponent);
            updateUITextField(textField);
        }
    }

    /**
     * Updates the dialog to change enabled text fields
     * @param textField The text field to be changed
     */
    private void updateUITextField(final JTextField textField) {
        if (textField.isEnabled() && textField.getText().isEmpty()) {
            textField.setFont(textField.getFont().deriveFont(Font.ITALIC));
            textField.setForeground(Color.LIGHT_GRAY);
            textField.setEnabled(false);
            textField.setText(getDefaultTextFieldData(textField));
        } else if (!textField.isEnabled()) {
            textField.setEnabled(true);
            textField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent focusEvent) {
                    textField.setFont(textField.getFont().deriveFont(Font.PLAIN));
                    textField.setForeground(Color.BLACK);
                    textField.setText(null);
                }

                @Override
                public void focusLost(FocusEvent focusEvent) {
                    if (textField.getText().isEmpty()) {
                        textField.setFont(textField.getFont().deriveFont(Font.ITALIC));
                        textField.setText(getDefaultTextFieldData(textField));
                        textField.setForeground(Color.LIGHT_GRAY);
                    }
                }
            });
        } else {
            textField.setEnabled(false);
        }
    }

    /**
     * Updates the gui to change enabled combo boxes
     * @param comboBox the combo box to be changed
     */
    private void updateUIComboBox(final JComboBox comboBox) {
        if (gameTypeCBox.getSelectedIndex() == 0 && difficultyCBox.getSelectedIndex() == 0
                && player1NameTF.getText().equals(playerTextDefault)) {
            gameTypeCBox.setEnabled(true);
            difficultyCBox.setEnabled(false);
            player1NameTF.setEnabled(false);
            player1NameTF.setForeground(Color.LIGHT_GRAY);
            player2NameTF.setEnabled(false);
            player2NameTF.setForeground(Color.LIGHT_GRAY);
        } else {
            if (comboBox == gameTypeCBox) {
                if (comboBox.getSelectedIndex() != 0) {
                    if (comboBox.getSelectedIndex() == 1) {
                        nameLabel.setForeground(Color.BLACK);
                        difficultyLabel.setForeground(Color.BLACK);
                        difficultyCBox.setEnabled(true);

                    } else {
                        difficultyLabel.setForeground(Color.LIGHT_GRAY);
                        nameLabel.setForeground(Color.BLACK);
                        nameLabel2.setForeground(Color.BLACK);
                        setEnabledData(player1NameTF);
                        setEnabledData(player2NameTF);
                        difficultyCBox.setEnabled(false);
                    }
                } else { difficultyCBox.setEnabled(false); }
            } else if (comboBox == difficultyCBox) {
                setEnabledData(player1NameTF);
            }
        }
    }

    /**
     * Updates the gui to change the selected checkboxes
     * @param checkBox  The checkbox to be updated
     */
    private void updateUICheckBox(final JCheckBox checkBox) {
        if (!defaultSizeCheckBox.isSelected() && !customSizeCheckBox.isSelected()) {
            numRowsTF.setText(String.valueOf(rowsDefault));
            numColumnsTF.setText(String.valueOf(columnsDefault));
            numRowsTF.setFont(numRowsTF.getFont().deriveFont(Font.ITALIC));
            numColumnsTF.setFont(numColumnsTF.getFont().deriveFont(Font.ITALIC));
            numRowsTF.setForeground(Color.LIGHT_GRAY);
            numColumnsTF.setForeground(Color.LIGHT_GRAY);
            numRowsTF.setEnabled(false);
            numColumnsTF.setEnabled(false);
            defaultSizeCheckBox.setSelected(true);
            customSizeCheckBox.setSelected(false);
            setEnabledData(player1NameTF);
            setEnabledData(player2NameTF);
            setEnabledData(numRowsTF);
            setEnabledData(numColumnsTF);
        } else if (checkBox == defaultSizeCheckBox) {
            numRowsTF.setText(String.valueOf(rowsDefault));
            numColumnsTF.setText(String.valueOf(columnsDefault));
            defaultSizeCheckBox.setSelected(true);
            customSizeCheckBox.setSelected(false);
            rowsLabel.setForeground(Color.LIGHT_GRAY);
            columnsLabel.setForeground(Color.LIGHT_GRAY);
        } else if (checkBox == customSizeCheckBox) {
            numRowsTF.setText(rowTextDefault);
            numColumnsTF.setText(columnTextDefault);
            defaultSizeCheckBox.setSelected(false);
            customSizeCheckBox.setSelected(true);
            rowsLabel.setForeground(Color.BLACK);
            columnsLabel.setForeground(Color.BLACK);
        } setEnabledData(numRowsTF);
        setEnabledData(numColumnsTF);
        validate();
    }

    /**
     * Retrieves the default data for the text field passed in
     *
     * @param textField new text field taken in
     * @return String value of the default message
     */
    String getDefaultTextFieldData(JTextField textField) {
        if (textField == player1NameTF) {
            return playerTextDefault;
        } else if (textField == numRowsTF) {
            return rowTextDefault;
        } else {
            return columnTextDefault;
        }
    }

    /**
     * Creates a new game by checking if the dialog data is valid, disposing
     * the initial dialog, setting the number of rows columns, and cards, passes
     * the specifications to the model, passes specifications to the AI, sets the
     * default card theme, calls the function to create the card buttons, and calls
     * the function to set the custom fonts.
     */
    private void createGame() {
        try {
            if (isValidInput()) {
                dialog.dispose();
                numRows = Integer.parseInt(numRowsTF.getText());
                numColumns = Integer.parseInt(numColumnsTF.getText());
                numCards = numRows * numColumns;
                cardFlipTimer = 1;
                game = new MatchingModel(numRows, numColumns);
                bot = new AIPlayer(numRows, numColumns, difficulty);
                setPlayerNames(player1NameTF.getText(), player2NameTF.getText());
                createCardButtons();
                setDeckTheme("Red");
                loadCustomFonts();
                validate();
                if (debugModeEnabled) {
                    System.out.print(game.revealAllCards());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Checks if the user input is valid
     *
     * @return True if valid
     * @throws Exception If input is invalid
     */
    private boolean isValidInput() throws Exception {
        if (difficultyCBox.getSelectedIndex() == 0) {
            if (!hasTwoUsers()) {
                throw new Exception("Error: Please select a difficulty.");
            }
        } if (gameTypeCBox.getSelectedIndex() == 0) {
            throw new Exception("Error: Please select a game type.");
        } if (numRowsTF.getText().isEmpty()
                || numRowsTF.getText().equals(rowTextDefault)) {
            throw new Exception("Error: Please enter a number of rows.");
        } if (numColumnsTF.getText().isEmpty()
                || numColumnsTF.getText().equals(columnTextDefault)) {
            throw new Exception("Error: Please enter a number of columns.");
        } if (player1NameTF.getText().isEmpty()
                || player1NameTF.getText().equals(playerTextDefault)) {
            throw new Exception("Error: Please enter your name.");
        } else {
            return true;
        }
    }

    /** Restarts game */
    private void resetGame() {
        ViewController.this.dispose();
        new ViewController();
    }

    /**
     * Makes a guess from the user data taken in, as well as add
     * those cards to the AI memory
     *
     * @param newActionSource The action event to find the source JButton
     */
    private void makeUserGuess(Object newActionSource) {
        if (guessCounter == 0) {
            guessCounter++;
        } for (int i = 0; i < game.getNumRows(); i++) {
            for (int j = 0; j < game.getNumColumns(); j++) {
                if (getGridButton(i, j) == newActionSource) {
                    setFaceImage(i, j);
                    if (guessCounter == 2) {
                        try {
                            row2 = i;
                            column2 = j;
                            game.makeMove(row1, column1, row2, column2);
                            updateUserResults();
                            int card2Location = (row2 * numColumns) + column2;
                            int cardValue2 = game.getCardData(row2, column2);
                            if (!bot.recallsIndividualCard(row2, column2, card2Location, cardValue2)
                                    && game.getCardData(row2, column2) != -1) {
                                bot.addCardToMemory(row2, column2, card2Location, cardValue2);
                            }
                        } catch (Exception a) {
                            JOptionPane.showMessageDialog(null, a.getMessage());
                        }
                    } else if (guessCounter == 1) {
                        row1 = i;
                        column1 = j;
                        int card1Location = (row1 * numColumns) + column1;
                        int cardValue1 = game.getCardData(row1, column1);
                        if (!bot.recallsIndividualCard(row1, column1, card1Location, cardValue1)
                                && game.getCardData(row1, column1) != -1) {
                            bot.addCardToMemory(row1, column1, card1Location, cardValue1);
                        } guessCounter++;
                        showResultLabel.setText("  ›  Make another guess...");
                        break;
                    } break;
                }
            }
        }
    }

    /**
     * Updates the user results panel and causes the AI to forget
     * cards that are now unavailable so that they are not guessed
     */
    private void updateUserResults() {
        disableMatchedButtons(row1, column1, row2, column2);
        if (game.matchedPair()) {
            if (game.gameOver()) {
                resultLabel.setText("GAME OVER");
                showResultLabel.setText("  ›  Winner: " + getPlayerName());
                JOptionPane.showMessageDialog(null, "Game Over\nWinner: " +
                        getPlayerName());
            } else {
                showResultLabel.setText("  ›  It's a match! Make another guess...");
                int card1Location = (botRow1 * numColumns) + botColumn1;
                int card2Location = (botRow2 * numColumns) + botColumn2;
                int cardValue1 = game.getCardData(botRow1, botColumn1);
                int cardValue2 = game.getCardData(botRow2, botColumn2);
                bot.forgetIndividualCard(botRow1, botColumn1, card1Location, cardValue1);
                bot.forgetIndividualCard(botRow2, botColumn2, card2Location, cardValue2);
                game.nullifyCard(row1, column1);
                game.nullifyCard(row2, column2);
                guessCounter = 0;
                p1ScoreLabel.setText("  ›  Player 1: " + game.getPlayer1Score());
                p2ScoreLabel.setText("  ›  Player 2: " + game.getPlayer2Score());
            }
        } else {
            bot.storeFailedPair(row1, column1, row2, column2);
            showResultLabel.setText("  ›  Sorry, not a match.");
            endUserTurn();
            guessCounter = 0;
        }
    }

    /**
     * Updates GUI for the end of the user's turn by
     * flipping the card back over after a specified amount
     * of time.
     */
    private void endUserTurn() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int counter = 0;

            @Override
            public void run() {
                disableAllButtons();
                if (counter > cardFlipTimer) {
                    flipToBackground(row1, column1);
                    flipToBackground(row2, column2);
                    enableAllButtons();
                    playerDisplayLabel.setText(getPlayerName());
                    if (!hasTwoUsers()) {
                        makeBotGuess();
                    }
                    timer.cancel();
                } counter++;
            }
        }, 0L, 1000L);
    }

    /**
     * Makes a guess for the AI by calling the AI's functions, checking the validity of the guess,
     * and makes the move if it is valid. It also removes successful guesses from its memory. It
     * also updates the GUI to allow us to view the GUI's turns
     */
    private void makeBotGuess() {
        int maxGuesses = 2;
        try {
            while (guessCounter < maxGuesses) {
                generateBotCards();
                if (game.isValidMove(row1, column1, row2, column2)) {
                    guessCounter++;
                    game.makeMove(row1, column1, row2, column2);
                    if (game.matchedPair()) {
                        setFaceImage(row1, column1);
                        setFaceImage(row2, column2);
                        disableMatchedButtons(row1, column1, row2, column2);
                        if (game.gameOver()) {
                            resultLabel.setText("GAME OVER");
                            showResultLabel.setText("  ›  Winner: " + getPlayerName());
                            JOptionPane.showMessageDialog(null, "Game Over\nWinner: " +
                                    getPlayerName());
                            break;
                        } else {
                            showResultLabel.setText("  ›  It's a match! Make another guess...");
                            p1ScoreLabel.setText("  ›  Player 1: " + game.getPlayer1Score());
                            p2ScoreLabel.setText("  ›  Player 2: " + game.getPlayer2Score());
                            int card1Location = (botRow1 * numColumns) + botColumn1;
                            int card2Location = (botRow2 * numColumns) + botColumn2;
                            int cardValue1 = game.getCardData(botRow1, botColumn1);
                            int cardValue2 = game.getCardData(botRow2, botColumn2);
                            bot.forgetIndividualCard(botRow1, botColumn1, card1Location, cardValue1);
                            bot.forgetIndividualCard(botRow2, botColumn2, card2Location, cardValue2);
                            game.nullifyCard(row1, column1);
                            game.nullifyCard(row2, column2);
                            maxGuesses++;
                            Thread.sleep(2000);
                        }
                    } else {
                        bot.storeFailedPair(botRow1, botColumn1, botRow2, botColumn2);
                        flipBotCards();
                        showResultLabel.setText("  ›  Sorry, not a match.");
                        guessCounter++;
                    }
                }
            }
        } catch (Exception a) {
            a.printStackTrace();
            JOptionPane.showMessageDialog(null, a.getMessage());
        }
    }

    /**
     * Creates the guesses made by the AI by calling them from
     * the AIPlayer class and assigning them to local variables, as long
     * as they cards haven't already been guessed previously
     */
    private void generateBotCards() {
        boolean logicalGuess = false;
        try {
            while (!logicalGuess) {
                bot.makeIntelligentGuess();
                botRow1 = bot.getRow1Guess();
                botColumn1 = bot.getColumn1Guess();
                botRow2 = bot.getRow2Guess();
                botColumn2 = bot.getColumn2Guess();
                int botCard1 = (botRow1 * numColumns) + botColumn1;
                int botCard2 = (botRow2 * numColumns) + botColumn2;
                if (!bot.remembersFailedPair(botCard1, botCard2)) {
                    logicalGuess = true;
                }
            } row1 = botRow1;
            column1 = botColumn1;
            row2 = botRow2;
            column2 = botColumn2;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /** flips over the cards that the bot plays after a timer */
    private void flipBotCards() {
        final Timer timer = new Timer();
        setFaceImage(row1, column1);
        setFaceImage(row2, column2);
        timer.schedule(new TimerTask() {
            private int counter = 0;

            @Override
            public void run() {
                if (counter > cardFlipTimer) {
                    setFaceImage(row1, column1);
                    setFaceImage(row2, column2);
                } else {
                    final Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        private int counter2 = 0;

                        @Override
                        public void run() {
                            disableAllButtons();
                            if (counter2 > cardFlipTimer) {
                                flipToBackground(row1, column1);
                                flipToBackground(row2, column2);
                                enableAllButtons();
                                guessCounter = 0;
                                timer2.cancel();
                                playerDisplayLabel.setText(getPlayerName());
                            } counter2++;
                        }
                    }, 0L, 1000L);
                    timer.cancel();
                } counter++;
            }
        }, 0L, 1000L);
    }

    /**
     * Sets the default background to the picture in the specified path
     * @param newDefault    The path of the picture
     */
    private void setDefaultCardBackground(String newDefault) {
        cardBackground = newDefault;
    }

    /**
     * Sets the image of each card corresponding to the card's value.
     * Cards such as King and Queen are assigned to values such as "12" and "13".
     * Any numbers higher than that are not given a card image, but instead just
     * display the card's value in text.
     *
     * @param row       The row of the card to add image
     * @param column    The column of the card to add image
     */
    private void setFaceImage(int row, int column) {
        int cardData = game.getCardData(row, column);
        String iconPath;
        try {
            if (cardData < 14) {
                iconPath = "Resources/Images/" + cardData + ".png";   // Path to image file in src
                if (debugModeEnabled) {
                    System.out.print("\n> Opening image file from location: \"" + iconPath + "\".");
                } ImageIcon faceIcon = getImgResource(iconPath);
                getGridButton(row, column).setIcon(faceIcon);
            } else {
                getGridButton(row, column).setIcon(null);
                getGridButton(row, column).setFont(this.getFont().deriveFont(Font.BOLD, 28.0f));
                getGridButton(row, column).setText(String.valueOf(game.getCardData(row, column)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /** Disables all JButtons */
    private void disableAllButtons() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                getGridButton(i, j).removeActionListener(this);
            }
        }
    }

    /** Enables all JButtons */
    private void enableAllButtons() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                getGridButton(i, j).addActionListener(this);
            }
        }
    }

    /**
     * Disables the buttons if they are a matched pair
     *
     * @param dRow1     card 1 row
     * @param dColumn1  card 1 column
     * @param dRow2     card 2 row
     * @param dColumn2  card 2 column
     */
    private void disableMatchedButtons(int dRow1, int dColumn1, int dRow2, int dColumn2) {
        getGridButton(dRow1, dColumn1).removeActionListener(this);
        getGridButton(dRow2, dColumn2).removeActionListener(this);
    }

    /**
     * Retrieves the grid button of the specified card index
     *
     * @param rowNumber     The row of the card
     * @param columnNumber  The column of the card
     * @return              The corresponding JButton
     */
    private JButton getGridButton(int rowNumber, int columnNumber) {
        int buttonIndex = (rowNumber * numColumns) + columnNumber;
        return cardButtons.get(buttonIndex);
    }

    /**
     * Creates JButtons for each card according to the number of rows and columns
     * specified by the user. It also changes the size of the JButtons if the
     * number is large, allowing for it to display correctly within the panel.
     */
    private void createCardButtons() {
        cardsLayout = new GridLayout(game.getNumRows(), game.getNumColumns());
        displayCards = new JPanel(cardsLayout);
        int numCards = numRows * numColumns;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                JButton newCard = new JButton();
                newCard.addActionListener(this);
                newCard.setContentAreaFilled(false);
                if (numCards < 25) {
                    newCard.setPreferredSize(new Dimension(80, 125));
                } else if (numCards < 50) {
                    newCard.setPreferredSize(new Dimension(60, 85));
                } else {
                    newCard.setPreferredSize(new Dimension(30, 65));
                } cardButtons.add(newCard);
                displayCards.add(newCard);
            }
        }
    }

    /**
     * Assigns local player name variables to the Strings taken in
     *
     * @param newPlayer1    Player 1's name
     * @param newPlayer2    Player 2's name
     */
    private void setPlayerNames(String newPlayer1, String newPlayer2) {
        player1Name = newPlayer1;
        if (!hasTwoUsers()) {
            player2Name = "Computer";
        } else {
            player2Name = newPlayer2;
        }
    }

    /**
     * Retrieves the name of the current player
     * @return  The name of the player
     */
    private String getPlayerName() {
        if (game.getCurrentPlayer() == 1) {
            return player1Name;
        } else {
            return player2Name;
        }
    }

    /**
     * Sets the theme of the deck according to the JMenuItem clicked by the user
     * @param deckTheme The theme specified
     */
    private void setDeckTheme(String deckTheme) {
        setDefaultCardBackground(deckTheme);
        String picLocation = null;
        try {
            if (deckTheme.equals("Red")) {
                picLocation = "Resources/Images/RedCard.png";
            } else if (deckTheme.equals("Blue")) {
                picLocation = "Resources/Images/BlueCard.png";
            } else if (deckTheme.equals("Black")) {
                picLocation = "Resources/Images/BlackCard.png";
            } else if (deckTheme.equals("Gold")) {
                picLocation = "Resources/Images/GoldCard.png";
            } ImageIcon cardIcon = getImgResource(picLocation);
            if (debugModeEnabled) {
                System.out.print("\n> Opening image file from location: \"" + picLocation + "\".");
            } for (int i = 0; i < game.getNumRows(); i ++) {
                for (int j = 0; j < game.getNumColumns(); j++) {
                    if (game.getCardData(i, j) != -1) {
                        getGridButton(i, j).setIcon(cardIcon);
                        mainPanel.repaint();
                        mainPanel.revalidate();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * "Flips" a card over/changes the icon image to a card back to
     * give the illusion that it is flipping over
     *
     * @param row       The row of a specified card
     * @param column    The column of a specified card
     */
    private void flipToBackground(int row, int column) {
        int cardData = game.getCardData(row, column);
        String picLocation = null;
        try {
            if (cardBackground.equals("Red")) {
                picLocation = "Resources/Images/RedCard.png";
            } else if (cardBackground.equals("Blue")) {
                picLocation = "Resources/Images/BlueCard.png";
            } else if (cardBackground.equals("Black")) {
                picLocation = "Resources/Images/BlackCard.png";
            } else if (cardBackground.equals("Gold")) {
                picLocation = "Resources/Images/GoldCard.png";
            } if (debugModeEnabled) {
                System.out.print("\n> Opening image file from location: \" " + picLocation + "\"n");
            } ImageIcon cardIcon = getImgResource(picLocation);

            if (cardData > 13) {
                getGridButton(row, column).setText(null);
            } getGridButton(row, column).setIcon(cardIcon);
            mainPanel.repaint();
            mainPanel.revalidate();
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Retrieves an image icon to be used in other methods from the location
     * passed in as a string.
     *
     * @param picLocation   The path of the desired image
     * @return              The ImageIcon
     * @throws Exception    If the image is not contained in the files
     */
    private ImageIcon getImgResource(String picLocation) throws Exception {
        BufferedImage cardImage = ImageIO.read(getClass().getResource(picLocation));
        ImageIcon cardIcon = new ImageIcon(cardImage);
        Image newCardImage = cardIcon.getImage();
        int sizeDivisor;
        if (numCards < 25) {
            sizeDivisor = 4;
        } else if (numCards < 50) {
            sizeDivisor = 5;
        } else {
            sizeDivisor = 6;
        } setDataViewSize(sizeDivisor);
        newCardImage = newCardImage.getScaledInstance(newCardImage.getWidth(null) / sizeDivisor,
                newCardImage.getHeight(null) / sizeDivisor, Image.SCALE_SMOOTH);
        cardIcon.setImage(newCardImage);
        return cardIcon;
    }

    /**
     * Sets the window size according to the size
     * of the game board.
     *
     * @param newSizeDivisor The divisor
     */
    private void setDataViewSize(int newSizeDivisor) {
        Dimension viewDimension;
        if (newSizeDivisor == 4) {
            viewDimension = new Dimension(1100, 725);
        } else if (newSizeDivisor == 6) {
            viewDimension = new Dimension(1200, 900);
        } else {
            viewDimension = new Dimension(1400, 900);
        } layeredViewPane.setPreferredSize(viewDimension);
    }

    /**
     * Sets the default time it takes for a card to be displayed
     * before it is flipped back over
     *
     * @param seconds Time before being flipped.
     */
    private void setDefaultTimer(int seconds) {
        cardFlipTimer = seconds;
    }

    /**
     * Loads custom font files from resources folder
     */
    private void loadCustomFonts() {
        try {
            defaultFont = getFont();
            String westernFontPath = "Resources/Fonts/Western.ttf";
            String tropicalFontPath = "Resources/Fonts/Tropical.ttf";
            if (debugModeEnabled) {
                System.out.print("\n> Opening font file from location: \"" + westernFontPath + "\".");
            } westernFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(westernFontPath));
            if (debugModeEnabled) {
                System.out.print("\n> Opening font file from location: \"" + tropicalFontPath + "\".");
            } tropicalFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(tropicalFontPath));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Unable to load custom fonts.");
        }
    }

    /**
     * Sets the app background to the one specified in the menu bar
     * @param newTheme  The title of the new theme.
     */
    private void setAppBackground(String newTheme) {
        if (newTheme.equals(menuBarObj.getDefaultWP().getText())) {
            topBanner.setText("♥               ♠            - " +
                    "CONCENTRATION -            ♦               ♣");
            topBanner.setForeground(lightGold);
            topBanner.setBackground(darkGreen);
            topBanner.setFont(defaultFont);
            topBanner.setFont(topBanner.getFont().deriveFont(Font.PLAIN, 26.0f));
            topBanner.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, lightGold));
            resultLabel.setBackground(darkGreen);
            resultLabel.setForeground(lightGold);
            showResultLabel.setBackground(darkGreen);
            showResultLabel.setForeground(lightGold);
            playerLabel.setBackground(darkGreen);
            playerLabel.setForeground(lightGold);
            scoreLabel.setBackground(darkGreen);
            scoreLabel.setForeground(lightGold);
            p1ScoreLabel.setBackground(darkGreen);
            p1ScoreLabel.setForeground(lightGold);
            p2ScoreLabel.setBackground(darkGreen);
            p2ScoreLabel.setForeground(lightGold);
            playerDisplayLabel.setBackground(darkGreen);
            playerDisplayLabel.setForeground(lightGold);
            displayCards.setBackground(darkGreen);
            displayCards.setForeground(lightGold);
            displayCards.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, darkGreen));
        } if (newTheme.equals(menuBarObj.getTropical().getText())) {
            topBanner.setText("CONCENTRATION");
            resultLabel.setBackground(darkBlue);
            resultLabel.setForeground(lightBlue);
            showResultLabel.setBackground(darkBlue);
            showResultLabel.setForeground(lightBlue);
            topBanner.setForeground(lightBlue);
            topBanner.setBackground(darkBlue);
            topBanner.setFont(tropicalFont);
            topBanner.setFont(topBanner.getFont().deriveFont(Font.BOLD, 28.0f));
            topBanner.setBorder(null);
            playerLabel.setBackground(darkBlue);
            playerLabel.setForeground(lightBlue);
            scoreLabel.setBackground(darkBlue);
            scoreLabel.setForeground(lightBlue);
            p1ScoreLabel.setBackground(darkBlue);
            p1ScoreLabel.setForeground(lightBlue);
            p2ScoreLabel.setBackground(darkBlue);
            p2ScoreLabel.setForeground(lightBlue);
            playerDisplayLabel.setBackground(darkBlue);
            playerDisplayLabel.setForeground(lightBlue);
            displayCards.setBackground(darkBlue);
            displayCards.setForeground(lightBlue);
            displayCards.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, darkBlue));
        } if (newTheme.equals(menuBarObj.getWestern().getText())) {
            topBanner.setText("CONCENTRATION");
            topBanner.setForeground(lightGold);
            topBanner.setBackground(darkBrown);
            topBanner.setFont(westernFont);
            topBanner.setFont(topBanner.getFont().deriveFont(Font.BOLD, 30.0f));
            topBanner.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, lightGold));
            resultLabel.setBackground(darkBrown);
            resultLabel.setForeground(lightGold);
            showResultLabel.setBackground(darkBrown);
            showResultLabel.setForeground(lightGold);
            playerLabel.setBackground(darkBrown);
            playerLabel.setForeground(lightGold);
            scoreLabel.setBackground(darkBrown);
            scoreLabel.setForeground(lightGold);
            p1ScoreLabel.setBackground(darkBrown);
            p1ScoreLabel.setForeground(lightGold);
            p2ScoreLabel.setBackground(darkBrown);
            p2ScoreLabel.setForeground(lightGold);
            playerDisplayLabel.setBackground(darkBrown);
            playerDisplayLabel.setForeground(lightGold);
            displayCards.setBackground(darkBrown);
            displayCards.setForeground(lightGold);
            displayCards.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, darkBrown));
        } layeredViewPane.remove(backgroundPanel);
        backgroundPanel = new JBackgroundPanel(newTheme);
        layeredViewPane.add(backgroundPanel, BorderLayout.CENTER);
        layeredViewPane.revalidate();
        layeredViewPane.repaint();
    }

    /** Main method */
    public static void main(String[] args) {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {/* Do nothing*/}
            ViewController gui = new ViewController();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "    * Error: Unable to initialize game *");
        }
    }

    /** sets the game type of the game */
    private void setGameType() {
        gameType = gameTypeCBox.getSelectedIndex();
    }

    /**
     * Checks if the game is singleplayer or multiplayer
     * @return True if multiplayer
     */
    private boolean hasTwoUsers() {
        return gameType == 2;
    }

    /**
     * Enables debug mode for console
     */
    private void enableDebugMode() {
        if (!debugModeEnabled) {
            viewDebugConsole();
            bot.enableDebugMode();
            game.enableDebugMode();
            consoleView.toFront();
            System.out.print(
                    "\n__________________" +
                            "\n| Revealed Cards |" +
                            "\n'----------------'\n");
            System.out.print(game.revealAllCards());
            System.out.print("------------------\n\n");
            debugModeEnabled = true;
        }
    }
}
