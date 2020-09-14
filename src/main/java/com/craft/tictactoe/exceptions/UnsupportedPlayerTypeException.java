package com.craft.tictactoe.exceptions;

public class UnsupportedPlayerTypeException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Unsupported type of the player, player type can be \"human\" or \"computer\"";
	
	public UnsupportedPlayerTypeException() {
		super(MESSAGE);
	}

}
