package com.haechi.digitpuzzle.core;

import com.haechi.digitpuzzle.display.PuzzleFrame;

public class Board {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private Cell[][] cells;
    private int nullCellX;
    private int nullCellY;
    private PuzzleFrame puzzleFrame;
    private int time;

    public void init() {
        makeCells();

        puzzleFrame = new PuzzleFrame("숫자 퍼즐", this);
        puzzleFrame.init();
        puzzleFrame.redraw();
    }

    private void makeCells() {
        cells = new Cell[ROWS][];

        for(int x = 0; x < ROWS; x++) {
            cells[x] = new Cell[COLUMNS];

            for(int y = 0; y < COLUMNS; y++) {
                cells[x][y] = new Cell(0);
            }
        }

        int number = 1;

        for(int y = 0; y < COLUMNS; y++) {
            for(int x = 0; x < ROWS; x++) {
                //if(x==3 && y==4) cells[x][y].setNumber(0);
                //else if(x==4 && y==4) cells[x][y].setNumber(24);
                cells[x][y].setNumber(number++);
            }
        }
    }

    private boolean checkComplete() {
        int count = 1;

        if(cells[ROWS - 1][COLUMNS - 1].getNumber() != 0) return false;

        for(int y = 0; y < COLUMNS; y++) {
            for(int x = 0; x < ROWS; x++) {
                //if(x == ROWS - 1 && y == COLUMNS - 1) break;
                if(cells[x][y].getNumber() != count) return false;

                count++;
            }
        }

        return true;
    }

    private void movePeice(int posX, int posY) {
        if(isMovablePeice(posX + 1, posY)) {
            changeCell(posX + 1, posY, posX, posY);
            return;
        }

        if(isMovablePeice(posX - 1, posY)) {
            changeCell(posX - 1, posY, posX, posY);
            return;
        }

        if(isMovablePeice(posX, posY + 1)) {
            changeCell(posX, posY + 1, posX, posY);
            return;
        }

        if(isMovablePeice(posX, posY - 1)) {
            changeCell(posX, posY - 1, posX, posY);
            return;
        }
    }

    private void changeCell(int posX, int posY, int posX2, int posY2) {
        if(validIndex(posX, posY) && validIndex(posX2, posY2)) {
            cells[posX][posY].setNumber(cells[posX2][posY2].getNumber());
            cells[posX2][posY2].setNumber(0);
            nullCellX = posX2;
            nullCellY = posY2;
        }
    }

    private void setNullCell() {
        for(int y = 0; y < COLUMNS; y++) {
            for(int x = 0; x < ROWS; x++) {
                if(cells[x][y].getNumber() == 0) {
                    nullCellX = x;
                    nullCellY = y;
                }
            }
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

    //  셀을 클릭하면 호출됨
    public void cellClicked(int posX, int posY) {
        movePeice(posX, posY);
        if(checkComplete()) System.out.println("완성");
        puzzleFrame.redraw();
    }

    //  현재 시각을 표시하기 위해 1초에 한번씩 호출됨
    public String getCurrentTime() {
        String second;
        String minute;
        String hour;

        time++;

        second = time % 60 < 10 ? (0 + "") + (time % 60 + "") : time % 60 + "";
        minute = time / 60 % 60 < 10 ? (0 + "") + (time / 60 % 60 + "") : time / 60 % 60 + "";
        hour = time / 60 / 60 < 10 ? (0 + "") + (time / 60 / 60 + "") : time / 60 / 60 + "";

        return hour + ":" + minute + ":" + second;
    }

    //  퍼즐을 섞으라고 지정된 회수만큼 호출됨
    public void shuffle() {
        setNullCell();

        //for(int  i = 0; i < 100; i++) {
            int direction = (int)(Math.random() * 4);

            if(direction == 0) changeCell(nullCellX, nullCellY, nullCellX + 1, nullCellY);
            if(direction == 1) changeCell(nullCellX, nullCellY, nullCellX - 1, nullCellY);
            if(direction == 2) changeCell(nullCellX, nullCellY, nullCellX, nullCellY + 1);
            if(direction == 3) changeCell(nullCellX, nullCellY, nullCellX, nullCellY - 1);
        //}

        puzzleFrame.redraw();
    }
}