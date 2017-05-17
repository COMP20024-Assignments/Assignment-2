
        package CyberdyneSystems.AI;

        import CyberdyneSystems.agents.T800;
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

    public int numChilds=0, numLegalMoves;
    private byte p_1, p_2;    // p_1 is the piece index, p_2 is how many directions for that index piece have been explored
    public Move firstMove;
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

        T800.expanded ++;

        Node child;
        final byte U = 0, R = 1, L = 2, D = 3; // direction values for finding next nodes

        // return null if all children have been explored
        if (numChilds >= numLegalMoves) {
            System.out.println("**************************************");
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

                    // in the node being created enemy is the agent making this move, so it needs to drive changes here
                    newAgent.enemy.makeMove(Move.Direction.UP, p_1);

                    // give the updated board to the newAgent (playerState in next node)
                    newAgent.giveBoard(newAgent.enemy.board.layout);
                    newAgent.givePieces();

                    if (first == null) {
                        child = new Node(newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                playerState.getPiece(p_1)[Agent.j], Move.Direction.UP));
                    } else {
                        child = new Node(newAgent, first);
                    }
                    // increment the direction value for next time and return
                    p_2++;
                    numChilds++;

                    return child;

                // increment the direction index if UP is not allowed
                } else {
                    p_2++;
                }
            }
            // check RIGHT
            if (p_2 == R) {
                if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i] + 1,
                        playerState.getPiece(p_1)[Agent.j], playerState.player)) {

             /*       // if this is the first move, set firstMove
                    if (first == null) {
                        firstMove = new Move(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j],
                                Move.Direction.RIGHT);
                    }
*/
                    newAgent.enemy.makeMove(Move.Direction.RIGHT, p_1);

                    // give the updated board to the newAgent (playerState in next node)
                    newAgent.giveBoard(newAgent.enemy.board.layout);
                    newAgent.givePieces();

                    if (first == null) {
                        child = new Node(newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                playerState.getPiece(p_1)[Agent.j], Move.Direction.RIGHT));
                    } else {
                        child = new Node(newAgent, first);
                    }

                    // increment the direction value for next time and return
                    p_2++;
                    numChilds++;

                    return child;

                // increment the direction index if RIGHT is not allowed
                } else {
                    p_2++;
                }

            }
            // check LEFT, if the player is H, just increment p_2 and continue to DOWN
            if (p_2 == L) {

                if (playerState.player == Board.H) {
                    p_2++;

                } else {
                    if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i] - 1,
                            playerState.getPiece(p_1)[Agent.j], playerState.player)) {

          /*              // if this is the first move, set firstMove
                        if (first == null) {
                            firstMove = new Move(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j],
                                    Move.Direction.LEFT);
                        }
                        */
                        newAgent.enemy.makeMove(Move.Direction.LEFT, p_1);



                        // give the updated board to the newAgent (playerState in next node)
                        newAgent.giveBoard(newAgent.enemy.board.layout);
                        newAgent.givePieces();



                        if (first == null) {
                            child = new Node(newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                    playerState.getPiece(p_1)[Agent.j], Move.Direction.LEFT));
                        } else {
                            child = new Node(newAgent, first);
                        }

                        // since V cant move down, reset p_2 to 0 and increment p_1
                        p_2 = 0;
                        p_1++;
                        numChilds++;

                        return child;

                    // if LEFT is not allowed, reset p_2 and increment p_1
                    } else {
                        p_1++;
                        p_2 = 0;
                    }
                }
            }
            // check DOWN, V should not be able to get here, check just in case and increment/reset their p values
            if (p_2 == D) {

                if (playerState.player == Board.V) {
                    // shouldn't get here
                    p_1++;
                    p_2 = 0;

                } else {
                    if (playerState.board.canMoveTo(playerState.getPiece(p_1)[Agent.i],
                            playerState.getPiece(p_1)[Agent.j] - 1, playerState.player)) {

                 /*       // if this is the first move, set firstMove
                        if (first == null) {
                            firstMove = new Move(playerState.getPiece(p_1)[Agent.i], playerState.getPiece(p_1)[Agent.j],
                                    Move.Direction.DOWN);
                        }
*/
                        newAgent.enemy.makeMove(Move.Direction.DOWN, p_1);

                        // give the enemy agent to the next node the new state's layout and pieces
                        newAgent.giveBoard(newAgent.enemy.board.layout);
                        newAgent.givePieces();

                        if (first == null) {
                            child = new Node(newAgent, new Move(playerState.getPiece(p_1)[Agent.i],
                                    playerState.getPiece(p_1)[Agent.j], Move.Direction.DOWN));
                        } else {
                            child = new Node(newAgent, first);
                        }
                        // this is the last move for given piece at p_1, increment p_1 and reset p_2
                        p_2 = 0;
                        p_1++;
                        numChilds++;
                        return child;

                    // if not allowed, increment/ reset p values and continue loop
                    } else {
                        p_1++;
                        p_2 = 0;
                    }
                }
            }
        }
    }

}