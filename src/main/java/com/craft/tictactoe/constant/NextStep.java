package com.craft.tictactoe.constant;

public enum NextStep {
	
	NEW_GAME_NEXT_STEP_COMPUTER("Please Set the Board in order to start with the play"),
	NEW_GAME_NEXT_STEP_PLAYER("Please Set the Board and wait for the next player to join"),
	GAME_ERROR("Please try gain after some time"),
	GAME_EXIT("Please create new game if you want to play again."),
	GAME_NEXT_TURN_COMPUTER("Please play your next move"),
	GAME_NEXT_TURN_PLAYER("Please wait for the other player to play the next move"),
	PLAYER_TURN("Waiting for your move."),
	OTHER_PLAYER_TURN("Wait for the other player to take its move."),
	PLAYER_WINNER("Exit the match. You can start a new game if you want to play further."),
	COMPUTER_WINNER("Exit the match. You can start a new game if you want to play further."),
	PLAYER_LOSER("You can definitely win next time. Exit the match. You can start a new game if you want to play further."),
	PLAYER_TIE("You can definitely win next time. Exit the match. You can start a new game if you want to play further."),
	PLAY_IN_PROGRESS("Please proceed with your next move."),
	WAITING_FOR_PLAYER_TO_SET_BOARD("Game has started. Please set the board."),
	WAITING_FOR_ANOTHER_PLAYER_TO_JOIN("Let the other player join the game.");
	
	private String message = "";
	
	private NextStep(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
