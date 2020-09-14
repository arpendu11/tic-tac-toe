package com.craft.tictactoe.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PlayerDTO {
	@NotNull
	@NotEmpty(message = "username cannot be empty")
	private String userName;

	public String getUserName() {
		return userName;
	}	
}
