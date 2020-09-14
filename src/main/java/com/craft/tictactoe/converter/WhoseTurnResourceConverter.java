package com.craft.tictactoe.converter;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craft.tictactoe.constant.GenericMessage;
import com.craft.tictactoe.constant.NextStep;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.resources.WhoseTurnResource;
import com.craft.tictactoe.service.GameService;

@Component
public class WhoseTurnResourceConverter extends AbstractConverter<Player, WhoseTurnResource> {
	
	@Autowired
	private GameService gameService;
	
	@Override
	public WhoseTurnResource convert(Player source) {
		String message = null;
		String next_step = null;
		if (source != null) {
			if (source.isTurn()) {
				message = GenericMessage.PLAYER_TURN.getMessage();
				next_step = NextStep.PLAYER_TURN.getMessage();
			} else {
				message = GenericMessage.OTHER_PLAYER_TURN.getMessage();
				next_step = NextStep.OTHER_PLAYER_TURN.getMessage();
			}
		}
		
		String[][] board = gameService.getGameByPlayer(source.getUserName()).getBoard();

		WhoseTurnResource resource = new WhoseTurnResource(source.getGameId(), message, next_step, Arrays.deepToString(board));
		return resource;
	}

}
