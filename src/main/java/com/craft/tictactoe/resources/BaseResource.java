package com.craft.tictactoe.resources;

public class BaseResource {
	private Long gameId;
	private String message;
	private String nextStep;
	
	public BaseResource(Long gameId, String message, String next_step) {
		this.gameId = gameId;
		this.message = message;
		this.nextStep = next_step;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String next_step) {
		this.nextStep = next_step;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
}
