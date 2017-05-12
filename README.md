# Assignment-2
Winning the game
This branch has not been bug tested but has no IDE errors.

Board and pieces are stored as 'byte' to save memory. Changed the package structure a bit as follows:

src:  .agents.Human : Human actor class
             .Node  : used for searching future game states
             .Skynet: heuristic class for evaluating desirability of a board state
             .T800  : AI agent, currently untrained, uses a-b minimax with iterative deepening
      .generic.Agent: superclass for any game playing agent
              .Board: handles board more neatly
              .Piece: redundant
      .aiproj.slider: source drivers + Move helper class
      
**Will be adding a machine learning library outside of src that modifies a config.txt file to be read by T800**
             
