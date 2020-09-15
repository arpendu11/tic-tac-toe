package com.craft.tictactoe.exceptions;

public class UnsupportedCoordinateException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The move coordinate is not valid, it must be something like {row: 1, column: 2}. The values should be greater than 0 and less than the size of the board.";
	
	public UnsupportedCoordinateException() {
		super(MESSAGE);
	}

}
