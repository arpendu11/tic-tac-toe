package com.craft.tictactoe.entity;

import org.springframework.context.annotation.Scope;

import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.constant.PlayerStatus;

@Scope("prototype")
public class Player {
	
	private Long gameId;
	private String userName;
	
	private MarkerType marker;
	
	private PlayerStatus status;
	
	private boolean turn;

	public Player(String userName) {
		this.userName = userName;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public MarkerType getMarker() {
		return marker;
	}

	public void setMarker(MarkerType marker) {
		this.marker = marker;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	@Override
	public String toString() {
		return "Player [gameId=" + gameId + ", userName=" + userName + ", marker=" + marker + ", status=" + status
				+ ", turn=" + turn + "]";
	}
}
