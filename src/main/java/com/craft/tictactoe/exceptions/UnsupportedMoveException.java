package com.craft.tictactoe.exceptions;

public class UnsupportedMoveException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The space in the board is already occupied by one of the marker. Please try again !";
	
	public UnsupportedMoveException() {
		super(MESSAGE);
	}

}
