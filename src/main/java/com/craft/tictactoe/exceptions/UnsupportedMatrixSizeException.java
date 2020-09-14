package com.craft.tictactoe.exceptions;

public class UnsupportedMatrixSizeException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Unsupported size of the board, size should be an integer and greater than 1";
	
	public UnsupportedMatrixSizeException() {
		super(MESSAGE);
	}

}
