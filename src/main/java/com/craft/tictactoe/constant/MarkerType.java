package com.craft.tictactoe.constant;

public enum MarkerType {
	CIRCLE("O"), CROSS("X");

	private String shape;

	private MarkerType(String shape) {
		this.shape = shape;
	}

	public String getShape() {
		return shape;
	}
}
