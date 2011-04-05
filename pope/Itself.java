package pope;

import java.util.Hashtable;
import java.util.Random;

import pope.alphaBeta.AlphaBetaPruning;
import pope.alphaBeta.SimpleHeuristics;
import pope.interfaces.IHeuristics;
import pope.interfaces.IResourceManager;
import pope.interfaces.IMoveEvaluator;
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
	
	private IHeuristics heuristics;
	
	private IResourceManager resourcesManager;
	
	private Random rndGenerator;
	
	public Itself(Random rnd) 
	{
		// just in case
		this.rndGenerator = rnd;
		
		// create Components
		heuristics = new SimpleHeuristics();						
		heuristics.setWeights(weigths());
		
		moveEvaluator = new AlphaBetaPruning(new SimpleHeuristics());		
		moveEvaluator.setRandomEngine(rnd);
		moveEvaluator.setHeuristics(heuristics);
		
		resourcesManager = new ResourceManager(3);
	}
	
	public void start(Engine engine, Side side) 
	{
		// init move Evaluator
		moveEvaluator.setAISide(side);		
		moveEvaluator.setEngine(engine);
		
		heuristics.setSide(side);
	}
	
	public Move move(Situation situation, int timeLeft) 
	{
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