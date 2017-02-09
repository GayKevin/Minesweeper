package com.company;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by gay_k on 24/01/2017.
 */

class Grid {

    private char[] gridYLetter = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'
    };
    private String[] gridFile;
    private char[][] resetGrid;
    private int mines = 10;
    private int flag = 15;

    /**
     * Consctrutor of Grid that stays active while you are on the game
     */
    Grid() {
        getMapFromFile();
        Input input = new Input();
        while (input.isPlay()) {
            showGrid(input);

            if (input.getInput()) {
                resetGrid();
                input.setLose(false);
            }

            if (input.isReveal()) {
                reveal(input);
                input.setReveal(false);
            } else if (input.isFlag()) {
                flag(input);
                input.setFlag(false);
            }

            checkEndGame(input);
        }
    }

    /**
     * Parse the map from a file Mines.txt
     */
    private void getMapFromFile() {
        int x = 0;
        String[] tmpGrid = new String[10];
        File file = new File("../LevelGenerator/Mines.txt");

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNext()) {
            tmpGrid[x] = sc.next();
                if (!(Objects.equals(tmpGrid[x], "")))
                    x++;
            }
            this.gridFile = tmpGrid;
            resetGrid();

            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Display the grid on the terminal
     * @param input
     */
    private void showGrid(Input input) {
        System.out.println(this.flag + " flag remaining.");
        System.out.println(this.mines + " mines left.");
        System.out.println("  123456789");
        for (int x = 0; x < 9; x++) {
            System.out.print(gridYLetter[x] + " ");
            for (int y = 0; y < 9; y++) {
                System.out.print(this.resetGrid[x][y]);
            }

            System.out.println();
        }
    }

    /**
     * Check if it has a mine inside the case
     * @param xInput
     * @param yInput
     * @return
     */
    private boolean checkBoundCaseUser(int xInput, int yInput){
        return 'o' == gridFile[xInput].charAt(yInput);
    }

    /**
     * Find mines near the input typed by the user
     * @param xInput
     * @param yInput
     * @return
     */
    private int findNearbyBounds(int xInput, int yInput) {
        int nbrBounds = 0;

        for(int x = -1; x < 2; x++) {
            if ((x + xInput) >= 0 && (x + xInput) < 9) {
                for (int y = -1; y < 2; y++) {
                    if ((yInput + y) >= 0 && (yInput + y) < 9) {
                        if (Objects.equals(gridFile[xInput + x].charAt(yInput + y), 'o'))
                            nbrBounds++;
                    }
                }
            }
        }
        return nbrBounds;
    }

    /**
     * Show the case that the user have typed
     * @param input
     */
    private void reveal(Input input)  {
        int xInput = input.getXInput() - 1;
        int yInput = input.getYInput() - 1;

        if (xInput > 8 || xInput < 0 || yInput > 8 || yInput < 0)
            return;

        if (Objects.equals(resetGrid[input.getXInput() - 1][input.getYInput() - 1], 'F'))
            return;

        if (checkBoundCaseUser(xInput, yInput)) {
            resetGrid[xInput][yInput] = 'o';
            input.setLose(true);
        } else {
            resetGrid[xInput][yInput] = (char)(findNearbyBounds(xInput, yInput) + 48);
        }
    }

    /**
     * Flag and Unflag a case
     * @param input
     */
    private void flag(Input input) {
        if (input.getXInput() - 1 > 8 || input.getYInput() - 1 > 8)
            return;
        if (Objects.equals(gridFile[input.getXInput() - 1].charAt(input.getYInput() - 1), 'o')
                && Objects.equals(resetGrid[input.getXInput() - 1][input.getYInput() - 1], 'x')) {
            mines--;
            flag--;
            this.resetGrid[input.getXInput() - 1][input.getYInput() - 1] = 'F';
        } else if (Objects.equals(resetGrid[input.getXInput() - 1][input.getYInput() - 1], 'F')) {
            this.resetGrid[input.getXInput() - 1][input.getYInput() - 1] = gridFile[input.getXInput() - 1].charAt(input.getYInput() - 1);
            flag++;
            if (Objects.equals(gridFile[input.getXInput() - 1].charAt(input.getYInput() - 1), 'o'))
                mines++;
        } else if (Objects.equals(resetGrid[input.getXInput() - 1][input.getYInput() - 1], 'x')) {
            this.resetGrid[input.getXInput() - 1][input.getYInput() - 1] = 'F';
            flag--;
        }
    }

    /**
     * Reset the grid from the beginning of the game
     */
    private void resetGrid () {
        char[][] str = new char[9][9];
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                str[x][y] = 'x';
            }
        }
        this.resetGrid = str;
    }

    /**
     * Check if the game is finsihed or not
     * @param input
     */
    private void checkEndGame(Input input){
        if (this.flag == 0 && mines > 0)
            input.setLose(true);
        if (this.mines == 0)
            input.setWin(true);
    }
}
