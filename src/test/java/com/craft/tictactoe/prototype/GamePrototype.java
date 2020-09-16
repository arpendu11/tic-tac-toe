package com.craft.tictactoe.prototype;

import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;

public class GamePrototype {

	public static Game aGame() {
		Player player = PlayerPrototype.aPlayer();
        Game g = new Game(player, GameType.COMPUTER);
        return g;
    }
}
