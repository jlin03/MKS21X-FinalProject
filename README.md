Instructions:





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
  
