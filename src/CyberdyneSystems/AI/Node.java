
        package CyberdyneSystems.AI;

        import CyberdyneSystems.generic.Agent;
        import CyberdyneSystems.generic.Board;
        import aiproj.slider.Move;

/** COMP30024 Artificial Intelligence
 SearchNode class
 George Juliff - 624946
 Thomas Miles - 626263

 Search node class for alpha-beta minmax with iterative deepening
 */
public class Node {

    private int numChilds=0, numLegalMoves;
    private byte p_1, p_2;    // p_1 is the piece index, p_2 is how many directions for that index piece have been explored
    public Move firstMove; // the move made to get to this node

    public Agent playerState; // the state of the player that is making the next move, switches between the AI agent and
    public Agent otherAgent;  // could be either the AI or opponent depending on min/max layer

    public Node(Agent agent, Move first) {
        p_1 = 0;
        p_2 = 0;
        playerState = agent;
        otherAgent = new Agent();
        firstMove = first;

        if (agent.player == Board.V) otherAgent.player = Board.H;
        else otherAgent.player = Board.V;

        otherAgent.giveBoard(agent.board.layout);

        otherAgent.givePieces();
        numLegalMoves = calculateNumMoves();
    }

    public int calculateNumMoves() {
        int numValid = 0;
        // Check all pieces possible move directions and sum valid
        for (int i=0; i < playerState.numPieces(); i++) {
            // both players can move right and up
            if (playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i] + 1, playerState.getPiece(i)[Agent.j],
                    playerState.player)) {
                numValid ++;
            }
            if (playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i], playerState.getPiece(i)[Agent.j] + 1,
                    playerState.player)) {
                numValid ++;
            }
            // only H can move down
            if (playerState.player == Board.H  && playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i],
                    playerState.getPiece(i)[Agent.j] - 1, playerState.player)) {
                numValid ++;

                // similarly, only V can move left
            } else if (playerState.player == Board.V   &&
                    playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i] - 1,
                            playerState.getPiece(i)[Agent.j], playerState.player)) {
                numValid ++;
            }
        }
        return numValid;
    }

    public boolean moreChilds() {
        return numChilds < numLegalMoves;
    }

    /**
     * Find next state node from the this nodes state
     * @return Node for a possible future state, this nodes playerState will be the opponent of the node that is finding
     * the next child since it will be opponents turn
     */
    public Node nextChild(Move first) {

        final byte U = 0, R = 1, L = 2, D = 3; // direction values for finding next nodes

        // return null if all children have been explored
        if (numChilds >= numLegalMoves) {
            return null;
        }
        // initialise the agent for the next node, once we find a move we will update it's layout and create the node
        Agent newAgent = new Agent();
        newAgent.enemy = new Agent();

        // swap the playerStates player value for the next node
        if (playerState.player == Board.V) {
            newAgent.player = Board.H;
            newAgent.enemy.player = Board.V;
        } else {
            newAgent.player = Board.V;
            newAgent.enemy.player = Board.H;
        }
        newAgent.enemy.giveBoard(playerState.board.layout);
        newAgent.enemy.givePieces();

        // using p_1 and p_2 we can find the next legal move
        while (true) {
            // first check UP
            if (p_2 == U) {
                if (playerState.board.canMoveTo (playerState.getPiece(p_1)[Agent.i],
                                                playerState.getPiece(p_1)[Agent.j] + 1, playerState.player)) {

                    // if up is a legal move, make the move using the new enemy agent (since each child swaps enemy
                    // and player state, the child's enemy agent is the one making the move from this state
                    newAgent.enemy.makeMove(Move.Direction.UP, p_1);

                    // increment the direction value for next time and return the new child node
                    numChilds++;
                    p_2++;

                    // give the enemy agent to the next node the new state's layout and pieces
                    newAgent.giveBoard(newAgent.enemy.board.layout);
                    newAgent.givePieces();

                    // this is the child of the root node, set firstMove for this branch
                    if (first == null){
                        return new Node (newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                playerState.getPiece(p_1)[Agent.j], Move.Direction.UP));

                    // otherwise just pass firstMove down
                    } else {
                        return new Node (newAgent, firstMove);
                    }

                    // if not an allowed move just increment p_2 and continue
                } else {
                    p_2++;
                }
            }
            // check RIGHT, logic similar to before, different direction
            if (p_2 == R) {

                if (playerState.board.canMoveTo((int)playerState.getPiece(p_1)[Agent.i]+1,
                        playerState.getPiece(p_1)[Agent.j], playerState.player)) {

                    newAgent.enemy.makeMove(Move.Direction.RIGHT, p_1);
                    newAgent.enemy.givePieces();

                    // increment the direction value for next time and return
                    p_2++;
                    numChilds++;

                    // give the enemy agent to the next node the new state's layout and pieces
                    newAgent.giveBoard(newAgent.enemy.board.layout);
                    newAgent.givePieces();

                    // this is the child of the root node, set firstMove for this branch
                    if (first == null){
                        return new Node (newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                playerState.getPiece(p_1)[Agent.j], Move.Direction.RIGHT));

                    // otherwise just pass firstMove down
                    } else {
                        return new Node (newAgent, firstMove);
                    }
                // if this is illegal increment the move indexer p_2
                } else {
                    p_2++;
                }

            }
            // check LEFT, if the player is H, just increment p_2 so it tries down in stead
            if (p_2 == L) {

                if (playerState.player == Board.H) {
                    p_2++;
                } else {
                    if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i] - 1,
                            playerState.getPiece(p_1)[Agent.j], playerState.player)) {


                        newAgent.enemy.makeMove(Move.Direction.LEFT, p_1);

                        // this is the last move for given piece at p_1, increment p_1 and numChilds, reset p_2 and return
                        p_2 = 0;
                        p_1++;
                        numChilds++;
                        // give the enemy agent to the next node the new state's layout and pieces
                        newAgent.giveBoard(newAgent.enemy.board.layout);
                        newAgent.givePieces();

                        // this is the child of the root node, set firstMove for this branch
                        if (first == null){
                            return new Node (newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                    playerState.getPiece(p_1)[Agent.j], Move.Direction.LEFT));

                        // otherwise just pass firstMove down
                        } else {
                            return new Node (newAgent, firstMove);
                        }

                    // if left is not allowed, and player is V, reset p_2 and increment the index
                    } else {
                        p_1++;
                        p_2 = 0;
                    }
                }
            }
            // check DOWN, if player is V, increment p_1 and reset p_2 and continue to next loop step
            if (p_2 == D) {

                if (playerState.player == Board.V) {
                    p_1++;
                    p_2 = 0;
                } else {
                    if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i],
                            playerState.getPiece(p_1)[Agent.j] - 1, playerState.player)) {

                        newAgent.enemy.makeMove(Move.Direction.DOWN, p_1);


                        // this is the last move for given piece at p_1, increment p_1 and numChilds, reset p_2
                        p_2 = 0;
                        p_1++;
                        numChilds++;

                        // give the enemy agent to the next node the new state's layout and pieces
                        newAgent.giveBoard(newAgent.enemy.board.layout);
                        newAgent.givePieces();

                        // this is the child of the root node, set firstMove for this branch
                        if (firstMove == null){
                            return new Node (newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                    playerState.getPiece(p_1)[Agent.j], Move.Direction.DOWN));

                        // otherwise just pass firstMove down
                        } else {
                            return new Node (newAgent, firstMove);
                        }

                    } else {
                        // Otherwise increment p_1, reset p_2 and continue
                        p_1++;
                        p_2 = 0;
                    }
                }
            }
            // if we get here before returning, the loop will continue until a child is found
        }
    }

}