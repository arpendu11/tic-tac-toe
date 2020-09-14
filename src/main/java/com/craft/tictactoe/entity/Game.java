package com.craft.tictactoe.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.craft.tictactoe.constant.GameStatus;
import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.constant.PlayerStatus;

public class Game {

	private final Logger LOGGER = LoggerFactory.getLogger(Game.class);

	private Long id;

	@Size(max = 2)
	private List<Player> players;

	private int size;

	private GameType type;

	private GameStatus status;

	private int noOfTurns;
	
	private static final AtomicLong gameCounter = new AtomicLong();
	private String[][] board = new String[size][size];

	public Game(Player player, GameType type) {
		id = gameCounter.incrementAndGet();
		player.setGameId(id);
		player.setTurn(true);
		player.setStatus(PlayerStatus.PLAYING);
		players = new ArrayList<>();
		players.add(player);
		this.type = type;
		this.status = GameStatus.WAITING_FOR_PLAYER_TO_SET_BOARD;
		if (type == GameType.COMPUTER) {
			Player computer = new Player(GameType.COMPUTER.toString());
			computer.setGameId(id);
			computer.setTurn(false);
			computer.setStatus(PlayerStatus.PLAYING);
			players.add(computer);
			LOGGER.info("Created a new game: " + id
					+ " and ready to play with Computer. Please set the board to start placing your move.");
		} else {
			// TODO: Add logic to handle if Human joins the game
			LOGGER.info("Created a new game: " + id + " and waiting for another player to join.");
		}
		cleanBoard();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public GameType getType() {
		return type;
	}

	public void setType(GameType type) {
		this.type = type;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public int getNoOfTurns() {
		return noOfTurns;
	}

	public void setNoOfTurns(int noOfTurns) {
		this.noOfTurns = noOfTurns;
	}

	public String[][] getBoard() {
		return board;
	}

	public void setBoard(String[][] board) {
		this.board = board;
	}

	public void addPlayer(Player player, MarkerType marker) {
		player.setGameId(id);
		player.setTurn(false);
		player.setMarker(marker);
		player.setStatus(PlayerStatus.PLAYING);
		if (isAvailable()) {
			players.add(player);
		} else {
			LOGGER.error("Please create a new game to play. This game is occupied with 2 players.");
		}

	}

	public boolean isAvailable() {
		return (players.size() < 2) ? true : false;
	}

	public void switchTurn() {
		if (players.size() == 2) {
			if (players.get(0).isTurn()) {
				players.get(0).setTurn(false);
				players.get(1).setTurn(true);
			} else {
				players.get(0).setTurn(true);
				players.get(1).setTurn(false);
			}
		}
	}

	private void cleanBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = "";
			}
		}
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", players=" + players + ", size=" + size + ", type=" + type + ", status=" + status
				+ ", board=" + Arrays.deepToString(board) + "]";
	}
}
