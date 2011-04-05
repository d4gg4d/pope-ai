package pope;

import java.util.Hashtable;
import java.util.Random;

import pope.alphaBeta.AlphaBetaPruning;
import pope.alphaBeta.SimpleHeuristics;
import pope.interfaces.IHeuristics;
import pope.interfaces.IMoveEvaluator;
import pope.interfaces.IResourceManager;
import pope.interfaces.IHeuristics.WeightNames;
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
			
	private IMoveEvaluator moveEvaluator;
		
	private IResourceManager resourcesManager;
	
	private IHeuristics heuristics;
		
	public Itself(Random rnd) 
	{		
		// create heuristics
		heuristics = new SimpleHeuristics();						
		heuristics.setWeights(weigths());
		
		// create move evaluator
		moveEvaluator = new AlphaBetaPruning();		
		moveEvaluator.setRandomEngine(rnd);
		moveEvaluator.setHeuristics(heuristics);
		
		// craete Resource Manager
		resourcesManager = new ResourceManager();
	}
	
	public void start(Engine engine, Side side) 
	{
		// init move Evaluator
		moveEvaluator.setAISide(side);		
		moveEvaluator.setEngine(engine);
		
		// init heuristics
		heuristics.setSide(side);
		heuristics.setMode(IHeuristics.Mode.aggressive);
	}
	
	public Move move(Situation situation, int timeLeft) 
	{
//		if (resourcesManager.changeMode(heuristics.evaluateIncompleteGame(situation)))
//		{
//			heuristics.setMode(resourcesManager.optimalMode(situation));
//		}
		Integer cutDepth = resourcesManager.calculateCutDepth(timeLeft);
		return moveEvaluator.getBesMove(situation, cutDepth);
	}
	
	private Hashtable<WeightNames, Integer> weigths()
	{
		Hashtable<WeightNames, Integer> tmp = new Hashtable<WeightNames, Integer>();
		tmp.put(WeightNames.positionWeight, 1);
		tmp.put(WeightNames.firepowerWeight, 3);
		tmp.put(WeightNames.rankWeight, 30);

		tmp.put(WeightNames.epositionWeight, 1);
		tmp.put(WeightNames.efirepowerWeight, 3);
		tmp.put(WeightNames.erankWeight, 30);
		return tmp;
	}
}