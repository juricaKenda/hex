import engine.Engine;
import utils.Input;

public class Main {

    public static void main(String[] args) {
        Input input = new Input();

        Engine engine = new Engine(input.requestGameMode(),input.requestGridSize());
        engine.run();
    }

}
