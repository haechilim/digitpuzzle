package com.haechi.digitpuzzle.display;

import java.awt.*;

public class PuzzleClock extends Panel {
    private String time;
    private Font font = new Font("Monospaced", Font.BOLD, 30);
    private static final Color colorBorder = new Color(187, 173, 159);
    private Image offscreen;

    public void init() {
        setLayout(null);
    }

    public void redraw() {
        if(time == null) return;
        if(offscreen == null) offscreen = createImage(getWidth(), getHeight());

        Graphics2D graphics = (Graphics2D)offscreen.getGraphics();

        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(colorBorder);
        graphics.drawRect(0, 0, getWidth()-1, getHeight()-1);

        graphics.setColor(Color.YELLOW);
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics.drawString(time, 10, 35);

        revalidate();
        repaint();
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public void update(Graphics graphics) {
        paint(graphics);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(offscreen, 0, 0, getWidth(), getHeight(), this);
    }
}