package com.craft.tictactoe.prototype;

import com.craft.tictactoe.entity.Player;

public class PlayerPrototype {

	public static Player aPlayer() {
		Player p = new Player("prototypeUser");
		return p;
	}
}
