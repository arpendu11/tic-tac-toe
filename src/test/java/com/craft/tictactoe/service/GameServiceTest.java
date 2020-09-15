package com.craft.tictactoe.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.exceptions.PlayerNotFoundException;

@SpringBootTest
public class GameServiceTest {
	
	Player newPlayer;
	Player newPlayer1;
	Player newPlayer2;
	Game game;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	
	@BeforeEach
	void setup() {
		newPlayer = new Player("testUser");

		playerService.createNewPlayer(newPlayer.getUserName());
	}
	
	@Test
	@Order(1)
	public void createNewGameTest() throws PlayerNotFoundException {
		String checkGame = "Game [id=2, players=[Player [gameId=2, userName=testUser, marker=null, status=PLAYING, turn=true],"
				+ " Player [gameId=2, userName=COMPUTER, marker=null, status=PLAYING, turn=false]], size=0, type=COMPUTER,"
				+ " noOfTurns=0, status=WAITING_FOR_PLAYER_TO_SET_BOARD, board=[]]";
		assertThat(gameService.createNewGame(newPlayer, GameType.COMPUTER).toString()).isEqualTo(checkGame);
	}
	
	@AfterEach
	void cleanUp() {}
}
