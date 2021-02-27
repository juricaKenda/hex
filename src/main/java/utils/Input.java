package utils;

import engine.GameMode;

import java.util.Scanner;

public class Input {
    private Scanner scanner;

    public Input(){
        scanner = new Scanner(System.in);
    }

    public int requestGridSize() {
        while (true){
            System.out.print("Enter grid size (N): ");
            String line = scanner.nextLine();
            try{
                return Integer.parseInt(line);
            }catch (NumberFormatException ignore){}
        }
    }

    public GameMode requestGameMode() {
        while (true){
            System.out.print("Enter game mode (single_player = 1 | multi_player = 2 | auto = 3): ");
            String line = scanner.nextLine();
            try{
                int selection = Integer.parseInt(line);
                switch (selection){
                    case 1:
                        return GameMode.SINGLE_PLAYER;
                    case 2:
                        return GameMode.MULTI_PLAYER;
                    case 3:
                        return GameMode.AUTO;
                }
            }catch (NumberFormatException ignore){}
        }
    }
}
