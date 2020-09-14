package com.craft.tictactoe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craft.tictactoe.constant.GameStatus;
import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.dto.SetBoardDTO;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.exceptions.PlayerNotFoundException;
import com.craft.tictactoe.repository.GameSessionRepository;

@Service
public class GameService {

	// create new game by taking gameDTO and player
	// setBoard existing game by taking boardDTO and player
	// update gameStatus
	// getAllPlayers playing game by taking player
	// getGame by taking gameId

	private final Logger logger = LoggerFactory.getLogger(GameService.class);
	private final GameSessionRepository gameSession = GameSessionRepository.getInstance();

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private GameLogicService gameLogic;

	public Game createNewGame(Player newPlayer, GameType type) {
		Game game = new Game(newPlayer, type);
		gameSession.createGameSession(game);
		logger.info("New game created: "+ game.toString());
		return game;
	}

	public Game getGameByPlayer(String username) {
		Game game = gameSession.getGameSessionByUsername(username);
		logger.info("Present State of the game: "+ game.toString());
		return game;
	}
	
	public Game endGame(String username) throws PlayerNotFoundException {
		if (playerService.checkPlayerPlaying(username)) {
			playerService.removePlayer(username);
			Game game = gameSession.removePlayer(username);

			if (game.getType().equals(GameType.HUMAN)) {
				playerService.removePlayer(game.getPlayers().get(0).getUserName());
			}
			logger.info("Present State of the game: "+ game.toString());
			return game;
		} else {
			logger.error("Player {variable} not found in the system.", username);
			throw new PlayerNotFoundException();
		}
	}

	public Game setGameProperties(SetBoardDTO board) {
		Game game = gameSession.getGameSessionByUsername(board.getUserName());
		game.setSize(board.getSize());
		if (game.getType() == GameType.COMPUTER) {
			game.getPlayers().get(0).setMarker(board.getMarker());
			if (board.getMarker() == MarkerType.CROSS) {
				game.getPlayers().get(1).setMarker(MarkerType.CIRCLE);
			} else {
				game.getPlayers().get(1).setMarker(MarkerType.CROSS);
			}
			game.setStatus(GameStatus.IN_PROGRESS);
		} else {
			game.getPlayers().stream().filter(e -> e.getUserName().equals(board.getUserName())).findFirst().get().setMarker(board.getMarker());
			if (game.isAvailable()) {
				game.setStatus(GameStatus.WAITING_FOR_ANOTHER_PLAYER_TO_JOIN);
			} else {
				game.setStatus(GameStatus.IN_PROGRESS);
			}
		}
		game.setBoard(gameLogic.createBoard(board.getSize()));
		game.setNoOfTurns(0);
		logger.info("Present State of the game: "+ game.toString());
		return game;
	}
}
