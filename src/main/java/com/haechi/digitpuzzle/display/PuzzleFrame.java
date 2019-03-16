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
    private Board board;

    public PuzzleFrame(String title, Board board) throws HeadlessException {
        super(title);
        
        this.board = board;
    }

    public void init() {
        //setUndecorated(true);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, 1980, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(251, 248, 238));

        setLayout(null);
        initDimensions();
        makeComponents();
        setVisible(true);
    }

    public void redraw() {
        puzzleBoard.redraw();
    }

    private void initDimensions() {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        frameDim = new Dimension(screenDim.width, screenDim.height - 60);

        frameDim.height -= 300;

        boardDim = new Dimension();
        boardDim.height = frameDim.height - 100;
        boardDim.width = boardDim.height;
    }

    private void makeComponents() {
        puzzleBoard = new PuzzleBoard(board);
        puzzleBoard.setBounds(frameDim.width/2 - boardDim.width/2,
                frameDim.height/2 - boardDim.height/2,
                boardDim.width, boardDim.height);
        puzzleBoard.init();
        add(puzzleBoard);

        exit = new Button("Exit");
        exit.setBounds(frameDim.width - 100, 10, 90, 60);
        exit.addActionListener(this);
        add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == exit) dispose();
    }
}
