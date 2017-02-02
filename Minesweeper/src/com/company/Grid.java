package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

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

    private void getMapFromFile() {
        int x = 0;
        int rc = 0;
        String[] tmpGrid = new String[10];

        try (BufferedReader br = new BufferedReader(new FileReader("Mines.txt"))) {
            while ((tmpGrid[x] = br.readLine()) != null) {
                if (!(Objects.equals(tmpGrid[x], "")))
                    x++;
            }
            this.gridFile = tmpGrid;
            resetGrid();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private boolean checkBoundCaseUser(int xInput, int yInput){
        return 'o' == gridFile[xInput].charAt(yInput);
    }

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

    private void reveal(Input input)  {
        int xInput = input.getXInput() -1;
        int yInput = input.getYInput() -1;

        if (checkBoundCaseUser(xInput, yInput)) {
            resetGrid[xInput][yInput] = 'o';
            input.setLose(true);
        } else {
            resetGrid[xInput][yInput] = (char)(findNearbyBounds(xInput, yInput) + 48);
        }
    }

    private void flag(Input input) {
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
        }
    }

    private void resetGrid () {
        char[][] str = new char[9][9];
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                str[x][y] = 'x';
            }
        }
        this.resetGrid = str;
    }

    private void checkEndGame(Input input){
        if (this.flag == 0 && mines > 0)
            input.setLose(true);
        if (this.mines == 0)
            input.setWin(true);
    }
}
