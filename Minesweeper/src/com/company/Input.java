package com.company;

import java.util.Objects;
import java.util.Scanner;

class Input {

    private Scanner sc;
    private boolean menu = true;
    private boolean play = true;
    private boolean lose = false;
    private boolean reveal = false;
    private boolean flag = false;
    private int xInput;
    private int yInput;

    Input() {
        this.sc = new Scanner(System.in);
    }

    boolean getInput() {
        if (lose)
            return getLoseInput();
        else
            getMenuInput();

        return false;
    }

    private void getGridInput() {
        String[] str;

        System.out.print("Enter row letter and column number: ");

        if (this.sc.hasNext()) {
            str = sc.nextLine().split(" ");
            if (str.length > 1) {
                this.xInput = str[0].toLowerCase().toCharArray()[0] - 'a' + 1;
                if (isInteger(str[1]))
                    this.yInput = Character.getNumericValue(str[1].charAt(0));
            }
        }
    }

    private boolean getLoseInput() {
        String str;
        boolean correct = false;

        while (!correct) {
            System.out.println("You Lose! Try again (Y/N)?");
            if (this.sc.hasNext()) {
                str = sc.nextLine();
                if (Objects.equals(str, "N") || Objects.equals(str, "n")) {
                    System.out.println("Game Over.");
                    this.play = false;
                    return false;
                } else if (Objects.equals(str, "Y") || Objects.equals(str, "Y")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void getMenuInput() {
        System.out.println(
                "Minesweeper:\n" +
                        "1. Reveal\n" +
                        "2. Drop Flag\n" +
                        "3. Quit"
        );
        if (this.sc.hasNextInt()) {
            int i = this.sc.nextInt();
            sc.nextLine();
            switch (i) {
                case 1:
                    this.reveal = true;
                    getGridInput();
                    break;
                case 2:
                    this.flag = true;
                    getGridInput();
                    break;
                case 3:
                    play = false;
                    break;
                default:
                    wrongMenuInput();
                    break;
            }
        } else {
            sc.nextLine();
            wrongMenuInput();
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean isPlay() {
        return play;
    }

    private void wrongMenuInput() {
        System.out.println("You have to type a number between 1 - 3");
    }

    int getXInput() {
        return xInput;
    }

    int getYInput() {
        return yInput;
    }

    boolean isReveal() {
        return reveal;
    }

    public void setReveal(boolean reveal) {
        this.reveal = reveal;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }
}