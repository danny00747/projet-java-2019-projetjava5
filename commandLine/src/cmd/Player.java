/**
 * 
 */
package cmd;

/**
 * @author martinmichotte
 *
 */
public class Player {
	
	Grid grids;
	
	public Player() {
		grids = new Grid();
		grids.displayGrid();
	}
	
	
	/**
	 * Function that takes a case coordinate as parameter and shoots a single time in that case.
	 *
	 * @param coords {String} - the coordinates of the grid-case to shoot, format example: "H4" 
	 */
	protected void singelShot(String coords, boolean isOutGoing) {
		String colInput = coords.substring(0,1);
		String rowInput = coords.substring(1);
		int rowIndex =0;
		int colIndex =0;
		int gridSelect=0;
		
		/**
		 * You shoot in the ennemy's grid
		 */
		if(isOutGoing) {
			gridSelect=60;
		}
		
		/**
		 * you're being shot by the ennemy
		 */
		else {
			gridSelect=0;
		}
		
		for(int i=0;i<grids.rowHeader.length;i++){
			if(rowInput.contentEquals(grids.rowHeader[i])) {
				rowIndex = i;
			}
		}
		for(int i=0;i<grids.colHeader.length;i++){
			if(colInput.contentEquals(grids.colHeader[i])) {
				colIndex = i;
			}
		}
		//cursorRowPos-2+(2*rowIndex)
		System.out.print(String.format("%c[%dA",grids.escCode,26-(2*rowIndex)));			//move cursor to row position
		System.out.print(String.format("%c[%dC",grids.escCode,4+(4*colIndex)+gridSelect));	//move cursor to column position
		System.out.print(String.format("%c[42m",grids.escCode)); 							//change color
		System.out.print(String.format(" %-2c", '✖'));										//change grid value
		System.out.print(String.format("%c[0m",grids.escCode)); 							//reset color
		System.out.print(String.format("|"));	
		System.out.print(String.format("%c[%d;%df",grids.escCode,999,0)); 					//move back to bottom
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Player Me = new Player();
		
		Me.singelShot("H7", true);
		Me.singelShot("B6", false);
		Me.singelShot("M13",false);

	}
	
}
