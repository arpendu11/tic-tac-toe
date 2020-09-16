package com.craft.tictactoe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craft.tictactoe.constant.GameStatus;
import com.craft.tictactoe.constant.GameType;
import com.craft.tictactoe.constant.MarkerType;
import com.craft.tictactoe.constant.PlayerStatus;
import com.craft.tictactoe.entity.Game;
import com.craft.tictactoe.entity.Player;
import com.craft.tictactoe.exceptions.UnsupportedCoordinateException;
import com.craft.tictactoe.exceptions.UnsupportedMoveException;

@Service
public class GameLogicService {

	private final Logger logger = LoggerFactory.getLogger(GameLogicService.class);

	@Autowired
	private GameService gameService;

	public String[][] createBoard(int size) {
		String[][] board = new String[size][size];
		int x = 1;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = "" + "[" + String.valueOf(x) + "]";
				x++;
			}
		}
		return board;
	}

	public void playNextMove(Player player, int row, int column) {
		Game game = gameService.getGameByPlayer(player.getUserName());
		int turns = game.getNoOfTurns();
		
		if (turns >= game.getSize()*game.getSize()-1) {
			game.setStatus(GameStatus.MATCH_TIE);
			game.getPlayers().get(0).setStatus(PlayerStatus.TIE);
			game.getPlayers().get(1).setStatus(PlayerStatus.TIE);
		} else {
			if (row > 0 && row <= game.getSize() && column > 0 && column <= game.getSize()) {
				String[][] board = game.getBoard();
				if(!(board[row - 1][column - 1].equals(MarkerType.CROSS.getShape())) && !(board[row - 1][column - 1]).equals(MarkerType.CIRCLE.getShape())) {
					board[row - 1][column - 1] = player.getMarker().getShape();
					game.setNoOfTurns(turns+1);
	            } else {
	    			logger.error("Already a marker has occupied the space! Please guess again !");
	    			throw new UnsupportedMoveException();            	
	            }
				if (hasWon(board)) {
					game.setStatus(GameStatus.FIRST_PLAYER_WON);
					if (game.getType() == GameType.COMPUTER) {
						game.getPlayers().get(0).setStatus(PlayerStatus.WINNER);
						game.getPlayers().get(1).setStatus(PlayerStatus.LOSER);
					} else {
						for (Player p : game.getPlayers()) {
							if (p.getUserName().equals(player.getUserName())) {
								p.setStatus(PlayerStatus.WINNER);
							} else {
								p.setStatus(PlayerStatus.LOSER);
							}
						}
					}
				} else {
					if (game.getType() == GameType.COMPUTER) {
						String[][] boardComputer = blockMove(game.getBoard(), game.getPlayers().get(0).getMarker(), game.getPlayers().get(1).getMarker());
						game.setBoard(boardComputer);
						game.setNoOfTurns(turns+2);
						if (hasWon(boardComputer)) {
							game.setStatus(GameStatus.COMPUTER_WON);
							game.getPlayers().get(0).setStatus(PlayerStatus.LOSER);
						} else {
							game.setStatus(GameStatus.IN_PROGRESS);
						}
					} else {
						game.switchTurn();
					}
				}
			} else {
				logger.error("Either row or column value is not correct. They should be greater than 0 and less than the board size.");
				throw new UnsupportedCoordinateException();
			}
		}
	}

	public boolean hasWon(String[][] board) {
		int counter = 1;
		int N = board.length;
        //horizontal check
        for(int i = 0; i<N; i++)
        {
            for(int j=0;j<N-1;j++)
            {
                if(board[i][j].equals(board[i][j+1]))
                {
                    counter = counter+1;
                }
                if(counter == N)
                {
                    return true;
                }
            }
            counter = 1;
        }
        
        counter = 1;
        //vertical
        for(int i = 0; i<N; i++)
        {
            for(int j=0;j<N-1;j++)
            {
                if(board [j][i].equals(board[j+1][i]))
                {
                    counter = counter+1;
                }
                if(counter == N)
                {
                    return true;
                }
            }
            counter = 1;
        }
        
        counter = 1;
        //diagonal from left-top to right-bottom
        for(int i=0;i<N-1;i++)
        {
            if(board[i][i].equals(board[i+1][i+1]))
            {
                counter = counter+1;
            }
            if (counter==N)
            {
                return true;
            }
            //counter = 1;
        }
        
        counter = 1;
        //diagonal from right-top to left-bottom
        for(int i=0;i<N-1;i++)
        {
            if(board[i][N-1-i].equals(board[i+1][N-1-(i+1)]))
            {
                counter = counter+1;
            }
            if (counter==N)
            {
                return true;
            }
            //counter = 1;
        }
		return false;
	}

	public String[][] blockMove(String[][] board, MarkerType humanMarker, MarkerType computerMarker) {
		boolean moveTaken = false;
		int counter = 1;
		int bs = board.length;
		
		// BLOCKS!!!! //
        counter = 1;
        // check if you can block a win horizontally
        for(int a = 0; a<bs; a++)
        {
            for(int j=0; j<bs-2; j++)
            {
                if((board[a][j].equals(board[a][j+1]) && board[a][j].equals(humanMarker.getShape()))
                        ||(board[a][j].equals(board[a][j+2]) && board[a][j].equals(humanMarker.getShape()))
                        || (board[a][j+1].equals(board[a][j+2]) && board[a][j+1].equals(humanMarker.getShape())))
                {
                    counter = counter+1;
                }
                if(counter == bs-1)
                {
                    for(int k=0;k<bs;k++)
                    {
                        if(!board[a][k].equals(humanMarker.getShape()) && !board[a][k].equals(computerMarker.getShape()))
                        {
                            logger.info("Computer placing the horizontal blocking marker at: "+(a+1)+", "+(k+1));
                            board[a][k] = computerMarker.getShape();
                            moveTaken = true;
                            return board;
                        }
                    }
                }
            }
            counter = 1;
        }
        
        counter = 1;
        // check if you can block a win vertically
        for(int i = 0; i<bs; i++)
        {
            for(int j=0; j<bs-2; j++)
            {
                if(board[j][i].equals(board[j+1][i]) && board[j][i].equals(humanMarker.getShape())
                        || (board[j][i].equals(board[j+2][i]) && board[j][i].equals(humanMarker.getShape()))
                        || (board[j+1][i].equals(board[j+2][i]) && board[j+1][i].equals(humanMarker.getShape())))
                {
                    counter = counter+1;
                }
                if(counter == bs-1)
                {
                    for(int k=0; k<bs; k++)
                    {
                        if(!board[i][k].equals(humanMarker.getShape()) && !board[i][k].equals(computerMarker.getShape()))
                        {
                            logger.info("Computer placing the vertical blocking marker at: "+(k+1)+", "+(i+1));
                            board[k][i] = computerMarker.getShape();
                            moveTaken = true;
                            return board;
                        }
                    }
                }
                //}
            }
            counter = 1;
        }
        
        
        // check if you can block a win diagonally left-top to right-bottom
        counter = 1;
        for(int j=1;j<bs;j++)
        {
            int i=0;
            if(board[i][i].equals(board[j][j]) && board[j][j].equals(humanMarker.getShape()))
            {
                counter = counter+1;
            }
            if(counter == bs-1)
            {
                for(int k=0;k<bs;k++)
                {
                    if(!board[k][k].equals(humanMarker.getShape()) && !board[k][k].equals(computerMarker.getShape()))
                    {
                        logger.info("Computer placing the diagonal LT-RB blocking marker at: "+(k+1)+", "+(k+1));
                        board[k][k] = computerMarker.getShape();
                        moveTaken = true;
                        return board;
                    }
                }
            }
        }
        
        //Check if you can block a win diagonally from right-top to left-bottom
        counter = 1;
        for(int i=0;i<bs-1;i++)
        {
            if(board[i][bs-1-i].equals(board[i+1][bs-1-i-1]) && board[i][bs-1-i].equals(humanMarker.getShape()))
            {
                counter = counter+1;
            }
            if(counter == bs-1)
            {
                for(int k=0;k<bs;k++)
                {
                    if(!board[k][bs-1-k].equals(humanMarker.getShape()) && !board[k][bs-1-k].equals(computerMarker.getShape()))
                    {
                        logger.info("Computer placing the diagonal RT-LB blocking marker at: "+(k+1)+", "+(bs-1-k+1));
                        board[k][bs-1-k] = computerMarker.getShape();
                        moveTaken = true;
                        return board;
                    }
                }
            }
        }
        
        // check if you can take a win vertically
        for(int a = 0; a<bs; a++)
        {
            for(int j=0; j<bs-2; j++)
            {
                if((board[j][a].equals(board[j+1][a]) && board[j][a].equals(computerMarker.getShape()))
                        || (board[j][a].equals(board[j+2][a]) && board[j][a].equals(computerMarker.getShape()))
                        || (board[j+1][a].equals(board[j+2][a]) && board[j+1][a].equals(computerMarker.getShape())))
                {
                    counter = counter+1;
                }
                if(counter == bs-1)
                {
                    for(int k=0;k<bs;k++)
                    {
                        if(!board[k][a].equals(humanMarker.getShape()) && !board[k][a].equals(computerMarker.getShape()))
                        {
                        	logger.info("Computer placing the vertical winning marker at: "+(k+1)+", "+(a+1));
                            board[k][a] = computerMarker.getShape();
                            moveTaken = true;
                            return board;
                        }
                    }
                }
            }
            counter = 1;
        }
        
        counter = 1;
        // check if you can take a win horizontally
        for(int i = 0; i<bs; i++)
        {
            for(int j=0; j<bs-2; j++)
            {
                if((board[i][j].equals(board[i][j+1]) && board[i][j].equals(computerMarker.getShape()))
                        ||(board[i][j].equals(board[i][j+2]) && board[i][j].equals(computerMarker.getShape()))
                        || (board[i][j+1].equals(board[i][j+2]) && board[i][j+1].equals(computerMarker.getShape())))
                {
                    counter = counter+1;
                }
                if(counter == bs-1)
                {
                    for(int k=0; k<bs; k++)
                    {
                        if(!board[i][k].equals(humanMarker.getShape()) && !board[i][k].equals(computerMarker.getShape()))
                        {
                        	logger.info("Computer placing the horizontal winning marker at: "+(i+1)+", "+(k+1));
                        	board[i][k] = computerMarker.getShape();
                            moveTaken = true;
                            return board;
                        }
                    }
                }
                //}
            }
            counter = 1;
        }
        
        counter = 1;
        // check if you can take a win diagonally left-top to right-bottom
        for(int i=0;i<bs;i++)
        {
            for(int j=0;j<bs;j++)
            {
                if(i!=j)
                {
                    if(board[i][i].equals(board[j][j]) && board[j][j].equals(computerMarker.getShape()))
                    {
                        counter = counter+1;
                    }
                    if(counter == bs-1)
                    {
                        for(int k=0;k<bs;k++)
                        {
                            if(!board[k][k].equals(humanMarker.getShape()) && !board[k][k].equals(computerMarker.getShape()))
                            {
                            	logger.info("Computer placing the diagonal LT-RB winning marker at: "+(k+1)+", "+(k+1));
                            	board[k][k] = computerMarker.getShape();
                                moveTaken = true;
                                return board;
                            }
                        }
                    }
                }
            }
        }
        counter = 1;
        // check if you can take a win diagonally right-bottom to left-top
        for(int j=bs-2;j>=0;j--)
            {
                int i=bs-1;
                if(board[i][i].equals(board[j][j]) && board[j][j].equals(computerMarker.getShape()))
                {
                    counter = counter+1;
                }
                if(counter == bs-1)
                {
                    for(int k=0;k<bs;k++)
                    {
                        if(!board[k][k].equals(humanMarker.getShape()) && !board[k][k].equals(computerMarker.getShape()))
                        {
                        	logger.info("Computer placing the diagonal winnning marker at: "+(k+1)+", "+(k+1));
                        	board[k][k] = computerMarker.getShape();
                            moveTaken = true;
                            return board;
                        }
                    }
                }
            }
        
        counter = 1;
        //Check if you can win diagonally from right-top to left-bottom
        for(int i=0;i<bs-2;i++)
        {
            if(board[i][bs-1-i].equals(board[i+1][bs-1-i-1]) && board[i][bs-1-i].equals(computerMarker.getShape()))
            {
                counter = counter+1;
            }
            if(counter == bs-1)
            {
                for(int k=0;k<bs-1;k++)
                {
                    if(!board[k][bs-1-k].equals(humanMarker.getShape()) && !board[k][bs-1-k].equals(computerMarker.getShape()))
                    {
                    	logger.info("Computer placing the diagonal RT-LB winning marker at: "+(k+1)+", "+(bs-1-k+1));
                    	board[k][bs-1-k] = computerMarker.getShape();
                        moveTaken = true;
                        return board;
                    }
                }
            }
        }
        
        int randRow = 0;
        int randCol = 0;
        
        while (!moveTaken) {
        	randRow = (int) (Math.random() * bs);
        	randCol = (int) (Math.random() * bs);
        	
        	if (!board[randRow][randCol].equals(humanMarker.getShape()) && !board[randRow][randCol].equals(computerMarker.getShape())) {
        		logger.info("Computer placing random marker at: " + (randRow+1) + ", " +(randCol+1));
        		board[randRow][randCol] = computerMarker.getShape();
        		moveTaken = true;
        	}
        }
        
        return board;
	}

}
