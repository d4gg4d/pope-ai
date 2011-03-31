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
	public Move getBesMove(Situation state, Integer timeLimit);	
}
