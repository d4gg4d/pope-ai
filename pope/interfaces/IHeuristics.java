package pope.interfaces;

import java.util.Hashtable;

import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public interface IHeuristics {

	public enum WeightNames {
		positionWeight, firepowerWeight, rankWeight, 		
		epositionWeight, efirepowerWeight, erankWeight 
	}
	
	/**
	 * Set weight parameters for different utility function components. 
	 * If weigths is null then weigths are ignored.
	 */
	void setWeights(Hashtable<WeightNames, Integer> weigths);
	
	/**
	 * Applies utility function to finished state of the game. 
	 * 
	 * @param state
	 * 	Finished State,throws exception if non finished state
	 * @param side
	 * 	defines side to whom the utility is evaluated.
	 * @return
	 * 	utility value of finished game.
	 * @throws Exception 
	 */
	Integer evaluateFinnishedGame(Situation state) throws Exception;
	
	/**
	 * Applies utility to non finished game.
	 * 
	 * @param state
	 * 	current State
	 * @return
	 * 	utility of non finished game
	 */
	Integer evaluateIncompleteGame(Situation state, Side side);

	void setSide(Side side);
}
