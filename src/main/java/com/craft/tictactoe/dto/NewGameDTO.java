package com.craft.tictactoe.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.craft.tictactoe.constant.GameType;

public class NewGameDTO extends PlayerDTO {
	
	@NotNull
	@NotEmpty(message = "type can be either \"human\" or \"computer\"")
	private GameType type;

	public GameType getType() {
		return type;
	}	
}
