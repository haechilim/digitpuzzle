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

        int count = 1;
        for(int y = 0; y < COLUMNS; y++) {
            for(int x = 0; x < ROWS; x++) {
                if(x==3 && y==4) cells[x][y].setNumber(0);
                else if(x==4 && y==4) cells[x][y].setNumber(24);
                else cells[x][y].setNumber(count++);
            }
        }
    }

    private boolean checkComplete() {
        int count = 1;

        if(cells[ROWS - 1][COLUMNS - 1].getNumber() != 0) return false;

        for(int y = 0; y < COLUMNS; y++) {
            for(int x = 0; x < ROWS; x++) {
                if(cells[x][y].getNumber() != count) return false;

                count++;
            }
        }

        return true;
    }

    private void movePeice(int posX, int posY) {
        if(isMovablePeice(posX + 1, posY)) {
            cells[posX + 1][posY].setNumber(cells[posX][posY].getNumber());
            cells[posX][posY].setNumber(0);
            return;
        }

        if(isMovablePeice(posX - 1, posY)) {
            cells[posX - 1][posY].setNumber(cells[posX][posY].getNumber());
            cells[posX][posY].setNumber(0);
            return;
        }

        if(isMovablePeice(posX, posY + 1)) {
            cells[posX][posY + 1].setNumber(cells[posX][posY].getNumber());
            cells[posX][posY].setNumber(0);
            return;
        }

        if(isMovablePeice(posX, posY - 1)) {
            cells[posX][posY - 1].setNumber(cells[posX][posY].getNumber());
            cells[posX][posY].setNumber(0);
            return;
        }
    }

    private boolean isMovablePeice(int posX, int posY) {
        return validIndex(posX, posY) && cells[posX][posY].getNumber() == 0;
    }

    private boolean validIndex(int posX, int posY) {
        return (posX >= 0 && posX < ROWS) && (posY >= 0 && posY < COLUMNS);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void cellClicked(int posX, int posY) {
        movePeice(posX, posY);
        if(checkComplete()) System.out.println("ㅈㅈ");
        puzzleFrame.redraw();
    }
}