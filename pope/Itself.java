package pope;

import java.util.ArrayList;
import java.util.Random;

import pope.interfaces.Heuristics;
import pope.interfaces.ResourceManager;
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
	
	private static final Integer DEFAULT_SEEKDEPTH = 3;

	private moveEvaluator moveEvaluator;
	
	private Heuristics heuristics;
	
	private ResourceManager resourcesManager;
	
	private Random rndGenerator;
	
	public Itself(Random rnd) 
	{
		// just in case
		this.rndGenerator = rnd;
		
		// create Components
						
		// weights for different aspects
		heuristics.setWeights(new ArrayList<Integer>());		
	}
	
	public void start(Engine engine, Side side) 
	{
		// init move Evaluator
		moveEvaluator.setRandomEngine(rndGenerator);
		moveEvaluator.setAISide(side);
		moveEvaluator.setEngine(engine);
		moveEvaluator.setHeuristics(heuristics);		
	}
	
	public Move move(Situation situation, int timeLeft) 
	{
		Integer cutDepth = resourcesManager.calculateCutDepth(timeLeft);
		return moveEvaluator.getBesMove(situation, cutDepth);
	}
}