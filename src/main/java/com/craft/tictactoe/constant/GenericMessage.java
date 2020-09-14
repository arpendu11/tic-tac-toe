package com.craft.tictactoe.constant;

public enum GenericMessage {
	
	NEW_GAME_CEATED("A new instance of the game has been successfully created."),
	GAME_ERROR("Some error occurred in the game. Please check after some time."),
	GAME_EXITED("The game session has been successfully closed."),
	GAME_IN_PROGRESS("The board is set. Game is in progress."),
	PLAYER_TURN("Its your turn. You can play the next move."),
	OTHER_PLAYER_TURN("Other player has to take its turn. Waiting for the other player to take his move."),
	PLAYER_WINNER("It was a valid and winning move. You have won the match"),
	COMPUTER_WINNER("Computer has won the match"),
	PLAYER_LOSER("It was a valid move. But unfortunately, it was not a winning move. You have lost the game."),
	PLAYER_TIE("It was a valid move. But unfortunately, it was not a winning move. The game has tied."),
	PLAY_IN_PROGRESS("It was a valid move.  But unfortunately, it was not a winning move. Please proceed with your next move."),
	WAITING_FOR_PLAYER_TO_SET_BOARD("Game has started. Waiting for the player to set the board."),
	WAITING_FOR_ANOTHER_PLAYER_TO_JOIN("Game has started. Waiting for another player to join the game.");
	
	private String message = "";
	
	private GenericMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
