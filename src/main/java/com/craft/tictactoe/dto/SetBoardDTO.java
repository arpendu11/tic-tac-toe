package com.craft.tictactoe.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.craft.tictactoe.constant.MarkerType;

public class SetBoardDTO extends PlayerDTO {
	
	@Min(3)
	@NotNull
	@NotEmpty(message = "size cannot be empty. Pass any integer greater than 1")
	private int size;
	
	@NotNull
	@NotEmpty(message = "marker cannot be empty. marker can be either \"O\" or \"X\"")
	private MarkerType marker;

	public int getSize() {
		return size;
	}

	public MarkerType getMarker() {
		return marker;
	}
}
