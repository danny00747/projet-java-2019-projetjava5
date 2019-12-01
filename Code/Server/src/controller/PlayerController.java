/**
 * @author Martin Michotte
 * @date 23/11/2019
 */

package controller;

import model.*;
import view.*;


/**
 * This class is currently not usefull since the syntax/input control is made whitin the model. 
 * 
 */
public class PlayerController {
	private PlayerModel model;
    private PlayerView view = null;

	public PlayerController(PlayerModel model) {
		this.model = model;
	}

	public void addView(PlayerView view) {
		this.view = view;
	}
}