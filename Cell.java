import java.util.*;

public class Cell
{
    private final ArrayList<String> possibleTypes;
    private final int row;
    private final int col;
    private String ANSI_COLOR;
    private String type;

	public Cell(int row, int col, String type, ArrayList<String> possibleTypes)
	{
        this.possibleTypes = possibleTypes;
        this.row = row;
        this.col = col;
        this.type = type.toLowerCase();
        this.ANSI_COLOR = "";
    }
    
    public void setType(String newType, String color) {
        newType = newType.toLowerCase();
        this.ANSI_COLOR = color;

        if(possibleTypes.contains(newType)) {
            this.type = newType;
        }
        else {
            System.out.println("Internal error! Invalid type set to cell. Ignoring set command.");
        }
    }

    public String getType()
    {
        return this.type;
    }

    public int getRow()
    {
        return this.row;
    }

    public String getString() {
        if(this.type.equals("apple"))
        {
            return ANSI_COLOR + "@";
        }
        else if(this.type.equals("snake")) {
            return ANSI_COLOR + "o";
        }
        else if(this.type.equals("x")) {
            return ANSI_COLOR + "X";
        }
        else {
            return ANSI_COLOR + " ";
        }
    }

    public int getCol()
    {
        return this.col;
    }
}