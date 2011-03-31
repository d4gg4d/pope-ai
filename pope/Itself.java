package pope;

import pope.interfaces.moveEvaluator;
import pope.minmax.MinMaxBasic;
import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Player;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

/**
 * AI for the "Hierarchy" board game.
 * 
 * This class defines AI in its upper most level. 
 * It will schedule moves and initializes AI.
 * 
 * @author Sami Airaksinen
 */

public class Itself implements Player {
	
	private moveEvaluator moveEvaluator;
	
	public Itself() {		
		MinMaxBasic.CUT_DEPTH = 3;

		// weights for different aspects
		// utility function
		// seek depth
		// time allowance profile?
	}
	
	public void start(Engine engine, Side side) 
	{
		MinMaxBasic.GAME = engine;
		MinMaxBasic.SIDE = side;
	}
	
	public Move move(Situation situation, int timeLeft) 
	{
		moveEvaluator = new MinMaxBasic(situation);		
		return moveEvaluator.getBesMove(situation, timeLeft);
	}
}