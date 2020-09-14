package com.craft.tictactoe.constant;

import com.craft.tictactoe.exceptions.UnsupportedShapeException;

public enum MarkerType {
	CIRCLE("O"), CROSS("X");

	private String shape;

	private MarkerType(String shape) {
		this.shape = shape;
	}

	public static MarkerType getPlayerShape(String shapeStr) throws UnsupportedShapeException {
		for (MarkerType shape : MarkerType.values()) {
			if (shape.shape.equalsIgnoreCase(shapeStr)) {
				return shape;
			}
		}
		throw new UnsupportedShapeException();
	}

	public String getShape() {
		return shape;
	}
}
