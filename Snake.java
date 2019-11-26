import java.util.LinkedList;

public class Snake
{
    private final String ANSI_GREEN = "\u001B[32m";
    private final String PURPLE_BRIGHT = "\033[0;95m";
    private final Board board;
    private final LinkedList<Cell> snakeList;
    private String direction;
    
    public Snake(Board board) {
        this.board = board;
        this.snakeList = new LinkedList<>();
        this.direction = "right";
    }

    /**
     * Initializes the snake on the board. As long as it is drawn before the apple there should be no conflicts.
     */
	public void initSnake()
	{
        for(int i = 3; i < 6; i++)
        {
            Cell currCell = board.boardArray.get(3).get(i);
            currCell.setType("snake", ANSI_GREEN);
            snakeList.add(currCell);
        }
    }
    
    public boolean moveSnake(String newDirection) {
        this.direction = newDirection;

        if(direction.equals("d"))
        {
            return moveCell(0, 1);
        }
        else if(direction.equals("a"))
        {
            return moveCell(0, -1);
        }
        else if(direction.equals("w"))
        {
            return moveCell(-1, 0);
        }
        else if(direction.equals("s"))
        {
            return moveCell(1, 0);
        }

        return true;
    }

    private boolean moveCell(int rowChange, int colChange) {
        Cell snakeHead = this.snakeList.peekLast();
        int newRow = rowChange + snakeHead.getRow();
        int newCol = colChange + snakeHead.getCol();

        // Check boarders
        if(newRow < 1 || newRow > board.boardHeight - 2 || newCol < 1 || newCol > board.boardWidth - 2)
        {
            board.boardArray.get(newRow).get(newCol).setType("x", PURPLE_BRIGHT);
            return false;
        }

        // Check snake body
        for(Cell cell : this.snakeList)
        {
            if(newRow == cell.getRow() && newCol == cell.getCol())
            {
                board.boardArray.get(newRow).get(newCol).setType("x", PURPLE_BRIGHT);
                return false;
            }
        }

        // Remove tail of snake and set to blank, unless our next space is an apple. Otherwise create new apple.
        if(board.boardArray.get(newRow).get(newCol).getType().equals("apple"))
        {
            board.setNewApple();
        }
        else
        {
            // Set to blank and draw update
            this.snakeList.peekFirst().setType("blank", "");
            board.drawCell(this.snakeList.remove());
        }

        // Modify next cell
        board.boardArray.get(newRow).get(newCol).setType("snake", ANSI_GREEN);
        this.snakeList.add(board.boardArray.get(newRow).get(newCol));

        // Draw update to this updated cell
        board.drawCell(board.boardArray.get(newRow).get(newCol));

        return true;
    }
}