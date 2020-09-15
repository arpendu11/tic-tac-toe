package com.craft.tictactoe.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONObject;
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

import com.craft.tictactoe.constant.MarkerType;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GameControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {}
	
	@Test
	public void createNewGameTest() throws Exception {
		String URI = "/game/new";
		JSONObject obj = new JSONObject();
		obj.put("userName", "test1");
		obj.put("type", "COMPUTER");
		String inputJson = this.mapToJson(obj);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.CREATED.value()).isEqualTo(response.getStatus());
	}
	
	@Test
	public void setGamingBoardTest() throws Exception {
		String URI = "/game/setBoard";
		JSONObject obj = new JSONObject();
		obj.put("userName", "test");
		obj.put("size", 3);
		obj.put("marker", MarkerType.CROSS.toString());
		String inputJson = this.mapToJson(obj);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(HttpStatus.NOT_FOUND.value()).isEqualTo(response.getStatus());
	}
		
	@AfterEach
	void cleanUp() {}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		return objectMapper.writeValueAsString(object);
	}
}
