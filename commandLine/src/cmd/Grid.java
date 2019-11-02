/**
 * 
 */
package cmd;

/* run from terminal: 
 * compile: javac package/file.class
 * run: java package/file
 * 
 * 
 * ANSI terminal control: http://www.termsys.demon.co.uk/vtansi.htm
 * Unicode chars: https://www.compart.com/fr/unicode/category/So
 */


/**
 * @author Martin Michotte
 * 
 */
public class Grid {
	
	private int rows = 13;
	private int cols = 13;
	
	protected String[] colHeader = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};
	protected String[] rowHeader = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	private String gridHeader = "     A   B   C   D   E   F   G   H   I   J   K   L   M";
	private String gridTop = 	"   ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐";
	private String gridLine = 	"   ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤";
	private String gridBottom = "   └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘";
	private String gridCase = 	String.format(" %-2c|", ' ');
	
	protected char escCode = 0x1B;
	protected char fg_red = 31;
	protected char fg_green = 32;
	protected char bg_red = 41;
	protected char bg_green = 42;
	
	/**
	 * Function that displays a AxA grid with row and column headers.
	 * 
	 */
	protected void displayGrid(){
		//System.out.print(String.format("%c[2J",escCode));
		//System.out.print(String.format("%c[71",escCode));
		System.out.println();
		System.out.print(String.format("%c[32m",escCode)); 					//change color
		System.out.println("		     YOUR BATTLEGROUND");
		System.out.print(String.format("%c[0m",escCode)); 					//reset color
		System.out.println(gridHeader);
		System.out.println(gridTop);
		for(int i=0; i<rows; i++){
			System.out.print(String.format("%2d |", i+1));
			for(int j=0; j<cols-1; j++){
				System.out.print(gridCase);
			}
			System.out.println(gridCase);
			if(i != rows-1) {
				System.out.println(gridLine);
			}
		}
		System.out.println(this.gridBottom);
		
		System.out.print(String.format("%c[%dA",escCode,29));	//move cursor to row position
		System.out.print(String.format("%c[%dC",escCode,60));	//move cursor to column position
		System.out.print(String.format("%c[31m",escCode)); 		//change color
		System.out.println("		       ENEMY'S BATTLEGROUND");
		System.out.print(String.format("%c[0m",escCode)); 		//reset color
		System.out.print(String.format("%c[%dC",escCode,60));	//move cursor to column position
		System.out.println(gridHeader);
		System.out.print(String.format("%c[%dC",escCode,60));	//move cursor to column position
		System.out.println(gridTop);
		System.out.print(String.format("%c[%dC",escCode,60));	//move cursor to column position
		for(int i=0; i<rows; i++){
			System.out.print(String.format("%2d |", i+1));
			for(int j=0; j<cols-1; j++){
				System.out.print(gridCase);
			}
			System.out.println(gridCase);
			System.out.print(String.format("%c[%dC",escCode,60));	//move cursor to column position
			if(i != rows-1) {
				System.out.println(gridLine);
				System.out.print(String.format("%c[%dC",escCode,60));	//move cursor to column position
			}
		}
		System.out.println(this.gridBottom);
		
	}
	
}
