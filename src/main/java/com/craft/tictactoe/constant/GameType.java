package com.craft.tictactoe.constant;

import com.craft.tictactoe.exceptions.UnsupportedShapeException;

public enum GameType {
	HUMAN("human"),
	COMPUTER("computer");
	
	private String type;

	private GameType(String type) {
		this.type = type;
	}

	public static GameType getPlayerShape(String typeStr) throws UnsupportedShapeException {
		for (GameType aType : GameType.values()) {
			if (aType.type.equalsIgnoreCase(typeStr)) {
				return aType;
			}
		}
		throw new UnsupportedShapeException();
	}

	public String getType() {
		return type;
	}
}
