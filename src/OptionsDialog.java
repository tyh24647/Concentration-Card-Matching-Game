import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * OptionsDialog creates a JDialog object that is presented to the user
 * once they start the program. This allows for them to specify the game options
 * that affect the GUI's attributes.
 *
 * @author Tyler Hostager
 * @version 12/17/14
 */
public class OptionsDialog extends JDialog {
    /** Values for the default number of rows and columns */
    private Integer rowsDefault, columnsDefault;

    /** Panels to be placed in the dialog */
    private JPanel sCenterPanel, startupPanel, sBottomPanel;

    /** Submit button */
    private JButton submitButton;

    /** Text fields for the player names and dimensions */
    private JTextField player1NameTF, player2NameTF, numRowsTF, numColumnsTF;

    /** Check boxes to specify dimensions */
    private JCheckBox defaultSizeCheckBox, customSizeCheckBox;

    /** Combo boxes for selecting difficulty and game type */
    private JComboBox gameTypeCBox, difficultyCBox;

    /** Labels for specific attributes */
    private JLabel rowsLabel, columnsLabel, startupBanner, attributesLabel, sLeftLabel,
            sRightLabel, difficultyLabel, sizeLabel, nameLabel, nameLabel2, fillerLabel1,
            gameTypeLabel, fillerLabel2;

    /** Default strings to be placeholders */
    private String playerTextDefault, rowTextDefault, columnTextDefault;

    /** array values to fill combo boxes */
    private String[] difficultyOptions, gameTypeOptions;

    /** border to be used for gui elements */
    private Border headerBorder;

