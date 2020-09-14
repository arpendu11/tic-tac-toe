package com.craft.tictactoe.resources;

import com.craft.tictactoe.constant.GameStatus;

public class GameStatusResource extends BaseResource{
	private GameStatus status;
	private String boardStructure;
	
	public GameStatusResource(Long gameId, String message, String next_step, GameStatus status,
			String boardStructure) {
		super(gameId, message, next_step);
		this.status = status;
		this.boardStructure = boardStructure;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public String getBoardStructure() {
		return boardStructure;
	}

	public void setBoardStructure(String boardStructure) {
		this.boardStructure = boardStructure;
	}
}
