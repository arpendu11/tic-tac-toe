package com.craft.tictactoe.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craft.tictactoe.constant.GenericMessage;
import com.craft.tictactoe.constant.NextStep;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.resources.MoveValidResource;
import com.craft.tictactoe.service.GameService;

@Component
public class MoveValidResourceConverter extends AbstractConverter<Player, MoveValidResource> {
	
	@Autowired
	private GameService gameService;
	
	@Override
	public MoveValidResource convert(Player source) {
		String message = null;
		String next_step = null;
		boolean success = false;
		if (source != null) {
			success = true;
			switch (source.getStatus()) {
			case WINNER:
				message = GenericMessage.PLAYER_WINNER.getMessage();
				next_step = NextStep.PLAYER_WINNER.getMessage();
				break;
			
			case LOSER:
				message = GenericMessage.PLAYER_LOSER.getMessage();
				next_step = NextStep.PLAYER_LOSER.getMessage();
				break;
				
			case TIE:
				message = GenericMessage.PLAYER_TIE.getMessage();
				next_step = NextStep.PLAYER_TIE.getMessage();
				break;
				
			case PLAYING:
				message = GenericMessage.PLAY_IN_PROGRESS.getMessage();
				next_step = NextStep.PLAY_IN_PROGRESS.getMessage();
				break;
			}
		}
		
		Game game = gameService.getGameByPlayer(source.getUserName());

		MoveValidResource resource = new MoveValidResource(source.getGameId(), message, next_step, success, source.getStatus(), game.getBoard());
		return resource;
	}

}
