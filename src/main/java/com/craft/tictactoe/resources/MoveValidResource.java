package com.craft.tictactoe.resources;

import com.craft.tictactoe.constant.PlayerStatus;

public class MoveValidResource extends BaseResource {
	private boolean success;
	private PlayerStatus state;
	private String[][] board;
	
	public MoveValidResource(Long gameId, String message, String next_step, boolean success, PlayerStatus state, String[][] board) {
		super(gameId, message, next_step);
		this.success = success;
		this.state = state;
		this.setBoard(board);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public PlayerStatus getState() {
		return state;
	}

	public void setState(PlayerStatus state) {
		this.state = state;
	}

	public String[][] getBoard() {
		return board;
	}

	public void setBoard(String[][] board) {
		this.board = board;
	}
}
