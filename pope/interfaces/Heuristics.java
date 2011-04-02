package pope.interfaces;

import java.util.ArrayList;
import java.util.Hashtable;

import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public interface Heuristics {

	/**
	 * Set weight parameters for different utility function components.
	 */
	void setWeights(Hashtable<String, Integer> weigths);
	
	/**
	 * Applies utility function to finished state of the game. 
	 * 
	 * @param state
	 * 	Finished State,throws exception if non finished state
	 * @param side
	 * 	defines side to whom the utility is evaluated.
	 * @return
	 * 	utility value of finished game.
	 */
	Integer evaluateFinnishedGame(Situation state, Side side);
	
	/**
	 * Applies utility to non finished game.
	 * 
	 * @param state
	 * 	current State
	 * @return
	 * 	utility of non finished game
	 */
	Integer evaluateIncompleteGame(Situation state);
}
