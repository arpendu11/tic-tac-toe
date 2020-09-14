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

import com.craft.tictactoe.constant.PlayerStatus;
import com.craft.tictactoe.converter.MoveValidResourceConverter;
import com.craft.tictactoe.converter.WhoseTurnResourceConverter;
import com.craft.tictactoe.dto.MoveDTO;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.resources.MoveValidResource;
import com.craft.tictactoe.resources.WhoseTurnResource;
import com.craft.tictactoe.service.PlayerService;

@RestController
@RequestMapping(path = "/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
	
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
	public ResponseEntity<WhoseTurnResource> getNextTurn(@RequestParam(required = true) String userName) {
		if (checkPlayer(userName)) {
			Player player = playerService.getPlayer(userName);
			return new ResponseEntity<WhoseTurnResource>(whoseTurnResourceConverter.convert(player), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MoveValidResource> playNextMove(@RequestBody @Valid MoveDTO move) {
		// Fetch the gaming session where player is playing
		// Structure the board to printable format
		// Play the next move and make Computer play its move
		// Determine if the player had a winning move or not
		if (checkPlayer(move.getUserName())) {
			if (playerService.getPlayer(move.getUserName()).getStatus().equals(PlayerStatus.PLAYING)) {
				Player player = playerService.playNextMove(playerService.getPlayer(move.getUserName()), move.getRow(), move.getColumn());
				return new ResponseEntity<MoveValidResource>(moveValidResourceConverter.convert(player), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
