package com.craft.tictactoe.exceptions;

public class UnsupportedCoordinateException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The move coordinate is not valid, it must be something like {row: 1, column: 2}. The vlues should be greater than 0 and less tahn the size of the board.";
	
	public UnsupportedCoordinateException() {
		super(MESSAGE);
	}

}
