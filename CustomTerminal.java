import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class CustomTerminal {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Custom Terminal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a text area for the terminal
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setEditable(false); // Make the text area read-only

        // Set initial rows and columns
        textArea.setRows(20); // Number of rows (lines)
        textArea.setColumns(80); // Number of columns (characters)

        // Add the text area to a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Get the size of the text area in pixels
                Dimension size = scrollPane.getViewport().getSize();

                // Get font metrics to calculate rows and columns
                FontMetrics metrics = textArea.getFontMetrics(textArea.getFont());
                int rowHeight = metrics.getHeight();
                int columnWidth = metrics.charWidth('W'); // Approximation using widest character

                // Calculate rows and columns
                int rows = size.height / rowHeight;
                int columns = size.width / columnWidth;

                // Output the dimensions to the terminal
                textArea.setText(""); // Clear text
                textArea.append("Window resized!\n");
                textArea.append("Rows: " + rows + "\n");
                textArea.append("Columns: " + columns + "\n");
            }
        });

        // Add a key listener to capture key inputs (if needed for game commands)
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Do nothing (prevent user typing directly into terminal)
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle key commands here, e.g., game logic
                System.out.println("Key pressed: " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Do nothing
            }
        });

        // Make the frame visible
        frame.setVisible(true);
        

        // Example: Dynamically update the terminal and clear on every update
        new Timer(100, e -> {
            // Clear the terminal
            textArea.setText(""); 

            // Add new content to the terminal
            textArea.append("Game update at " + System.currentTimeMillis() + "\n");

            // Auto-scroll to the bottom
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }).start();
    }
}
