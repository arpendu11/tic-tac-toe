package com.craft.tictactoe.repository;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerPoolRepository {
	private final Logger LOGGER = LoggerFactory.getLogger(PlayerPoolRepository.class);
	private static PlayerPoolRepository instance;
	private static Set<String> session;
	
	public PlayerPoolRepository() {
		cleanRepo();
		session =  new HashSet<>();
	}	
	
	public static PlayerPoolRepository getInstance(){
		if(instance == null){
			instance = new PlayerPoolRepository();
		}		
		return instance;
	}
	
	public boolean checkPlayer(String username) {
		return session.contains(username);
	}
	
	public void addPlayer(String username) {
		session.add(username);
		LOGGER.info("New Player added to the system: " + username);
	}

	public void removePlayer(String userName) {
		session.remove(userName);
	}

	public void cleanRepo() {
		session = null;
	}
}
