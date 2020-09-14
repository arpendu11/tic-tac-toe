package com.craft.tictactoe.resources;

public class WhoseTurnResource extends BaseResource {
	private String boardStructure;

	public WhoseTurnResource(Long gameId, String message, String next_step, String boardStructure) {
		super(gameId, message, next_step);
		this.boardStructure = boardStructure;
	}

	public String getBoardStructure() {
		return boardStructure;
	}

	public void setBoardStructure(String boardStructure) {
		this.boardStructure = boardStructure;
	}
}
