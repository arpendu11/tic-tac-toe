package com.craft.tictactoe.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MoveDTO extends PlayerDTO {
	
	@Min(1)
	@NotNull
	@NotBlank(message = "row cannot be empty")
	private int row;
	
	@Min(1)
	@NotNull
	@NotBlank(message = "column cannot be empty")
	private int column;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
