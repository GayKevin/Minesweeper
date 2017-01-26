package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class LevelGenerator {
    private String[][] grid;
    private static final int gridX = 9;
    private static final int gridY = 9;
    private static final int gridO = 10;

    LevelGenerator() {
        setGrid();
        generateX();
        generateOFromGrid();
        generateFile(concatGrid());
    }

    public String[][] getGrid() {
        return grid;
    }

    private void setGrid() {
        this.grid = new String[gridX][gridY];
    }

    private void generateX() {
        for ( int x = 0; x < gridX; x++ ) {
            for ( int y = 0; y < gridY; y++ ) {
                this.grid[x][y] = "x";
            }
        }
    }

    private void generateOFromGrid() {

        int rX = 0;
        int rY = 0;
        int i  = 0;

        while(i < gridO) {
          rX = (int) (Math.random()* gridX);
          rY = (int) (Math.random()* gridY);

          if (Objects.equals(grid[rX][rY], "x")) {
              this.grid[rX][rY] = "o";
              i++;
            }
        }
    }

    private String[] concatGrid() {

        String[] lines = new String[gridX];

        for (int x = 0; x < gridX; x++) {
            for (int y = 0; y < gridX; y++) {
                if ( y != 0 )
                    lines[x] += this.grid[x][y];
                else
                    lines[x] = this.grid[x][y];
            }
        }

        return lines;
    }

    private void generateFile(String[] grid) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Mines.txt"))) {

            for (int i = 0; i < gridX; i++) {
                bw.write(grid[i] + "\n");
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
