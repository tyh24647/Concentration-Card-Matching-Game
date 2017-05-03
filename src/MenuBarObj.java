import javax.swing.*;

/**
 * MenuBarObj class is a menu bar object that contains specific functions
 * in order to access its' specific data.
 *
 * @author Tyler Hostager
 * @version 12/17/14
 */
public class MenuBarObj extends JMenuBar {
    /** Menu bar being created */
    protected JMenuBar menuBar;

    /**
     * Specific menus for the options: "file", "Edit", "Theme", and "window"
     * as well as the sub-menus to change the difficulty and set a card's theme.
     */
    private JMenu fileMenu, editMenu, themeMenu, windowMenu, cardSubMenu, changeDifficulty;

    /**
     * Creates menu items to reset/exit the game, as well as
     * edit specific GUI options.
     */
    private JMenuItem newGame, exitGame, changeTimer, redCards, blueCards, blackCards,
            goldCards, debugMode, defaultTime, extendedTime, longTime, novice, easy,
            medium, hard, extreme, tropical, western, defaultWP;

    /** Constructs menu bar object. */
    public MenuBarObj() {
        menuBar = new JMenuBar();
        generateMenus();
        add(fileMenu);
        add(editMenu);
        add(themeMenu);
        add(windowMenu);
    }

    /** Creates the menus and calls the functions to add menu elements */
    private void generateMenus() {
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        themeMenu = new JMenu("Theme");
        windowMenu = new JMenu("Window");
        addFileMenuElements();
        addEditMenuElements();
        addThemeMenuElements();
        addWindowElements();
    }

    /** Adds elements to the file menu */
    private void addFileMenuElements() {
        newGame = new JMenuItem("Reset Game");
        exitGame = new JMenuItem("Exit Program");
        fileMenu.add(newGame);
        fileMenu.add(exitGame);
    }

    /** Adds elements to the edit menu */
    private void addEditMenuElements() {
        changeTimer = new JMenu("Change Timer (card flip)");
        changeDifficulty = new JMenu("Change Difficulty");
        addEditSubElements();
        editMenu.add(changeTimer);
        editMenu.add(changeDifficulty);
    }

    /** Adds elements to the edit's submenus */
    private void addEditSubElements() {
        defaultTime = new JMenuItem("Default");
        extendedTime = new JMenuItem("Extended");
        longTime = new JMenuItem("Long");
        novice = new JMenuItem("Pathetic");
        easy = new JMenuItem("Easy");
        medium = new JMenuItem("Medium");
        hard = new JMenuItem("Hard");
        extreme = new JMenuItem("Extreme");
        changeTimer.add(defaultTime);
        changeTimer.add(extendedTime);
        changeTimer.add(longTime);
        changeDifficulty.add(novice);
        changeDifficulty.add(easy);
        changeDifficulty.add(medium);
        changeDifficulty.add(hard);
        changeDifficulty.add(extreme);
    }

    /** Adds elements to the theme menu */
    private void addThemeMenuElements() {
        cardSubMenu = new JMenu("Cards");
        defaultWP = new JMenuItem("Default");
        tropical = new JMenuItem("Tropical");
        western = new JMenuItem("Western");
        themeMenu.add(defaultWP);
        themeMenu.add(tropical);
        themeMenu.add(western);
        addThemeSubElements();
        themeMenu.addSeparator();
        themeMenu.add(cardSubMenu);
    }

    /** Adds elements to the theme's submenus */
    private void addThemeSubElements() {
        redCards = new JMenuItem("Red (Default)");
        blueCards = new JMenuItem("Blue");
        blackCards = new JMenuItem("Black");
        goldCards = new JMenuItem("Gold");
        cardSubMenu.add(redCards);
        cardSubMenu.add(blueCards);
        cardSubMenu.add(blackCards);
        cardSubMenu.add(goldCards);
    }

    /** Adds elements to the window menu */
    private void addWindowElements() {
        debugMode = new JMenuItem("Debug Mode");
        windowMenu.add(debugMode);
    }

    /**
     * Retrieves menu item for setting the card flip time to default.
     * @return  The default time menu item
     */
    public JMenuItem getDefaultTime() {
        return defaultTime;
    }

    /**
     * Retrieves menu item to set card flip time to longer than the default.
     * @return  The extended time menu item
     */
    public JMenuItem getExtendedTime() {
        return extendedTime;
    }

    /**
     * Retrieves menu item to set card flip time to longer than extended.
     * @return  The menu item for a long time.
     */
    public JMenuItem getLongTime() {
        return longTime;
    }

    /**
     * Retrieves menu item to reset game.
     * @return  Menu item for new game.
     */
    public JMenuItem getNewGame() {
        return newGame;
    }

    /**
     * Retrieves menu item to exit game.
     * @return  Menu item to exit game.
     */
    public JMenuItem getExitGame() {
        return exitGame;
    }

    /**
     * Retrieves menu item for default background.
     * @return  Default background menu item.
     */
    public JMenuItem getDefaultWP() {
        return defaultWP;
    }

    /**
     * Retrieves menu item for tropical background.
     * @return  tropical menu item.
     */
    public JMenuItem getTropical() {
        return tropical;
    }

    /**
     * Retrieves menu item for western background.
     * @return  western menu item.
     */
    public JMenuItem getWestern() {
        return western;
    }

    /**
     * Retrieves menu item for red cards.
     * @return  Red cards menu item.
     */
    public JMenuItem getRedCards() {
        return redCards;
    }

    /**
     * Retrieves menu item for blue cards.
     * @return  Blue cards menu item.
     */
    public JMenuItem getBlueCards() {
        return blueCards;
    }

    /**
     * Retrieves menu item for black cards.
     * @return  Black cards menu item.
     */
    public JMenuItem getBlackCards() {
        return blackCards;
    }

    /**
     * Retrieves menu item for gold cars.
     * @return  Gold cards menu item.
     */
    public JMenuItem getGoldCards() {
        return goldCards;
    }

    /**
     * Retrieves menu item for debug mode.
     * @return  Debug mode menu item.
     */
    public JMenuItem getDebugMode() {
        return debugMode;
    }
}
