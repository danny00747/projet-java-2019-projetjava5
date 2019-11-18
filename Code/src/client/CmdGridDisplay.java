/**
 * @author Martin Michotte
 * @date 12/11/2019
 * 
 * ANSI terminal control: http://www.termsys.demon.co.uk/vtansi.htm
 * Unicode chars: https://www.compart.com/fr/unicode/category/So
 */

package client;


/**
 * 
 */
public class CmdGridDisplay {


	protected final short rows = 13;
	protected final short cols = rows;

	private Grid grid = new Grid();

	protected final String[] rowNames = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };
	private final String gridHeader = "     1   2   3   4   5   6   7   8   9   10  11  12  13";
	private final String gridTop =    "   ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐";
	private final String gridLine =   "   ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤";
	private final String gridBottom = "   └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘";
	private final String gridCase = String.format(" %-2c|", ' ');

	protected final char escCode = 0x1B;
	protected final char fg_red = 31;
	protected final char fg_green = 32;
	protected final char bg_red = 41;
	protected final char bg_green = 42;

	/**
	 * Function that displays a AxA grid with row and column headers.
	 * 
	 */
	protected void displayGrid() {
		System.out.print(String.format("%c[2J", escCode)); 		//Clear the whole screen
		System.out.print(String.format("%c[H", escCode));  		//set cursor to home 
		System.out.println("");
		System.out.print(String.format("%c[32m", escCode)); 	// change color
		System.out.println("		     YOUR BATTLEGROUND"); 	
		System.out.print(String.format("%c[0m", escCode));  	// reset color
		System.out.println(gridHeader);
		System.out.println(gridTop);
		for (int i = 0; i < rows; i++) {
			System.out.print(String.format("%s  |", rowNames[i]));
			for (int j = 0; j < cols - 1; j++) {
				System.out.print(gridCase);
			}
			System.out.println(gridCase);
			if (i != rows - 1) {
				System.out.println(gridLine);
			}
		}
		System.out.println(this.gridBottom);

		System.out.print(String.format("%c[%dA", escCode, 29)); 	// move cursor to row position
		System.out.print(String.format("%c[%dC", escCode, 60)); 	// move cursor to column position
		System.out.print(String.format("%c[31m", escCode)); 		// change color
		System.out.println("		       ENEMY'S BATTLEGROUND");
		System.out.print(String.format("%c[0m", escCode)); 			// reset color
		System.out.print(String.format("%c[%dC", escCode, 60)); 	// move cursor to column position
		System.out.println(gridHeader);
		System.out.print(String.format("%c[%dC", escCode, 60));		// move cursor to column position
		System.out.println(gridTop);
		System.out.print(String.format("%c[%dC", escCode, 60)); 	// move cursor to column position
		for (int i = 0; i < rows; i++) {
			System.out.print(String.format("%s  |", rowNames[i]));
			for (int j = 0; j < cols - 1; j++) {
				System.out.print(gridCase);
			}
			System.out.println(gridCase);
			System.out.print(String.format("%c[%dC", escCode, 60)); 	 // move cursor to column position
			if (i != rows - 1) {
				System.out.println(gridLine);
				System.out.print(String.format("%c[%dC", escCode, 60)); // move cursor to column position
			}
		}
		System.out.println(this.gridBottom);
		System.out.print(String.format("%c7", escCode)); // Save cursor position
	}

	protected void insertInGrid(String val, String coord, boolean isOutGoing) {
		int rowIndex = grid.getCoordIndex(coord)[0];
		int colIndex = grid.getCoordIndex(coord)[1];
		int gridSelect = 0;
		String str = String.format("");

		switch (val) {
			case "Unit":
				str = String.format("%c[42m", escCode) + String.format(" %-2c", '⏣');
				break;
			case "Hit":
				str = String.format("%c[43m%c[31m", escCode, escCode) + String.format(" %-2c", 'Ⓧ');
				break;
			case "noHit":
				str = String.format("%c[44m", escCode) + String.format(" %-2c", 'Ⓧ');
				break;
			case "Destroyed":
				str = String.format("%c[41m%c[33m", escCode, escCode) + String.format(" %-2c", 'Ⓧ');
				break;
			default:
				break;
		}

		// insert int the ennemy's grid
		if (isOutGoing) {
			gridSelect = 60;
		}
		// insert in your grid
		else {
			gridSelect = 0;
		}

		System.out.print(String.format("%c8", escCode)); 									 	// move to saved cursor position
		System.out.print(String.format("%c[%dA", escCode, (2 * rows) - (2 * rowIndex)));    	// move cursor to row position
		System.out.print(String.format("%c[%dC", escCode, 4 + (4 * colIndex) + gridSelect)); 	// move cursor to column position
		System.out.print(str);
		System.out.print(String.format("%c[0m", escCode)); 										// reset color
		System.out.print(String.format("|"));
		System.out.print(String.format("%c8", escCode));										// move to saved cursor position
	}

	/**
     * 
     * @param numberOfLines {int} -
     */
    protected void removeLines(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            System.out.print(String.format("%c[1A", escCode)); // Move Up 1 line
            System.out.print(String.format("%c[2K", escCode)); // clear the current line
        }
	}
	
	/**
     * 
     */
    protected void clearDown() {
		System.out.print(String.format("%c8", escCode)); // move to saved cursor position
		System.out.print(String.format("%c[J", escCode));
		System.out.print(String.format("%c8", escCode));
    }

}
