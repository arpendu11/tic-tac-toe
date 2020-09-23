package com.craft.tictactoe.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;

@SpringBootTest
public class GameServiceTest {
	
	Player newPlayer;
	Game game;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	
	@BeforeEach
	void setup() {
		newPlayer = new Player("testUser");

		playerService.createNewPlayer(newPlayer.getUserName());
		gameService.createNewGame(newPlayer, GameType.COMPUTER);
	}
	
	@Test
	@Order(1)
	public void createNewGameTest() {
		newPlayer = new Player("testUser1");
		String checkGame = "Game [id=7, players=[Player [gameId=7, userName=testUser1, marker=null, status=PLAYING, turn=true],"
				+ " Player [gameId=7, userName=COMPUTER, marker=null, status=PLAYING, turn=false]], size=0, type=COMPUTER,"
				+ " noOfTurns=0, status=WAITING_FOR_PLAYER_TO_SET_BOARD, board=[]]";
		assertThat(gameService.createNewGame(newPlayer, GameType.COMPUTER).toString()).isEqualTo(checkGame);
	}
	
	@Test
	@Order(1)
	public void setGamePropertiesTest() {
		String checkGame = "Game [id=5, players=[Player [gameId=5, userName=testUser, marker=CIRCLE, status=PLAYING, turn=true],"
				+ " Player [gameId=5, userName=COMPUTER, marker=CROSS, status=PLAYING, turn=false]], size=3, type=COMPUTER,"
				+ " noOfTurns=0, status=IN_PROGRESS, board=[[[1], [2], [3]], [[4], [5], [6]], [[7], [8], [9]]]]";
		assertThat(gameService.setGameProperties(newPlayer.getUserName(), 3, MarkerType.CIRCLE).toString()).isEqualTo(checkGame);
	}
	
	@AfterEach
	void cleanUp() {}
}
