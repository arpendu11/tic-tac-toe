package com.craft.tictactoe.exceptions;

public class PlayerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Player not found playing any game";
	
	public PlayerNotFoundException() {
		super(MESSAGE);
	}	

}
