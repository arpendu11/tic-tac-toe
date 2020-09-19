package com.craft.tictactoe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craft.tictactoe.constant.GameStatus;
import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.constant.PlayerStatus;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.exceptions.PlayerNotFoundException;
import com.craft.tictactoe.repository.GameSessionRepository;

@Service
public class GameService {

	private final Logger logger = LoggerFactory.getLogger(GameService.class);
	private final GameSessionRepository gameSession = GameSessionRepository.getInstance();

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameLogicService gameLogic;

	public Game createNewGame(Player newPlayer, GameType type) {
		Game game = new Game(newPlayer, type);
		gameSession.createGameSession(game);
		logger.info("New game created: " + game.toString());
		return game;
	}

	public Game getGameByPlayer(String username) {
		Game game = gameSession.getGameSessionByUsername(username);
		logger.info("Present State of the game: " + game.toString());
		return game;
	}

	public Game endGame(String username) throws PlayerNotFoundException {
		Game game = getGameByPlayer(username);
		if (game.getType().equals(GameType.HUMAN)) {
			for (Player p: game.getPlayers()) {
				if (!p.getUserName().equals(username)) {
					playerService.removePlayer(p.getUserName());
				}
			}
		}
		playerService.removePlayer(username);
		Game gameDeleted = gameSession.removePlayer(username);
		logger.info("Present State of the game: " + gameDeleted.toString());
		return gameDeleted;
	}

	public Game setGameProperties(String username, int size, MarkerType marker) {
		Game game = gameSession.getGameSessionByUsername(username);
		if (size == 1) {
			game.setSize(size);
			game.setStatus(GameStatus.FIRST_PLAYER_WON);
			game.getPlayers().stream().filter(e -> e.getUserName().equals(username)).findFirst().get()
					.setStatus(PlayerStatus.WINNER);
			game.setBoard(gameLogic.createBoard(size));
			game.setNoOfTurns(0);
			return game;
		}
		game.setSize(size);
		if (game.getType() == GameType.COMPUTER) {
			game.getPlayers().get(0).setMarker(marker);
			if (marker == MarkerType.CROSS) {
				game.getPlayers().get(1).setMarker(MarkerType.CIRCLE);
			} else {
				game.getPlayers().get(1).setMarker(MarkerType.CROSS);
			}
			game.setStatus(GameStatus.IN_PROGRESS);
		} else {
			game.getPlayers().stream().filter(e -> e.getUserName().equals(username)).findFirst().get()
					.setMarker(marker);
			if (game.isAvailable()) {
				game.setStatus(GameStatus.WAITING_FOR_ANOTHER_PLAYER_TO_JOIN);
			} else {
				game.setStatus(GameStatus.IN_PROGRESS);
			}
		}
		game.setBoard(gameLogic.createBoard(size));
		game.setNoOfTurns(0);
		logger.info("Present State of the game: " + game.toString());
		return game;
	}
}
