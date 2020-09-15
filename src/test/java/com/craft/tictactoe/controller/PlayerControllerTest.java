package com.craft.tictactoe.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PlayerControllerTest {
	
	@Autowired
	private PlayerController playerController;
	
	@BeforeEach
	void setup() {}
	
	@Test
	public void createNewPlayerTest() throws Exception {
		String checkPlayer = "Player [gameId=null, userName=test12, marker=null, status=null, turn=false]"; 
		
		assertThat(playerController.createNewPlayer("test12").toString()).isEqualTo(checkPlayer);
	}
	
	@Test
	@Order(4)
	public void checkPlayerTest() {
		playerController.createNewPlayer("test123");
		assertThat(playerController.checkPlayer("test123")).isEqualTo(true);
	}
		
	@AfterEach
	void cleanUp() {}
}
