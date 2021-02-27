package engine;

import players.ComputerPlayer;
import players.ConsolePlayer;
import players.Player;
import utils.Display;

import java.util.Arrays;


public class Engine {
    private Player red;
    private Player blue;
    private char[][] grid;

    public Engine(GameMode mode, int gridSize){
        initGame(mode);
        initGrid(gridSize);
    }

    public void run() {
        int redBlue = 1;
        char tag;
        Player player;

        while (true){
            Display.show(grid);

            // alternate between the players
            redBlue++;
            if (redBlue % 2 == 1){
                player = red;
                tag = 'r';
            }else{
                player = blue;
                tag = 'b';
            }

            player.makeMove(grid,tag);

            if(GridChecker.gameDone(grid,tag)){
                Display.show(grid);
                String winner = tag == 'r' ? "RED" : "BLUE";
                System.out.println(winner + " won!");
                break;
            }
        }
    }

    private void initGame(GameMode mode) {
        switch (mode){
            case SINGLE_PLAYER:
                red = new ConsolePlayer();
                blue = new ComputerPlayer();
                return;
            case MULTI_PLAYER:
                red = new ConsolePlayer();
                blue = new ConsolePlayer();
                return;
            case AUTO:
                red = new ComputerPlayer();
                blue = new ComputerPlayer();
        }
    }

    private void initGrid(int n) {
        grid = new char[n][n];
        for (char[] row : grid) {
            Arrays.fill(row, ' ');
        }
    }

}
