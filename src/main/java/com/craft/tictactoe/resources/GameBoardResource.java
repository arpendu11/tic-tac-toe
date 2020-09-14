package com.craft.tictactoe.resources;

public class GameBoardResource extends BaseResource {
	private boolean success;
	private String boardStructure;
	
	public GameBoardResource(Long gameId, String message, String next_step, boolean success, String boardStructure) {
		super(gameId, message, next_step);
		this.success = success;
		this.boardStructure = boardStructure;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getBoardStructure() {
		return boardStructure;
	}

	public void setBoardStructure(String boardStructure) {
		this.boardStructure = boardStructure;
	}
}
