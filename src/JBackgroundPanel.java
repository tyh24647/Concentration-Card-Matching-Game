import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * JBackgroundPanel class creates a JPanel object that allows for it to
 * be painted by an image file, which lets the user specify a background
 * image for the GUI.
 *
 * @author Tyler Hostager
 * @version 12/14/14
 */
public class JBackgroundPanel extends JPanel {
    /** The location of the background image */
    private String imageLocation;

    /** The image to be set */
    private Image bImage;

    /** If enabled, allows console output */
    private boolean debugModeEnabled = false;

    /** Default constructor - loads default background */
    public JBackgroundPanel() {
        imageLocation = "/Resources/Images/Backgrounds/Default.jpg";
        locateImage(imageLocation);
    }

    /**
     * Creates background panel object and paints a specified image.
     * @param imageTitle    The title of the image to be found.
     */
    public JBackgroundPanel(String imageTitle) {
        imageLocation = "Resources/Images/Backgrounds/" + imageTitle + ".jpg";
        locateImage(imageLocation);
    }

    /**
     * Searches resource files for the image in the specified location.
     * @param imageLocation The file path of the image to be found.
     */
    public void locateImage(String imageLocation) {
        try {
            bImage = ImageIO.read(getClass().getResource(imageLocation));
            if (debugModeEnabled) {
                System.out.print("\n> Opening image file from location: \"src/" + imageLocation + "\".");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } setOpaque(true);
    }

    /** Enables printing to the console when called */
    public void enableDebugMode() {
        debugModeEnabled = true;
    }

    /**
     * Paints the JPanel with the specified image.
     * @param g The graphics of the JPanel.
     */
    public void paintComponent(Graphics g) {
        g.drawImage(bImage, 0, 0, getWidth(), getHeight(), null);
    }
}
