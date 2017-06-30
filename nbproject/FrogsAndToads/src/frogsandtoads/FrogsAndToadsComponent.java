package frogsandtoads;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A Graphics version of the game Frogs and Toads.
 *
 * @author Sadan Mallhi
 * @version Apr 2, 2014
 *
 * I certify that I wrote all of the code in this file myself.
 */
public class FrogsAndToadsComponent extends JComponent {

    private FrogsAndToads game;
    private int cellSize = 40;
    private static final int Padding = 40; // distance to edge of frame
    private static final int LINE_THICKNESS = 3;
    private static final int FONT_SIZE = 18;
    private static final Color bgColor = Color.BLACK;
    private static final Color lineColor = Color.PINK;

    /**
     * Registers a mouse click handler
     *
     * @param r Rows
     * @param c Columns
     */
    public FrogsAndToadsComponent(int r, int c) {
        game = new FrogsAndToads(r, c);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // the grid
                for (int row = 0; row < game.getROWS(); row++) {
                    for (int col = 0; col < game.getCOLS(); col++) {

                        int x = Padding + col * cellSize + cellSize / 2;
                        int y = Padding + row * cellSize + cellSize / 2;

                        // if mouse was clicked close to center,
                        // then we have found the chosen game cell.
                        int a = e.getX();
                        int b = e.getY();
                        double d = Math.sqrt((x - a) * (x - a) + (y - b) * (y - b));
                        if (d <= cellSize / 2) {
                            game.move(row, col);
                            repaint();
                        }
                    }
                }
                // the reset button
                int x = (Padding + cellSize) / 2;
                int y = Padding + game.getROWS() * cellSize + Padding / 2;

                int a = e.getX();
                int b = e.getY();
                double d = Math.sqrt((x - a) * (x - a) + (y - b) * (y - b));

                if (d <= cellSize / 2) {

                    game.reset();
                    repaint();

                }
                // the change size button
                x = (Padding + cellSize * (game.getCOLS() - 1)) + cellSize / 2;
                y = Padding + game.getROWS() * cellSize + Padding / 2;

                a = e.getX();
                b = e.getY();

                d = Math.sqrt((x - a) * (x - a) + (y - b) * (y - b));
                if (d <= cellSize / 2) {

                    String s = JOptionPane.showInputDialog(" How many rows and columns? (rows,cols)");
                    Scanner in = new Scanner(s);
                    int c = in.nextInt();
                    int f = in.nextInt();

                    game = new FrogsAndToads(c, f);
                    repaint();

                }
            }
        });
    }

    /**
     * Draws a grid of the game, 2 buttons at the bottom and right corners and a
     * label on the top left if your win or get stuck.
     *
     * @param g the current graphics content
     */
    @Override

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        BufferedImage image1 = null;
        BufferedImage image2 = null;
        try {
            image1 = ImageIO.read(new File("images/frog.png"));
            image2 = ImageIO.read(new File("images/toad.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not find file.");
            System.exit(0);
        }

        cellSize = (w - 2 * Padding) / game.getCOLS();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // paint background
        g2.setColor(bgColor);
        g2.fill(new Rectangle(w, h));

        // draw change size button
        g2.setColor(lineColor);
        g2.setFont(new Font("serif", Font.BOLD, FONT_SIZE));
        g2.drawString("Change Size", Padding + cellSize * (game.getCOLS() - 1), Padding + game.getROWS() * cellSize + Padding / 2);

        // draw reset button
        g2.setColor(lineColor);
        g2.setFont(new Font("serif", Font.BOLD, FONT_SIZE));
        g2.drawString("Reset", Padding, Padding + game.getROWS() * cellSize + Padding / 2);

        // draw the grid
        g2.setStroke(new BasicStroke(LINE_THICKNESS));
        for (int row = 0; row < game.getROWS(); row++) {
            for (int col = 0; col < game.getCOLS(); col++) {

                int x = Padding + col * cellSize;
                int y = Padding + row * cellSize;

                g2.setColor(lineColor);
                g2.draw(new Rectangle(x, y, cellSize, cellSize));

                if (game.isFrog(row, col)) {
                    Image scaledInstance1 = image1.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT);
                    g2.drawImage(scaledInstance1, x, y, null);

                }
                if (game.isToad(row, col)) {
                    Image scaledInstance2 = image2.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT);
                    g2.drawImage(scaledInstance2, x, y, null);
                }

            }
        }

        if (!game.canMove()) {

            if (game.win()) {
                g2.setFont(new Font("serif", Font.BOLD, FONT_SIZE));
                int n = Padding / 2;
                g2.drawString("You Win " + game.getMoveCount()
                        + " moves.", n, n);
            } else {
                g2.setFont(new Font("serif", Font.BOLD, FONT_SIZE));
                int n = Padding / 2;
                g2.drawString("You are Stuck! " + game.getMoveCount()
                        + " moves.", n, n);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Frogs and Toads");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String s = JOptionPane.showInputDialog(" How many rows and columns? (rows,cols)");
        Scanner in = new Scanner(s);
        int x = in.nextInt();
        int y = in.nextInt();

        frame.add(new FrogsAndToadsComponent(x, y));

        frame.setSize(500, 500);

        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

}
