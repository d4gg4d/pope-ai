package pope.minmax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import pope.interfaces.Heuristics;
import pope.interfaces.moveEvaluator;
import fi.zem.aiarch.game.hierarchy.Board;
import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;
import fi.zem.aiarch.game.hierarchy.Board.Square;

/**
 * 
 * Classic MinMax adversarial search Algorithm with tree memorization.
 * 
 * @author Sami Airaksinen
 */

public class MinMaxBasic implements moveEvaluator 
{	
	public static Engine GAME;	
	public static Integer CUT_DEPTH;
	
	private Heuristics heuristics = new MinMaxHeuristic();

	private GameTreeNode gameTree;
	
	private Random rnd;
	private Side sideOfAI;
	
	public MinMaxBasic(Situation root)
	{
		gameTree = new GameTreeNode(root, 0);
	}

	/**
	 * @inherit
	 */
	@Override
	public Move getBesMove(Situation state, Integer timeLimit) {
		//TODO cutDepth(time)
		try {
			return minMaxDecision(state);
		} catch (Exception e) {			
			e.printStackTrace();

			//FALLBACK MODE
			return state.legal().get(rnd.nextInt()); 
		}
	}
	
	/**
	 * Actual decision maker.
	 * 
	 * @param state current State of The game.
	 * @return best possible move to MaxPlayer (player that has turn)
	 * @throws Exception 
	 */
	public Move minMaxDecision(Situation state) throws Exception
	{
		Integer value = maxValue(gameTree);
		
		Situation bestNextSitutation;		
		for (GameTreeNode current : gameTree.getChilds()) 
		{
			if (current.value == value)
			{
				bestNextSitutation = current.state;
				return bestNextSitutation.getPreviousMove();
			}
		}
		throw new Exception("Error in selecting next move.");
	}
	
	/**
	 * Calculates players MAX utility
	 * 
	 * @param node 
	 * 	gameTree node
	 * @return
	 * Utility value for current decision
	 * @throws Exception
	 */
	private Integer maxValue(GameTreeNode node) throws Exception
	{
		if (terminalTest(node)) 
		{
			return utility(node.state);
		}
		
		Integer value = Integer.MIN_VALUE;
				
		for (Situation current : nextPossibleStates(node.state) ) 
		{
			GameTreeNode nextNode = new GameTreeNode(current, node.depth + 1);
			value = Math.max(value, minValue(nextNode));
			nextNode.value = value;
			node.addChild(nextNode);
		}
		return value;
	}
	
	/**
	 * Calculates players MIN utility
	 * 
	 * @param node 
	 * 	gameTree node
	 * @return
	 * Utility value for current decision
	 * @throws Exception
	 */	
	private Integer minValue(GameTreeNode node) throws Exception
	{
		if (terminalTest(node)) 
		{
			return utility(node.state);
		}
		
		int value = Integer.MAX_VALUE;
		
		for (Situation current : nextPossibleStates(node.state) ) 
		{
			GameTreeNode nextNode = new GameTreeNode(current, node.depth + 1);
			value = Math.min(value, maxValue(nextNode));
			nextNode.value = value;
			node.addChild(nextNode);
		}
		return value;
	}
	
	/**
	 * Generates game tree Branches.
	 * 
	 * @param state
	 * @return all legal states that the Side can 
	 */
	private Collection<Situation> nextPossibleStates(Situation state)
	{
		Collection<Situation> nextStates = new ArrayList<Situation>(); 
				
		for (Move current : state.legal(state.getTurn())) 
		{
			nextStates.add(state.copyApply(current));
		}		
		return nextStates;
	}
	
	/**
	 * Test if node is state where to stop current branch.
	 * 
	 * @param node
	 * @return
	 */
	private Boolean terminalTest(GameTreeNode node)
	{
		return  node.depth > CUT_DEPTH || node.state.isFinished();
	}
		
	/**
	 * Evaluates weighted utility of current state.
	 * 
	 * @param state Current state
	 * @return Utility value of current state.
	 * @throws Exception
	 */
	private Integer utility(Situation state) throws Exception
	{
		if (state.isFinished())
		{
			return heuristics.evaluateFinnishedGame(state, state.getTurn());
		}
		else
		{
			return heuristics.evaluateIncompleteGame(state);
		}
	}
		
	@Override
	public void setAISide(Side side) {
		sideOfAI = side;
	}

	@Override
	public void setEngine(Engine engine) {
		GAME = engine;
	}

	@Override
	public void setHeuristics(Heuristics heuristics) {
		this.heuristics = heuristics;
	}

	@Override
	public void setRandomEngine(Random rndGenerator) {
		this.rnd = rndGenerator;
	}
}