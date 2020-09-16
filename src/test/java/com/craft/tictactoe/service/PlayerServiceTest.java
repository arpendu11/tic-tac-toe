package com.craft.tictactoe.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlayerServiceTest {
	
	Player newPlayer;
	Game game;
	
	@Autowired
	private PlayerService playerService;
	
	@BeforeEach
	void setup() {
		newPlayer = new Player("testPlayer");
		game = new Game(newPlayer, GameType.COMPUTER);
		playerService.createNewPlayer(newPlayer.getUserName());
	}
	
	@Test
	@Order(3)
	public void createNewPlayerTest() {
		String checkPlayer = "Player [gameId=null, userName=testPlayer, marker=null, status=null, turn=false]";
		
		assertThat(playerService.createNewPlayer(newPlayer.getUserName()).toString()).isEqualTo(checkPlayer);
	}
	
	@Test
	@Order(4)
	public void checkPlayerPlayingTest() {
		assertThat(playerService.checkPlayerPlaying(newPlayer.getUserName())).isEqualTo(true);
	}
	
	@AfterEach
	void cleanUp() {}
}
