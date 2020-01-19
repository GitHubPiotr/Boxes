package boxes.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Window {

    private String title;
    private int width, height;
    private JFrame frame;
    private Canvas canvas;

    Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    void createWindow() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/textures/icon.png")));
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas = new Canvas();
        frame.add(canvas);
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));

        canvas.setFocusable(false);
        canvas.setBackground(Color.BLACK);
    }

    public JFrame getJFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
