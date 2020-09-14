package com.craft.tictactoe.exceptions;

public class UnsupportedShapeException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Unsupported marker, marker can be either 'O' or 'X'";
	
	public UnsupportedShapeException() {
		super(MESSAGE);
	}

}
