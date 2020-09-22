package com.craft.tictactoe.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PlayerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PlayerController playerController;
	
	@BeforeEach
	void setup() throws Exception {
		String inputJson = "{\"userName\": \"testEach\", \"type\": \"COMPUTER\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/game/new")
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder);
	}
	
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
	
	@Test
	public void getNextTurnTest() throws Exception {
		String URI = "/player/turn?userName=testEach";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(URI)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.OK.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void getNextTurnNotFoundTest() throws Exception {
		String URI = "/player/turn?userName=testEachNot";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(URI)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.NOT_FOUND.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void playNextMoveTest() throws Exception {
		String URI = "/player/move";
		String inputJson = "{\"userName\": \"testEach\", \"row\": 1, \"column\": 1}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.OK.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void playNextMoveNotAcceptableTest() throws Exception {
		String newJson = "{\"userName\": \"testNotAccept\", \"type\": \"COMPUTER\"}";
		RequestBuilder requestBuilderNew = MockMvcRequestBuilders
				.post("/game/new")
				.accept(MediaType.APPLICATION_JSON).content(newJson)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilderNew);
		
		String setJson = "{\"userName\": \"testNotAccept\", \"size\": 1, \"marker\": \"CIRCLE\"}";
		RequestBuilder requestBuilderSet = MockMvcRequestBuilders
				.post("/game/setBoard")
				.accept(MediaType.APPLICATION_JSON).content(setJson)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilderSet);
		
		String URI = "/player/move";
		String inputJson = "{\"userName\": \"testNotAccept\", \"row\": 1, \"column\": 1}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.NOT_ACCEPTABLE.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void playNextMoveNotFoundTest() throws Exception {
		String URI = "/player/move";
		String inputJson = "{\"userName\": \"testEachNot\", \"row\": 1, \"column\": 1}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.NOT_FOUND.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void playNextMoveBadRequestTest() throws Exception {
		String URI = "/player/move";
		String inputJson = "{\"userName\": \"\", \"row\": 1, \"column\": 1}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(response.getStatus());
	}
		
	@AfterEach
	void cleanUp() {}
}
