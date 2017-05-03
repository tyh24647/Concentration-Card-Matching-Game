import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ConsoleOutput class extends the OutputStream, but allows for the
 * console data to be printed on a JTextArea so that debug mode can be
 * viewed when running the jar file independently from an IDE
 *
 * @author Tyler Hostager
 * @version 12/18/14
 */
public class ConsoleOutput extends OutputStream {
    private JTextArea console;

    /**
     * Constructs output using a new JTextArea
     * @param newConsole the new text area taken in
     */
    public ConsoleOutput(JTextArea newConsole) {
        console = newConsole;
        console.setEditable(false);
        console.setForeground(Color.WHITE);
        console.setBackground(Color.BLACK);
    }

    /**
     * Writes data to the output stream
     *
     * @param b the int taken in
     * @throws IOException  If it cannot write data
     */
    public void write(int b) throws IOException {
        console.append(String.valueOf((char) b));
        console.setCaretPosition(console.getDocument().getLength());
    }
}
