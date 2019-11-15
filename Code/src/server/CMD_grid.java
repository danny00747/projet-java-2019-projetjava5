/**
 * @author Martin Michotte
 * @date 12/11/2019
 * 
 * ANSI terminal control: http://www.termsys.demon.co.uk/vtansi.htm
 * Unicode chars: https://www.compart.com/fr/unicode/category/So
 */

package server;



public class CMD_grid {

	CMD cmd = new CMD();

	protected final short rows = 13;
	protected final short cols = rows;

	private myGrid myGrid = new myGrid();

		//TODO -> use forced whitespaces instead of manual! -> to prevent unwanted reformatting
	protected final String[] rowNames = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };
	private final String gridHeader = "     1   2   3   4   5   6   7   8   9   10  11  12  13";
	private final String gridTop = "   ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐";
	private final String gridLine = "   ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤";
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
		cmd.println("");
		cmd.print(String.format("%c[32m", escCode)); // change color
		cmd.println("		     YOUR BATTLEGROUND"); //TODO -> use forced whitespaces instead of manual! -> to prevent unwanted reformatting
		cmd.print(String.format("%c[0m", escCode));  // reset color
		cmd.println(gridHeader);
		cmd.println(gridTop);
		for (int i = 0; i < rows; i++) {
			cmd.print(String.format("%s  |", rowNames[i]));
			for (int j = 0; j < cols - 1; j++) {
				cmd.print(gridCase);
			}
			cmd.println(gridCase);
			if (i != rows - 1) {
				cmd.println(gridLine);
			}
		}
		cmd.println(this.gridBottom);

		cmd.print(String.format("%c[%dA", escCode, 29)); 	// move cursor to row position
		cmd.print(String.format("%c[%dC", escCode, 60)); 	// move cursor to column position
		cmd.print(String.format("%c[31m", escCode)); 		// change color
		cmd.println("		       ENEMY'S BATTLEGROUND");
		cmd.print(String.format("%c[0m", escCode)); 		// reset color
		cmd.print(String.format("%c[%dC", escCode, 60)); 	// move cursor to column position
		cmd.println(gridHeader);
		cmd.print(String.format("%c[%dC", escCode, 60));	// move cursor to column position
		cmd.println(gridTop);
		cmd.print(String.format("%c[%dC", escCode, 60)); 	// move cursor to column position
		for (int i = 0; i < rows; i++) {
			cmd.print(String.format("%s  |", rowNames[i]));
			for (int j = 0; j < cols - 1; j++) {
				cmd.print(gridCase);
			}
			cmd.println(gridCase);
			cmd.print(String.format("%c[%dC", escCode, 60)); 	 // move cursor to column position
			if (i != rows - 1) {
				cmd.println(gridLine);
				cmd.print(String.format("%c[%dC", escCode, 60)); // move cursor to column position
			}
		}
		cmd.println(this.gridBottom);
		cmd.print(String.format("%c7", escCode)); // Save cursor position
	}

	protected void insertInGrid(String val, String coord, boolean isOutGoing) {
		int rowIndex = myGrid.getCoordIndex(coord)[0];
		int colIndex = myGrid.getCoordIndex(coord)[1];
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
				str = String.format("%c[34m", escCode) + String.format(" %-2c", 'Ⓧ');
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

		cmd.print(String.format("%c8", escCode)); 									 	// move to saved cursor position
		cmd.print(String.format("%c[%dA", escCode, (2 * rows) - (2 * rowIndex)));    	// move cursor to row position
		cmd.print(String.format("%c[%dC", escCode, 4 + (4 * colIndex) + gridSelect)); 	// move cursor to column position
		cmd.print(str);
		cmd.print(String.format("%c[0m", escCode)); 									// reset color
		cmd.print(String.format("|"));
		cmd.print(String.format("%c8", escCode));										// move to saved cursor position
	}

	// for debugging
	public static void main(String[] args) {
		CMD_grid test = new CMD_grid();
		test.displayGrid();
	}

}
