package com.craft.tictactoe.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.craft.tictactoe.constant.EnumValidator;
import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.constant.PlayerStatus;

public class Player {
	
	private Long gameId;
	
	@NotNull
	@NotEmpty(message = "username cannot be empty")
	private String userName;
	
	@NotBlank
	@EnumValidator(
		     enumClazz = MarkerType.class,
		     message = "This error is coming from the MarkerType class"
		 )
	private MarkerType marker;
	
	@NotBlank
	@EnumValidator(
		     enumClazz = PlayerStatus.class,
		     message = "This error is coming from the PlayerStatus class"
		 )
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
