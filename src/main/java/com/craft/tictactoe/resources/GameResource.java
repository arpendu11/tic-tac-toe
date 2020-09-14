package com.craft.tictactoe.resources;

public class GameResource extends BaseResource {
	
	private boolean success;
	
	public GameResource(Long gameId, String message, String next_step, boolean success) {
		super(gameId, message, next_step);
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
