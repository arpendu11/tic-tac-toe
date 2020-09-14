package com.craft.tictactoe.controller;

import javax.validation.Valid;

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
		if (!playerController.checkPlayer(dto.getUserName())) {
			Player newPlayer = playerController.createNewPlayer(dto.getUserName());
			Game newGame = gameService.createNewGame(newPlayer, dto.getType());
			if (newGame != null) {
				return new ResponseEntity<>(gameResourceConverter.convert(newGame), HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@PostMapping(path = "/setBoard", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameBoardResource> setGamingBoard(@RequestBody @Valid SetBoardDTO board) {
		if (playerController.checkPlayer(board.getUserName())) {
			Game game = gameService.setGameProperties(board);
			return new ResponseEntity<GameBoardResource>(gameBoardResourceConverter.convert(game), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/status")
	public ResponseEntity<GameStatusResource> getCurrentStatus(@RequestParam(required = true) String userName) {
		if (playerController.checkPlayer(userName)) {
			Game game = gameService.getGameByPlayer(userName);
			return new ResponseEntity<GameStatusResource>(gameStatusResourceConverter.convert(game), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path = "/exit", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameResource> exitGame(@RequestBody @Valid PlayerDTO playerDTO) throws PlayerNotFoundException {
		if (playerController.checkPlayer(playerDTO.getUserName())) {
			Game game = gameService.endGame(playerDTO.getUserName());
			return new ResponseEntity<GameResource>(gameResourceConverter.convert(game), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
