package pope.interfaces;

import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Situation;

/**
 * Interface for Itself to ask best move from move evaluator.
 * 
 * @author Sami Airaksinen
 *
 */
public interface moveEvaluator 
{
	/**
	 * Asks evaluator to calculate best possible move 
	 * with given time range.
	 * 
	 * @param state
	 * @param timeLimit
	 * @return
	 */
	public Move getBesMove(Situation state, Integer timeLimit);	
}
