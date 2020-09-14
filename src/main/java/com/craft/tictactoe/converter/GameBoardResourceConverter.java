package com.craft.tictactoe.converter;

import org.springframework.stereotype.Component;

import com.craft.tictactoe.constant.GameStatus;
import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.GenericMessage;
import com.craft.tictactoe.constant.NextStep;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.resources.GameBoardResource;

@Component
public class GameBoardResourceConverter extends AbstractConverter<Game, GameBoardResource> {

	@Override
	public GameBoardResource convert(Game source) {
		String message = null;
		String next_step = null;
		boolean success = false;
		if (source != null) {
			success = true;
			if (source.getStatus() == GameStatus.IN_PROGRESS) {
				message = GenericMessage.GAME_IN_PROGRESS.getMessage();
				if (source.getType() == GameType.COMPUTER) {
					next_step = NextStep.GAME_NEXT_TURN_COMPUTER.getMessage();
				} else {
					next_step = NextStep.GAME_NEXT_TURN_PLAYER.getMessage();
				}
			}
		}
		
		// Print board structure 

		GameBoardResource resource = new GameBoardResource(source.getId(), message, next_step, success, "structure");
		return resource;
	}

}
