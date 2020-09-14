package com.craft.tictactoe.constant;

public enum GameStatus {
	WAITING_FOR_PLAYER_TO_SET_BOARD,
	WAITING_FOR_ANOTHER_PLAYER_TO_JOIN,
    IN_PROGRESS,
    FIRST_PLAYER_WON,
    SECOND_PLAYER_WON,
    COMPUTER_WON,
    MATCH_TIE,
    TIMEOUT,
    CLOSED
}
