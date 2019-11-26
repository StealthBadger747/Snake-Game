import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    Board board;
    Snake snake;

    /**
     * Constructor for game.
     */
    public Game() {
        this.board = new Board(20, 20);
        this.snake = new Snake(board);
        this.board.initializeBoard(new ArrayList<>(Arrays.asList("blank", "apple", "snake", "x")));
        this.snake.initSnake();
        this.board.setNewApple();
    }

    public void gameLoop() {
        System.out.print("\033[?1049l");
        board.showBoard();
        Console terminal = System.console();
        while (true) {
            char[] in = terminal.readPassword();
            if (in != null) {
                if (!snake.moveSnake(in[0] + "")) {
                    board.showBoard();
                    System.out.println("GAME OVER. Press any key to exit.");
                    break;
                }
            }
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        board.clearScreen();
        System.out.print("\033[?1049l");
    }

	public static void main(String [] args)
	{
        // Hide cursor
        System.out.print("\u001B[?25l");

        Game newGame = new Game();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(100);
                    System.out.println("Shutting down ...");
                    // Give back the preserved screen buffer and clear the screen.
                    newGame.board.clearScreen();
                    System.out.print("\033[?1049l");
                    // Unhide the cursor
                    System.out.print("\u001B[?25h");
    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });

        newGame.gameLoop();

        // Unhide the cursor
        System.out.print("\u001B[?25h");
	}
}