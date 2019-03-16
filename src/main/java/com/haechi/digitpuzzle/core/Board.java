package com.haechi.digitpuzzle.core;

import com.haechi.digitpuzzle.display.PuzzleFrame;

public class Board {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private Cell[][] cells;
    private PuzzleFrame puzzleFrame;

    public void init() {
        makeCells();

        puzzleFrame = new PuzzleFrame("숫자 퍼즐", this);
        puzzleFrame.init();
        puzzleFrame.redraw();
    }

    private void makeCells() {
        int number = 0;

        cells = new Cell[ROWS][];

        for(int x = 0; x < ROWS; x++) {
            cells[x] = new Cell[COLUMNS];

            for(int y = 0; y < COLUMNS; y++) {
                cells[x][y] = new Cell(number++);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void cellClicked(int posX, int posY) {
        puzzleFrame.redraw();
    }
}
