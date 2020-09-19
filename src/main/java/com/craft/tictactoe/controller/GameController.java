package com.craft.tictactoe.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.craft.tictactoe.converter.GameBoardResourceConverter;
import com.craft.tictactoe.converter.GameResourceConverter;
import com.craft.tictactoe.converter.GameStatusResourceConverter;
import com.craft.tictactoe.dto.NewGameDTO;
import com.craft.tictactoe.dto.PlayerDTO;
import com.craft.tictactoe.dto.SetBoardDTO;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.exceptions.PlayerNotFoundException;
import com.craft.tictactoe.resources.GameBoardResource;
import com.craft.tictactoe.resources.GameResource;
import com.craft.tictactoe.resources.GameStatusResource;
import com.craft.tictactoe.service.GameService;

@RestController
@RequestMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {
	
	private final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private PlayerController playerController;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameResourceConverter gameResourceConverter;
	
	@Autowired
	private GameBoardResourceConverter gameBoardResourceConverter;
	
	@Autowired
	private GameStatusResourceConverter gameStatusResourceConverter;
	
	@PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameResource> createNewGame(@RequestBody @Valid NewGameDTO dto) {
		if (dto.getUserName() != null && dto.getUserName().length() > 0) {
			if (!playerController.checkPlayer(dto.getUserName())) {
				Player newPlayer = playerController.createNewPlayer(dto.getUserName());
				Game newGame = gameService.createNewGame(newPlayer, dto.getType());
				if (newGame != null) {
					return new ResponseEntity<>(gameResourceConverter.convert(newGame), HttpStatus.CREATED);
				}
			}
			logger.error("Player " + dto.getUserName() + " is already playing a game. Please exit to start a new game.");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		logger.error("Username cannot be null or empty");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path = "/setBoard", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBoardResource> setGamingBoard(@RequestBody @Valid SetBoardDTO board) {
		if (board.getUserName() != null && board.getUserName().length() > 0) {
			if (playerController.checkPlayer(board.getUserName())) {
				Game game = gameService.setGameProperties(board.getUserName(), board.getSize(), board.getMarker());
				return new ResponseEntity<GameBoardResource>(gameBoardResourceConverter.convert(game), HttpStatus.OK);
			} else {
				logger.error("Player " + board.getUserName() + " was not found playing any game");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		logger.error("Username cannot be null or empty");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/status")
	public ResponseEntity<GameStatusResource> getCurrentStatus(@RequestParam(required = true) @Valid String userName) {
		if (playerController.checkPlayer(userName)) {
			Game game = gameService.getGameByPlayer(userName);
			return new ResponseEntity<GameStatusResource>(gameStatusResourceConverter.convert(game), HttpStatus.OK);
		} else {
			logger.error("Player " + userName + " was not found playing any game");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path = "/exit", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameResource> exitGame(@RequestBody @Valid PlayerDTO playerDTO) throws PlayerNotFoundException {
		if (playerDTO.getUserName() != null && playerDTO.getUserName().length() > 0) {
			if (playerController.checkPlayer(playerDTO.getUserName())) {
				Game game = gameService.endGame(playerDTO.getUserName());
				return new ResponseEntity<GameResource>(gameResourceConverter.convert(game), HttpStatus.OK);
			} else {
				logger.error("Player " + playerDTO.getUserName() + " was not found playing any game");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		logger.error("Username cannot be null or empty");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
