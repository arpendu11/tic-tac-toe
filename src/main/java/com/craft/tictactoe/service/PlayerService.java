package com.craft.tictactoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.repository.PlayerPoolRepository;

@Service
public class PlayerService {

	
	// create new player by taking playerDTO
	// listPlayers all
	// getPlayer by username
	
	private final PlayerPoolRepository playerPool = PlayerPoolRepository.getInstance();
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameLogicService gameLogic;
	
	public Player createNewPlayer(String userName) {
        Player newPlayer = new Player(userName);
        playerPool.addPlayer(userName);
        return newPlayer;
    }
	
	public boolean checkPlayerPlaying(String username) {
		return playerPool.checkPlayer(username);
	}

	public void removePlayer(String userName) {
		playerPool.removePlayer(userName);
	}
	
	public void cleanUp() {
		playerPool.cleanRepo();
	}

	public Player getPlayer(String userName) {
		Game game = gameService.getGameByPlayer(userName);
		return game.getPlayers().stream().filter(e -> e.getUserName().equals(userName)).findFirst().get();
	}

	public Player playNextMove(Player player, int row, int column) {
		gameLogic.playNextMove(player, row, column);
		Player updatedPlayer = getPlayer(player.getUserName());
		return updatedPlayer;
	}
	
}
