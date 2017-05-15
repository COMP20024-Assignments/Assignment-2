# Assignment-2
Winning the game
This branch has not been fully bug tested but has no IDE errors.

Board and pieces are stored as 'byte' to save memory. Changed the package structure a bit as follows:

src:  .agents.Human : Human actor class
             .T800  : AI agent, currently untrained, uses a-b minimax with iterative deepening
             
      .generic.Agent: superclass for any game playing agent
              .Board: handles board more neatly
              
      .AI    .Node  : used for searching future game states
             .Skynet: heuristic class for evaluating desirability of a board state
         /config.xml: configuration file for the AI
             
      .training.logs: logs from training runs
        .Training.py: python application to train the AI, modifies config values and evaluates the effects
        
      .aiproj.slider: source drivers + Move helper class
      
**Will be adding a machine learning library outside of src that modifies a config.txt file to be read by T800**
             
