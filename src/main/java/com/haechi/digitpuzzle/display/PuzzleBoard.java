package com.haechi.digitpuzzle.display;

import com.haechi.digitpuzzle.core.Board;
import com.haechi.digitpuzzle.core.Cell;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PuzzleBoard extends Panel implements MouseListener {
    private Font font;
    private static final Color colorBack = new Color(187, 173, 159);
    private static final Color[] colors = {
            new Color(238, 228, 218),
            new Color(236, 224, 119),
            new Color(244, 178, 116),
            new Color(247, 149, 93),
            new Color(248, 92, 50)
    };

    private Board board;
    private Cell[][] cells;
    private double cellWidth;
    private double cellHeight;
    private double cellMargin;
    private int fontHeight;

    private Image offscreen;
    private Image background;
    private Map<Integer, Image> imagePiecies;

    public PuzzleBoard(Board board) {
        this.board = board;
        this.cells = board.getCells();
    }

    public void init() {
        imagePiecies = new HashMap<>();

        setLayout(null);
        initDimensions();
        addMouseListener(this);
    }

    private void initDimensions() {
        cellMargin = 10.0;
        cellWidth = (getWidth() - cellMargin * (cells.length + 1)) / cells.length;
        cellHeight = (getHeight() - cellMargin * (cells[0].length + 1)) / cells[0].length;
        fontHeight = (int)(cellHeight * 0.3);
        font = new Font("Monospaced", Font.BOLD, fontHeight);
    }

    public void redraw() {
        makePieces();
        makeBackground();

        if(offscreen == null) offscreen = createImage(getWidth(), getHeight());
        Graphics graphics = offscreen.getGraphics();
        graphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        if(cells == null) return;

        for(int x=0; x<cells.length; x++) {
            for(int y=0; y<cells[x].length; y++) {
                drawPiece(graphics, x, y, cells[x][y]);
            }
        }

        revalidate();
        repaint();
    }

    private void makeBackground() {
        if(background != null) return;

        background = createImage(getWidth(), getHeight());

        Graphics graphics = background.getGraphics();
        graphics.setColor(colorBack);
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
    }

    private void makePieces() {
        if(!imagePiecies.isEmpty()) return;

        for(int x=0; x<cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                int number = cells[x][y].getNumber();
                imagePiecies.put(number, makePiece(number));
            }
        }
    }

    private Image makePiece(int number) {
        int colorIndex = new Random().nextInt(colors.length);
        Image image = createImage((int)cellWidth, (int)cellHeight);
        Graphics2D graphics = (Graphics2D)image.getGraphics();
        graphics.setColor(number==0 ? new Color(205, 193, 180) : colors[colorIndex]);
        graphics.fillRect(0, 0, (int)cellWidth, (int)cellHeight);

        if(number != 0) {
            String text = String.valueOf(number);
            graphics.setColor(colorIndex>1 ? Color.WHITE : new Color(119, 110, 99));
            graphics.setFont(font);
            FontMetrics fm = graphics.getFontMetrics();
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            graphics.drawString(text, (int)(cellWidth/2 - fm.stringWidth(text)/2), (int)(cellHeight/2 + fontHeight/2));
        }

        return image;
    }

    private void drawPiece(Graphics graphics, int posX, int posY, Cell cell) {
        int x = (int)(posX * cellWidth + (posX + 1) * cellMargin);
        int y = (int)(posY * cellHeight + (posY + 1) * cellMargin);
        graphics.drawImage(getPieceImage(cell), x, y, (int)cellHeight, (int)cellHeight, this);
    }

    private Image getPieceImage(Cell cell) {
        return imagePiecies.get(cell.getNumber());
    }

    @Override
    public void update(Graphics graphics) {
        paint(graphics);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(offscreen, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {
        int posX = event.getX() / (int)(cellWidth + cellMargin);
        int posY = event.getY() / (int)(cellHeight + cellMargin);

        if(posX >= cells.length || posY >= cells[0].length) return;

        board.cellClicked(posX, posY);
    }

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}
}
