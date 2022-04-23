package com.vpolishchuk.tanks.launcher;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Vladimir on 15/02/18.
 **/
public class IntroFrame extends JFrame {
    private String line = "Loading...";
    public IntroFrame() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 200));
        setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2,
                (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2);
        setUndecorated(true);
        setLayout(new GridLayout(1, 1));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(0x4CAF50));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 48));
        g.drawString("Tanks", (getWidth() - g.getFontMetrics().stringWidth("Tanks")) / 2, getHeight() / 2);
        g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), 18));
        g.drawString(line, (getWidth() - g.getFontMetrics().stringWidth(line)) / 2, (int)(getHeight() * 0.7));
        g.setColor(Color.BLACK);
    }
}
