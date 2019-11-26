import java.util.ArrayList;
import java.util.Random;

public class Board {
    private final String YELLOW_BACKGROUND = "\033[43m";
    private final String WHITE_BACKGROUND = "\033[47m";
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final Random rand;
    public final ArrayList<ArrayList<Cell>> boardArray;
    public final int boardWidth;
    public final int boardHeight;

    /**
     * Constructor for the board.
     * 
     * @param boardWidth  defines the width of the board.
     * @param boardHeight defines the height of the board.
     * @param reDrawFlag  enables redrawing of the terminal window in UNIX based
     *                    systems or consoles.
     */
    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.boardArray = new ArrayList<>();
        this.rand = new Random();
    }

    /**
     * Initializes the board.
     * 
     * @param possibleTypes defines the possible types there can be for error
     *                      checking.
     */
    public void initializeBoard(ArrayList<String> possibleTypes) {
        for (int row = 0; row < this.boardHeight; row++) {
            // Setup Rows, Initialize a new ArrayList for each Row.
            boardArray.add(new ArrayList<Cell>());
            for (int col = 0; col < this.boardWidth; col++) {
                // Initialize Cols. Creating a new Cell object for each cell.
                Cell cell = new Cell(row, col, "blank", possibleTypes);
                if(row == 0 || row == this.boardHeight - 1 || col == 0 || col == this.boardWidth - 1)
                {
                    cell.setType("blank", WHITE_BACKGROUND);
                }
                boardArray.get(row).add(cell);
            }
        }
    }

    /**
     * Sets the apple's location where the snake isn't.
     */
    public void setNewApple() {
        while(true)
        {
            int row = rand.nextInt(this.boardWidth - 2) + 1;
            int col = rand.nextInt(this.boardHeight - 2) + 1;
            Cell randomCell = this.boardArray.get(row).get(col);
            String cellType = randomCell.getType();

            if(cellType.equals("blank")) {
                randomCell.setType("apple", YELLOW_BACKGROUND + ANSI_RED);
                // Draw the cell
                drawCell(randomCell);
                break;
            }
        }
    }

	public void showBoard()
	{
		for(int row = 0; row < this.boardHeight; row++)
		{
			for(int col = 0; col < this.boardWidth; col++)
			{
				System.out.print(this.boardArray.get(row).get(col).getString() + " " + ANSI_RESET);
            }
            System.out.print("\n");
        }

        drawBottom();
    }

    public void drawColumn(int row) {
        for(int col = 0; col < this.boardWidth; col++)
        {
            System.out.print(this.boardArray.get(row).get(col).getString() + " " + ANSI_RESET);
        }
        System.out.print("\n");
    }

    public void drawCell(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();

        // Jump cursor to where we need to write to
        System.out.printf("\033[%d;%dH", row + 1, (col * 2) + 1);

        // Draw the cell
        System.out.print(this.boardArray.get(row).get(col).getString() + " " + ANSI_RESET);

        // Move cursor to (0,0)
        System.out.printf("\033[H");
        for(int i = 0; i <= this.boardHeight + 2; i++) {
            System.out.printf("\033[1B");
        }
    }

    public void clearScreen() {
        for(int row = 0; row < this.boardHeight + 3; row++)
		{
			System.out.print(" ");
			for(int col = 0; col < this.boardWidth; col++)
			{
				System.out.print("  ");
			}
			System.out.println("  ");
        }
    }

    public void clearCell(int row, int col) {
        this.boardArray.get(row).get(col).setType("blank", "");
    }
    
    private void drawBottom()
    {
        System.out.print(" ");
        for(int i = 0; i < boardWidth; i++)
        {
            System.out.print("__");
        }
        System.out.print("\n");
    }	
}