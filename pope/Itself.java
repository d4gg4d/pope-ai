package pope;

import java.util.Hashtable;
import java.util.Random;

import pope.alphaBeta.AlphaBetaPruning;
import pope.alphaBeta.SimpleHeuristics;

import pope.interfaces.IHaltingCondition;
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
	
	private static int TOTAL_GAMETIME = 1000;
	private static int MAX_TURNS = 120;
	
	private Side sideOfAI;
	
	private IMoveEvaluator moveEvaluator;
		
	private IResourceManager resourceManager;
	
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
		resourceManager = new ResourceManager(TOTAL_GAMETIME, MAX_TURNS);
	}
	
	public void start(Engine engine, Side side) 
	{
		sideOfAI = side;
		
		// init move Evaluator
		moveEvaluator.setAISide(side);		
		moveEvaluator.setEngine(engine);
		moveEvaluator.setHaltingCondition(new IHaltingCondition() {
			
			@Override
			public void isTimeLimitReached() throws SoftTimeLimitException {
				if (resourceManager.timeLimitReached())
				{
					System.out.println("SoftTime limit reached.");					
					throw new SoftTimeLimitException();
				}
			}
		});
		
		// init heuristics
		heuristics.setSide(side);
		heuristics.setMode(IHeuristics.Mode.aggressive);
	}
	
	/**
	 * 
	 */ 
	public Move move(Situation situation, int timeLeft) 
	{
		resourceManager.startTurn(sideOfAI, timeLeft);
		
		//TODO check good heuristic for the current game situation.
		
		//creating fallback mode;
		Move nextMove = moveEvaluator.getFallBackMove(situation);				
		
		try 
		{
			// popping out of this loop when time limiter hits the wall or nextMove is known to be Winning game path.
			for (int searchDepth = 1; searchDepth <  resourceManager.calculateCutDepth(timeLeft) ; searchDepth++) {
				System.out.println("Starting seekdepth: " + searchDepth); //FIXME CLEAN
				
				nextMove = moveEvaluator.getBesMove(situation, searchDepth);
				
				if (moveEvaluator.isPathOfWinningMove(nextMove))
				{
					System.out.println("found Blocking or winning move."); //FIXME CLEAN
					return nextMove;
				}
			}
		}
		catch (SoftTimeLimitException e)
		{
			System.out.println("ITSELF>SoftLimit passing last found move."); //FIXME CLEAN
			// catch Halting condition, at the moment nextMove has best possible move (naturally incomplete stateSearch is discarded).
			return nextMove;
		}
		catch (Exception e)
		{
			System.out.println("SEARCH FAILED: fallbacking to random move."); //FIXME CLEAN
			e.printStackTrace();

			return moveEvaluator.getFallBackMove(situation);
		}
		
		//To get here we have been searched all the game states, and then some but not found winning condition... so.
		System.out.println("Found route to place where shouldn't be!(?)"); //FIXME CLEAN
		return nextMove;
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