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

import com.craft.tictactoe.constant.PlayerStatus;
import com.craft.tictactoe.converter.MoveValidResourceConverter;
import com.craft.tictactoe.converter.WhoseTurnResourceConverter;
import com.craft.tictactoe.dto.MoveDTO;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.resources.MoveValidResource;
import com.craft.tictactoe.resources.WhoseTurnResource;
import com.craft.tictactoe.service.PlayerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
	
	private final Logger logger = LoggerFactory.getLogger(PlayerController.class);
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private WhoseTurnResourceConverter whoseTurnResourceConverter;
	
	@Autowired
	private MoveValidResourceConverter moveValidResourceConverter;
	
	public Player createNewPlayer(String userName) {
		return playerService.createNewPlayer(userName);
	}
	
	public boolean checkPlayer(String username) {
		return playerService.checkPlayerPlaying(username);
	}
	
	@GetMapping("/turn")
	@ApiOperation(value = "Get the turn of the game which the user is playing.",
				notes = "Provide userName as queryParam to see whose turn is it to play this game.",
				response = WhoseTurnResource.class)
	public ResponseEntity<WhoseTurnResource> getNextTurn(@ApiParam(value = "userName of the player who is playing the game")
													@RequestParam(required = true) @Valid String userName) {
		if (checkPlayer(userName)) {
			Player player = playerService.getPlayer(userName);
			return new ResponseEntity<WhoseTurnResource>(whoseTurnResourceConverter.convert(player), HttpStatus.OK);
		}
		logger.error("Player " + userName + " was not found playing any game");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Play the next move in the game which the user is playing",
				notes = "Provide userName, row as row index and column as column index of the board matrix to playthe next move in this game.",
				response = MoveValidResource.class)
	public ResponseEntity<MoveValidResource> playNextMove(@ApiParam(name= "MoveDTO", value = "payload object to play the next move")
												@RequestBody @Valid MoveDTO move) {
		if (move.getUserName() != null && move.getUserName().length() > 0 && move.getRow() > 0 && move.getColumn() > 0) {
			if (checkPlayer(move.getUserName())) {
				if (playerService.getPlayer(move.getUserName()).getStatus().equals(PlayerStatus.PLAYING)) {
					Player player = playerService.playNextMove(playerService.getPlayer(move.getUserName()), move.getRow(), move.getColumn());
					return new ResponseEntity<MoveValidResource>(moveValidResourceConverter.convert(player), HttpStatus.OK);
				}
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			logger.error("Player " + move.getUserName() + " was not found playing any game");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		logger.error("Username cannot be null or empty");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
