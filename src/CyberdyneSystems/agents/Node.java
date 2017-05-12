
        package CyberdyneSystems.agents;

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
    public Move moveToArrive; // the move made to get to this node

    public Agent playerState; // the state of the player that is making the next move, switches between the AI agent and
    public Agent otherAgent;  // could be either the AI or opponent depending on min/max layer

    public Node(Agent playerState) {
        p_1 = 0;
        p_2 = 0;
        this.playerState = playerState;

        otherAgent = new Agent();
        if (playerState.player == Board.V) otherAgent.player = Board.H;
        else otherAgent.player = Board.V;

        otherAgent.giveBoard(playerState.board.board, playerState.board.getSize());
        otherAgent.givePieces();

        numLegalMoves = calculateNumMoves();
    }

    public int calculateNumMoves() {
        int numValid = 0;

        // Check all pieces possible move directions and sum valid
        for (int i=0; i < playerState.numPieces(); i++) {

            // both players can move right and up
            if (playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i] + 1, playerState.getPiece(i)[Agent.j])){
                numValid ++;
            }
            if (playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i], playerState.getPiece(i)[Agent.j] + 1)){
                numValid ++;
            }
            // only H can move down
            if (playerState.player == Board.H   &&
                    playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i],playerState.getPiece(i)[Agent.j] - 1)) {
                numValid ++;

                // similarly, only V can move left
            } else if (playerState.player == Board.V   &&
                    playerState.board.canMoveTo(playerState.getPiece(i)[Agent.i] - 1,playerState.getPiece(i)[Agent.j])) {
                numValid ++;
            }
        }
        return numValid;
    }

    /**
     * Find next state node from the this nodes state
     * @return Node for a possible future state, this nodes playerState will be the opponent of the node that is finding
     * the next child since it will be opponents turn
     */
    public Node nextChild() {

        final byte U = 0, R = 1, L = 2, D = 3; // direction values for finding next nodes
        Node child;

        // return null if all children have been explored
        if (numChilds == numLegalMoves) {
            return null;
        }
        // initialise the agent for the next node, once we find a move we will update it's board and create the node
        Agent newAgent = new Agent();
        newAgent.giveBoard(playerState.board.board, playerState.board.getSize());
        // swap the playerStates player value for the next node
        if (playerState.player == Board.V) {
            newAgent.player = Board.H;
        } else {
            newAgent.player = Board.V;
        }

        // using p_1 and p_2 we can find the next legal move
        while (true) {

            // first check UP
            if (p_2 == U) {
                if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j] + 1)) {

                    // if up is a legal move, make a new node based on moving piece at p_1 by updating the new agent's board
                    newAgent.makeMove(Move.Direction.UP, p_1);
                    newAgent.givePieces();
                    child = new Node(newAgent);

                    moveToArrive = new Move(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j], Move.Direction.UP);

                    // increment the direction value for next time and return the new child node
                    numChilds++;
                    p_2++;
                    return child;
                    // if not an allowed move just increment p_2 and continue
                } else {
                    p_2++;
                }
            }
            // check RIGHT
            if (p_2 == R) {

                if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i] + 1, playerState.getPiece(p_1)[Agent.j])) {

                    newAgent.makeMove(Move.Direction.RIGHT, p_1);
                    newAgent.givePieces();
                    child = new Node(newAgent);

                    moveToArrive = new Move(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j], Move.Direction.RIGHT);

                    // increment the direction value for next time and return
                    p_2++;
                    numChilds++;
                    return child;
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
                    if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i] - 1, playerState.getPiece(p_1)[Agent.j])) {

                        newAgent.makeMove(Move.Direction.LEFT, p_1);
                        newAgent.givePieces();
                        child = new Node(newAgent);
                        moveToArrive = new Move(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j], Move.Direction.LEFT);
                        // this is the last move for given piece at p_1, increment p_1 and numChilds, reset p_2 and return
                        p_2 = 0;
                        p_1++;
                        numChilds++;
                        return child;

                    } else {
                        // Otherwise just increment p_1, reset p_2 and continue
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
                    if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j] - 1)) {

                        newAgent.makeMove(Move.Direction.DOWN, p_1);
                        newAgent.givePieces();
                        child = new Node(newAgent);
                        moveToArrive = new Move(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j], Move.Direction.DOWN);
                        // this is the last move for given piece at p_1, increment p_1 and numChilds, reset p_2
                        p_2 = 0;
                        p_1++;
                        numChilds++;
                        return child;

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