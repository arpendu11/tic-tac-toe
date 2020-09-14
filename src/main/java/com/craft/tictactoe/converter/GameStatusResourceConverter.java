package com.craft.tictactoe.converter;

import org.springframework.stereotype.Component;

import com.craft.tictactoe.constant.GenericMessage;
import com.craft.tictactoe.constant.NextStep;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.resources.GameStatusResource;

@Component
public class GameStatusResourceConverter extends AbstractConverter<Game, GameStatusResource> {

	@Override
	public GameStatusResource convert(Game source) {
		String message = null;
		String next_step = null;
		if (source != null) {
			switch (source.getStatus()) {
			case WAITING_FOR_PLAYER_TO_SET_BOARD:
				message = GenericMessage.WAITING_FOR_PLAYER_TO_SET_BOARD.getMessage();
				next_step = NextStep.WAITING_FOR_PLAYER_TO_SET_BOARD.getMessage();
				break;

			case WAITING_FOR_ANOTHER_PLAYER_TO_JOIN:
				message = GenericMessage.WAITING_FOR_ANOTHER_PLAYER_TO_JOIN.getMessage();
				next_step = NextStep.WAITING_FOR_ANOTHER_PLAYER_TO_JOIN.getMessage();
				break;

			case IN_PROGRESS:
				message = GenericMessage.GAME_IN_PROGRESS.getMessage();
				next_step = NextStep.PLAY_IN_PROGRESS.getMessage();
				break;

			case FIRST_PLAYER_WON:
				message = GenericMessage.PLAYER_WINNER.getMessage();
				next_step = NextStep.PLAYER_WINNER.getMessage();
				break;

			case SECOND_PLAYER_WON:
				message = GenericMessage.PLAYER_WINNER.getMessage();
				next_step = NextStep.PLAYER_WINNER.getMessage();
				break;
				
			case COMPUTER_WON:
				message = GenericMessage.COMPUTER_WINNER.getMessage();
				next_step = NextStep.COMPUTER_WINNER.getMessage();
				break;
				
			case MATCH_TIE:
				message = GenericMessage.PLAYER_TIE.getMessage();
				next_step = NextStep.PLAYER_TIE.getMessage();
				break;
				
			case CLOSED:
				message = GenericMessage.GAME_EXITED.getMessage();
				next_step = NextStep.GAME_EXIT.getMessage();
				break;
			
			default:
				message = GenericMessage.GAME_ERROR.getMessage();
				next_step = NextStep.GAME_ERROR.getMessage();
				break;
			}
		}

		// Print board structure
		
		GameStatusResource resource = new GameStatusResource(source.getId(), message, next_step, source.getStatus(), "structure");
		return resource;
	}

}
