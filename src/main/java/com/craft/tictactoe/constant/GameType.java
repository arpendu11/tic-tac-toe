package com.craft.tictactoe.constant;

public enum GameType {
	HUMAN("human"),
	COMPUTER("computer");
	
	private String type;

	private GameType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
