package com.craft.tictactoe.converter;

import org.springframework.stereotype.Component;

import com.craft.tictactoe.constant.GameStatus;
import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.GenericMessage;
import com.craft.tictactoe.constant.NextStep;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.resources.GameResource;

@Component
public class GameResourceConverter extends AbstractConverter<Game, GameResource> {

	@Override
	public GameResource convert(Game source) {
		String message = null;
		String next_step = null;
		boolean success = false;
		if (source != null) {
			success = true;
			if (source.getStatus() == GameStatus.WAITING_FOR_PLAYER_TO_SET_BOARD) {
				message = GenericMessage.NEW_GAME_CEATED.getMessage();
				if (source.getType() == GameType.COMPUTER) {
					next_step = NextStep.NEW_GAME_NEXT_STEP_COMPUTER.getMessage();
				} else {
					next_step = NextStep.NEW_GAME_NEXT_STEP_PLAYER.getMessage();
				}
			} else if (source.getStatus() == GameStatus.CLOSED) {
				message = GenericMessage.GAME_EXITED.getMessage();
				next_step = NextStep.GAME_EXIT.getMessage();
			}
		} else {
			message = GenericMessage.GAME_ERROR.getMessage();
			next_step = NextStep.GAME_ERROR.getMessage();
		}

		GameResource resource = new GameResource(source.getId(), message, next_step, success);
		return resource;
	}

}