    /** Constructs the options dialog */
    public OptionsDialog() {
        headerBorder = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY);
        setTextFieldDefaults();
        generateJPanels();
        initializeTextFields();
        generateCheckBoxes();
        generateJLabels();
        setTopBanner();
    }

    /** Adds this panel to JDialog */
    void addStartupPanel() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        add(startupPanel);
        setTitle("Game Options");
        pack();
        setVisible(true);
    }

    /** Populates panel with sub-panels */
    void populateStartupPanel() {
        startupPanel.add(startupBanner, BorderLayout.NORTH);
        startupPanel.add(sCenterPanel, BorderLayout.CENTER);
        startupPanel.add(sBottomPanel, BorderLayout.SOUTH);
        startupPanel.add(sLeftLabel, BorderLayout.WEST);
        startupPanel.add(sRightLabel, BorderLayout.EAST);
    }

    /**
     * Creates combo boxes using the arguments taken in
     *
     * @param newGTCBox The game type options.
     * @param newDCBox  The difficulty options.
     */
    void generateComboBoxes(JComboBox newGTCBox, JComboBox newDCBox) {
        gameTypeCBox = newGTCBox;
        difficultyCBox = newDCBox;
    }

    /** Adds JComponents to center panel */
    void setSCenterPanel() {
        sCenterPanel.add(attributesLabel);
        sCenterPanel.add(fillerLabel1);
        sCenterPanel.add(gameTypeLabel);
        sCenterPanel.add(gameTypeCBox);
        sCenterPanel.add(difficultyLabel);
        sCenterPanel.add(difficultyCBox);
        sCenterPanel.add(nameLabel);
        sCenterPanel.add(player1NameTF);
        sCenterPanel.add(nameLabel2);
        sCenterPanel.add(player2NameTF);
        sCenterPanel.add(sizeLabel);
        sCenterPanel.add(fillerLabel2);
        sCenterPanel.add(defaultSizeCheckBox);
        sCenterPanel.add(customSizeCheckBox);
        sCenterPanel.add(rowsLabel);
        sCenterPanel.add(numRowsTF);
        sCenterPanel.add(columnsLabel);
        sCenterPanel.add(numColumnsTF);
        sCenterPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1, 1, 1, 1, Color.GRAY
                )
        );
    }

    /** Creates check boxes */
    protected void generateCheckBoxes() {
        defaultSizeCheckBox = new JCheckBox(" Default");
        customSizeCheckBox = new JCheckBox(" Custom");
    }

    /** Creates JPanels */
    protected void generateJPanels() {
        startupPanel = new JPanel(new BorderLayout(6, 5));
        sCenterPanel = new JPanel(new GridLayout(9, 2));
        sBottomPanel = new JPanel();
    }

    /** Creates JLabels */
    protected void generateJLabels() {
        attributesLabel = new JLabel(" Player Attributes:");
        gameTypeLabel = new JLabel(" Game Type:");
        difficultyLabel = new JLabel(" Difficulty:");
        nameLabel = new JLabel(" Player 1 Name:");
        nameLabel2 = new JLabel(" Player 2 Name:");
        sizeLabel = new JLabel(" Size:");
        rowsLabel = new JLabel(" Number of rows:");
        columnsLabel = new JLabel(" Number of Columns:");
        sizeLabel.setFont(sizeLabel.getFont().deriveFont(Font.BOLD, 18.0f));
        sizeLabel.setBackground(Color.LIGHT_GRAY);
        sizeLabel.setOpaque(true);
        sizeLabel.setBorder(headerBorder);
        nameLabel.setForeground(Color.LIGHT_GRAY);
        nameLabel2.setForeground(Color.LIGHT_GRAY);
        difficultyLabel.setForeground(Color.LIGHT_GRAY);
        fillerLabel1 = new JLabel();
        fillerLabel1.setBorder(headerBorder);
        fillerLabel1.setBackground(Color.LIGHT_GRAY);
        fillerLabel1.setOpaque(true);
        fillerLabel2 = new JLabel();
        fillerLabel2.setBackground(Color.LIGHT_GRAY);
        fillerLabel2.setOpaque(true);
        fillerLabel2.setBorder(headerBorder);
        sLeftLabel = new JLabel();
        sLeftLabel.setPreferredSize(new Dimension(25, 0));
        sRightLabel = new JLabel();
        sRightLabel.setPreferredSize(new Dimension(25, 0));
        attributesLabel.setFont(attributesLabel.getFont().deriveFont(Font.BOLD, 18.0f));
        attributesLabel.setBorder(headerBorder);
        attributesLabel.setBackground(Color.LIGHT_GRAY);
        attributesLabel.setOpaque(true);
        rowsLabel.setForeground(Color.LIGHT_GRAY);
        columnsLabel.setForeground(Color.LIGHT_GRAY);
    }

    /** Creates bottom panel */
    protected void setBottomPanel() {
        setSubmitButton();
        sBottomPanel.add(new JLabel());
        sBottomPanel.add(submitButton);
        sBottomPanel.add(new JLabel());
    }

    /** creates submit button */
    private void setSubmitButton() {
        submitButton = new JButton("Submit");
        submitButton.setOpaque(true);
        submitButton.setHorizontalAlignment(SwingConstants.CENTER);
        submitButton.setMargin(new Insets(0, 10, 0, 10));
    }

    /** creates text fields */
    protected void initializeTextFields() {
        player1NameTF = new JTextField(10);
        player2NameTF = new JTextField(10);
        numRowsTF = new JTextField(10);
        numColumnsTF = new JTextField(10);
    }

    /** creates top banner */
    protected void setTopBanner() {
        startupBanner = new JLabel("- Initial Setup -");
        startupBanner.setFont(startupBanner.getFont().deriveFont(24.0f));
        startupBanner.setPreferredSize(new Dimension(0, 40));
        startupBanner.setHorizontalAlignment(SwingConstants.CENTER);
        startupBanner.setVerticalAlignment(SwingConstants.CENTER);
    }

    /** creates default text for text fields */
    protected void setTextFieldDefaults() {
        playerTextDefault = "- Your Name Here -";
        rowTextDefault = "Desired # of Rows";
        columnTextDefault = "Desired # of Columns";
        rowsDefault = 4;
        columnsDefault = 6;
    }

    /**
     * Assigns difficulty options to variables passed in.
     * @param newOptions    The difficulty options.
     */
    protected void setDifficultyOptions(final String[] newOptions) {
        difficultyOptions = newOptions;
    }

    /**
     * Assigns game type options to local variables from the array passed in.
     * @param newOptions    Game type options array
     */
    protected void setGameTypeOptions(final String[] newOptions) {
        gameTypeOptions = newOptions;
    }

    /**
     * Retrieves the default amount of rows
     * @return  Default number of rows
     */
    public Integer getRowsDefault() {
        return rowsDefault;
    }

    /**
     * Retrieves default number of columns
     * @return  number of columns
     */
    public Integer getColumnsDefault() {
        return columnsDefault;
    }

    /**
     * Retrieves player text field placeholder string
     * @return  placeholder string
     */
    public String getPlayerTextDefault() {
        return playerTextDefault;
    }

    /**
     * Retrieves placeholder text for number of rows
     * @return  placeholder text
     */
    public String getRowTextDefault() {
        return rowTextDefault;
    }

    /**
     * Retrieves placeholder text for number of columns
     * @return  placeholder text
     */
    public String getColumnTextDefault() {
        return columnTextDefault;
    }

    /**
     * Retrieves submit button
     * @return  submit button.
     */
    public JButton getSubmitButton() {
        return submitButton;
    }

    /**
     * Retrieves default size check box
     * @return  default size check box
     */
    public JCheckBox getDefaultSizeCheckBox() {
        return defaultSizeCheckBox;
    }

    /**
     * Retrieves the custom size checkbox
     * @return  the JCheckbox
     */
    public JCheckBox getCustomSizeCheckBox() {
        return customSizeCheckBox;
    }

    /**
     * Retrieves the player 1 name text field
     * @return player 1 name text field
     */
    public JTextField getPlayer1NameTF() {
        return player1NameTF;
    }

    /**
     * Retrieves the player 2 name text field
     * @return player 2 name text field
     */
    public JTextField getPlayer2NameTF() {
        return player2NameTF;
    }

    /**
     * Retrieves the number of rows text field
     * @return  text field
     */
    public JTextField getNumRowsTF() {
        return numRowsTF;
    }

    /**
     * Retrieves the number of columns text field
     * @return  text field
     */
    public JTextField getNumColumnsTF() {
        return numColumnsTF;
    }

    /**
     * Retrieves the number of rows label
     * @return  rows label
     */
    public JLabel getRowsLabel() {
        return rowsLabel;
    }

    /**
     * Retrieves the number of columns label
     * @return  columns label
     */
    public JLabel getColumnsLabel() {
        return columnsLabel;
    }

    /**
     * Retrieves the name label
     * @return  name label
     */
    public JLabel getNameLabel() {
        return nameLabel;
    }

    /**
     * Retrieves 2nd name label
     * @return  2nd name label
     */
    public JLabel getNameLabel2() {
        return nameLabel2;
    }

    /**
     * Retrieves the difficulty label
     * @return  difficulty label
     */
    public JLabel getDifficultyLabel() {
        return difficultyLabel;
    }
}
