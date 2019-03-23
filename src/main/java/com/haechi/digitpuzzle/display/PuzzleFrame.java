package com.haechi.digitpuzzle.display;

import com.haechi.digitpuzzle.core.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleFrame extends JFrame implements ActionListener {
    private Dimension frameDim;
    private Dimension boardDim;

    private Button exit;
    private PuzzleBoard puzzleBoard;
    private PuzzleClock puzzleClock;
    private Board board;

    private Timer timerShuffle;
    private Timer timerClock;

    public PuzzleFrame(String title, Board board) throws HeadlessException {
        super(title);
        
        this.board = board;
    }

    public void init() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setBounds(0, 0, 1980, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(251, 248, 238));

        setLayout(null);
        initDimensions();
        makeComponents();
        setVisible(true);

        timerShuffle = new Timer(100, this);
        timerShuffle.start();

        timerClock = new Timer(1000, this);
        timerClock.start();
    }

    public void redraw() {
        puzzleClock.redraw();
        puzzleBoard.redraw();
    }

    private void initDimensions() {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        frameDim = new Dimension(screenDim.width, screenDim.height - 60);

        //frameDim.height -= 300;

        boardDim = new Dimension();
        boardDim.height = frameDim.height - 100;
        boardDim.width = boardDim.height;
    }

    private void makeComponents() {
        Rectangle rectBoard = new Rectangle(frameDim.width/2 - boardDim.width/2,
                frameDim.height/2 - boardDim.height/2,
                boardDim.width, boardDim.height);
        Rectangle rectClock = new Rectangle(rectBoard.x + rectBoard.width + 50,
                frameDim.height/2 - 50/2, 165, 50);

        puzzleBoard = new PuzzleBoard(board);
        puzzleBoard.setBounds(rectBoard);
        puzzleBoard.init();
        add(puzzleBoard);

        puzzleClock = new PuzzleClock();
        puzzleClock.setBounds(rectClock);
        puzzleClock.init();
        add(puzzleClock);

        exit = new Button("Exit");
        exit.setBounds(frameDim.width - 100, 10, 90, 60);
        exit.addActionListener(this);
        add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if(source == timerShuffle) {
            //board.shuffle();
        }
        else if(source == timerClock) {
            puzzleClock.setTime(board.getCurrentTime());
            puzzleClock.redraw();
        }
        else if(source == exit) {
            if(timerShuffle.isRunning()) timerShuffle.stop();
            if(timerClock.isRunning()) timerClock.stop();
            dispose();
        }
    }
}
