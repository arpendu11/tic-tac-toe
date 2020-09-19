package com.craft.tictactoe.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class GameControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
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
	public void createNewGameTest() throws Exception {
		String URI = "/game/new";		
		String inputJson = "{\"userName\": \"test1\", \"type\": \"COMPUTER\"}";
		String outputJson = "{\"gameId\":3,\"message\":\"A new instance of the game"
				+ " has been successfully created.\",\"nextStep\":\"Please Set the Board"
				+ " in order to start with the play\",\"success\":true}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.CREATED.value()).isEqualTo(response.getStatus());
		assertThat(outputJson).isEqualTo(response.getContentAsString());
	}
	
	@Test
	public void createNewGameBadRequestTest() throws Exception {
		String URI = "/game/new";		
		String inputJson = "{\"userName\": \"\", \"type\": \"COMPUTER\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void setGamingBoardTest() throws Exception {
		String URI = "/game/setBoard";
		String inputJson = "{\"userName\": \"testEach\", \"size\": 3, \"marker\": \"CROSS\"}";
		String outputJson = "{\"gameId\":2,\"message\":\"The board is set. Game is in"
				+ " progress.\",\"nextStep\":\"Please play your next move\",\"success\":true,"
				+ "\"boardStructure\":\"[[[1], [2], [3]], [[4], [5], [6]], [[7], [8], [9]]]\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.OK.value()).isEqualTo(response.getStatus());
		assertThat(outputJson).isEqualTo(response.getContentAsString());
	}
	
	@Test
	public void setGamingBoardNotFoundTest() throws Exception {
		String URI = "/game/setBoard";
		String inputJson = "{\"userName\": \"testEachNot\", \"size\": 3, \"marker\": \"CROSS\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.NOT_FOUND.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void setGamingBoardBadRequestTest() throws Exception {
		String URI = "/game/setBoard";
		String inputJson = "{\"userName\": \"\", \"size\": 3, \"marker\": \"CROSS\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void getCurrentStatusTest() throws Exception {
		String URI = "/game/status?userName=testEach";
		String outputJson = "{\"gameId\":1,\"message\":\"Game has started. Waiting for the player to set the board.\","
				+ "\"nextStep\":\"Game has started. Please set the board.\","
				+ "\"status\":\"WAITING_FOR_PLAYER_TO_SET_BOARD\",\"boardStructure\":\"[]\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(URI)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.OK.value()).isEqualTo(response.getStatus());
		assertThat(outputJson).isEqualTo(response.getContentAsString());
	}
	
	@Test
	public void getCurrentStatusNotFoundTest() throws Exception {
		String URI = "/game/status?userName=testEach1";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(URI)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.NOT_FOUND.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void exitGameTest() throws Exception {
		String URI = "/game/exit";
		String inputJson = "{\"userName\": \"testEach\"}";
		String outputJson = "{\"gameId\":1,\"message\":\"The game session has been successfully closed.\","
				+ "\"nextStep\":\"Please create new game if you want to play again.\",\"success\":true}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.OK.value()).isEqualTo(response.getStatus());
		assertThat(outputJson).isEqualTo(response.getContentAsString());
	}
	
	@Test
	public void exitGameNotFoundTest() throws Exception {
		String URI = "/game/exit";
		String inputJson = "{\"userName\": \"testEachNot\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.NOT_FOUND.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void exitGameBadRequestTest() throws Exception {
		String URI = "/game/exit";
		String inputJson = "{\"userName\": \"\"}";
		
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
