Instructions:

	To compile: javac Driver.java
	
This is a training/playing tool that trains the computer to play tic-tac-toe by learning from its failures and successes. After much training, the computer becomes a perfect player.
	
	To run: java Driver boardSize mode [p1states p1weights p2states p2weights]
	
		(boardSize) is the dimension of the sides of the tic-tac-toe board. (must be greater than 1)
		(mode) is a string. "train" will put the program into training mode. "play" will allow the user to play against the computer.
		(p1states) is a txt file that stores all the game states that player1 has in memory.
		(p1weights) is a txt file that stores all the weights correlated with the game states that player1 has in memory.
		(p2states) and (p2weights) are the same, but for player2.
		If the states/weights are not specified, the default files are read.
		
	Training mode:
	
		When the user enters training mode, they will be presented with three prompts:
		
			1"Should the bots make random moves?[y/n]"		where the expected input is "y" or "n"
			2"How many games should be played?"				where the expected input is an integer
			3"Should the games be printed?[y/n]"			where the expected input is "y" or "n"
			
			If the response to prompt 1 is "y", the computer will make random "exploratory" moves 20% of the time and the "optimal" move 80% of the time. If the response is "n", it will only make the optimal move.
			*the computer will not learn if it does not sometimes make random moves*
			
			The response to prompt 2 determines how many games will be played in the training session.
			
			If the response to prompt 3 is "y", the state of the board and the weights of the player making the move will be printed for every move of every game played in the training session.
	
			
			After training, a summary will appear showing the amount of games each player won, and the amount of draws.
			
	Play mode:
	
		When the user enters play mode, they will be presented with three prompts at varying stages of the game:
		
			1"Start as player 1 or 2?:"		where the expected input is "1" or "2"
			2"Make your move:"				where the expected input is 2 integers between 0 inclusive and boardSize non-inclusive, separated by a comma
			3"Play again?[y/n]"				where the expected input is "y" or "n"
			
			Prompt 1 will appear before the game. If the response to prompt 1 is "1", the user will start a game vs. the computer as player 1(X). If it is "2", then the user will start as player 2.
			
			Prompt 2 will appear during the game.If the response to prompt 2 does not follow the expected format for the input, then the program will terminate. If the move is invalid, the user will be prompted to make a different move.
			
			Prompt 3 will appear after the game. If the response to prompt 3 is "y", another game will be played. If "n", then the program will terminate.
			
   Data Collection:
	
		The win/draw rates of each player under their "optimal" policies were recorded in intervals of 2,500 games.
		On a 3x3 Board:
			
			After 2,500 games:
			P1 winrate - 52%
			P2 winrate - 27%
			Draw rate - 21%
			
			After 5,000 games:
			P1 winrate - 44%
			P2 winrate - 33%
			Draw rate - 23%
			
			After 7,500 games:
			P1 winrate - 40%
			P2 winrate - 12%
			Draw rate - 48%
			
			After 10,000 games:
			P1 winrate - 21%
			P2 winrate - 14%
			Draw rate - 65%
			
			After 12,500 games:
			P1 winrate - 26%
			P2 winrate - 12%
			Draw rate - 62%
			
			After 15,000 games:
			P1 winrate - 16%
			P2 winrate - 13%
			Draw rate - 71%
			
			After 17,500 games:
			P1 winrate - 1%
			P2 winrate - 2%
			Draw rate - 97%
			
			After 20,000 games:
			P1 winrate - 1%
			P2 winrate - 1%
			Draw rate - 98%
			
			
Development:
1-3-19:
  Initialized repo
  
1-4-19:
  Constructor for board written
  Ability to make a move on the board coded
  Win conditions still in progress
  Nothing tested yet
  
1-5-19:
  Win conditions fully coded
  toString written
  Reward values implemented for every state of a game
  Small changes to move() method made to make future code easier to implement
  New constructor added to be able to load a board from a specified state
  Game messages made to keep track of the match
  Board class completely coded, may be edited in the future
  
1-6-19:
  AI class completely coded(for now):
    Constructor for AI with no training weights
    Constructor for AI to import training weights from file
    Ability to import and export AI data through txt files
    Ability to get/change weights in relation to the index of a state(to be used in decision tree training later on)
    
1-7-19:
  Some methods made public for better access(Board,AI)
  New methods getNthMaxWeight*AI* and isMoveValid*Board* written along with several helper methods
  All methods for weights changed from int to double comparison and operation
  Learn method in Environment class completed
  Tested and saved training for bots, algorithm may be changed later
  
1-8-19:
  play method to start a player vs computer match written
  learning algorithm changed so weights update every two moves, due to the two player nature of tic-tac-toe
  AI is almost unbeatable on 3x3 board
  
1-9-19:
  Driver is complete, instructions/documentation coming soon
  summary is now included after training sessions
  
1-16-19:
  Instructions documented
  Data collected for 3x3 board
  
