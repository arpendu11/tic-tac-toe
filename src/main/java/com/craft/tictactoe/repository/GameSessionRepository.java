package com.craft.tictactoe.repository;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.craft.tictactoe.constant.GameStatus;
import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;

public class GameSessionRepository {
	private final Logger LOGGER = LoggerFactory.getLogger(GameSessionRepository.class);
	private static GameSessionRepository instance;
	private static Map<Long, Game> session;
	
	public GameSessionRepository() {
		cleanRepo();
		session =  new ConcurrentHashMap<Long, Game>();
	}

	public static GameSessionRepository getInstance(){
		if(instance == null){
			instance = new GameSessionRepository();
		}		
		return instance;
	}

	public synchronized Game getGameSession(Long id) {
		return session.get(id);
	}

	public synchronized Game getGameSessionByUsername(String username) {
		for (Game s : session.values()) {
			for (Player p : s.getPlayers()) {
				if (p.getUserName().equals(username)) {
					return s;
				}
			}
		}
		return null;
	}

	public synchronized void createGameSession(Game game) {
		session.putIfAbsent(game.getId(), game);
		LOGGER.info("Game id: " + game.getId() + " is allocated with a gaming session.");
	}

	public synchronized Game findAvailableSession() {
		return session.values().stream().filter(g -> g.isAvailable()).findFirst().get();
	}

	public synchronized Game addToAvailableSession(Player player, MarkerType marker) {
		Game game = null;
		try {
			game = findAvailableSession();
			game.addPlayer(player, marker);
		} catch (NoSuchElementException e) {
			game = new Game(player, GameType.HUMAN);
			session.putIfAbsent(game.getId(), game);
		}
		return game;
	}

	public synchronized Game removePlayer(String userName) {
		Game game = null;
		Player player = null;
		boolean validGame = false;
		for (Game s : session.values()) {
			for (Player p : s.getPlayers()) {
				if (p.getUserName().equals(userName)) {
					validGame = true;
					player = p;
				}
			}
			if (validGame) {
				s.setStatus(GameStatus.CLOSED);
				game = s;
				s.getPlayers().remove(player);
				endGameSession(s.getId());
			}
		}
		return game;
	}

	public synchronized void endGameSession(Long id) {
		session.remove(id);
	}	
	
	private synchronized void cleanRepo() {
		session = null;
	}
}
