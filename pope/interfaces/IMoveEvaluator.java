package pope.interfaces;

import java.util.Random;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

/**
 * Interface for Itself to ask best move from move evaluator.
 * 
 * @author Sami Airaksinen
 *
 */
public interface IMoveEvaluator 
{
	/**
	 * Asks evaluator to calculate best possible move 
	 * with given time range.
	 * 
	 * @param state
	 * 	current State of the game
	 * @param cutDepth
	 * 	depth of the tree where to cut the Search
	 * @return
	 * 	Best move evaluated in given time limit.
	 */
	Move getBesMove(Situation state, Integer cutDepth);

	/**
	 * set game engine for moveEvaluator
	 * 
	 * @param engine
	 */
	void setEngine(Engine engine);

	/**
	 * sets Side of the AI to moveEvaluator
	 * 
	 * @param side
	 */
	void setAISide(Side side);

	/**
	 * Adds heuristics to AI which evaluates moves goodness.
	 * 
	 * @param heuristics
	 */
	void setHeuristics(IHeuristics heuristics);

	/**
	 * Adds random number generator to moveEvaluator.
	 * 
	 * @param rndGenerator
	 */
	void setRandomEngine(Random rndGenerator);	
}