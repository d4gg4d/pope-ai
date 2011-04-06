package pope.alphaBeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import pope.interfaces.IHeuristics;
import pope.interfaces.IMoveEvaluator;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

/**
 * 
 * Classic MinMax adversarial search Algorithm with tree memorization.
 * 
 * @author Sami Airaksinen
 */

public class AlphaBetaPruning implements IMoveEvaluator 
{	
	public static Engine GAME;	
	public static Integer CUT_DEPTH;
	
	private IHeuristics heuristics;

	private GameTreeNodePrune gameTree;
	
	private Random rnd;
	
	private Side sideOfAI;
	
	public AlphaBetaPruning()
	{
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
	public void setHeuristics(IHeuristics heuristics) {
		this.heuristics = heuristics;
	}

	@Override
	public void setRandomEngine(Random rndGenerator) {
		this.rnd = rndGenerator;
	}
	
	/**
	 * @inherit
	 */
	@Override
	public Move getBesMove(Situation state, Integer cutDepth) {
		CUT_DEPTH = cutDepth;

		gameTree = new GameTreeNodePrune(state, 0); //TODO better implementation of storaging game tree.
		
		try {
			if (state.mustFinishAttack())
			{
				return state.legal(sideOfAI).get(0);
			}
			else 
			{
				return alphaBetaSearch(state);	
			}
		} catch (Exception e) {	
			System.out.println("SEARCH FAILED: fallbacking to random move.");
			e.printStackTrace();

			List<Move> moves = state.legal();
			return moves.get(rnd.nextInt(moves.size()));
		}
	}
	
	/**
	 * Actual decision maker.
	 * 
	 * @param state current State of The game.
	 * @return best possible move to MaxPlayer (player that has turn)
	 * @throws Exception 
	 */
	public Move alphaBetaSearch(Situation state) throws Exception
	{
		Integer value = maxValue(gameTree, Integer.MIN_VALUE, Integer.MAX_VALUE);

		//FIXME clean
		System.out.println("best move is with value " + value);
		
		Situation bestNextSitutation;		
		for (GameTreeNode current : gameTree.getChilds())
		{
			if (current.value.intValue() == value.intValue())
			{		
				//FIXME clean
				System.out.println("found best mode");
				bestNextSitutation = current.state;
				return bestNextSitutation.getPreviousMove();
			}
		}
		
		//FIXME clean
		System.out.println("MOVE NOT FOUND!?");
		
		for (GameTreeNode current : gameTree.getChilds())
		{
			System.out.println("move with value " + current.value);
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
	private Integer maxValue(GameTreeNodePrune node, Integer a, Integer b) throws Exception
	{
		if (terminalTest(node)) 
		{
			return utility(node.state);
		}
		
		Integer value = Integer.MIN_VALUE;
				
		for (Situation current : nextPossibleStates(node.state) ) 
		{
			GameTreeNodePrune nextNode = new GameTreeNodePrune(current, node.depth + 1);
		
			value = Math.max(value, minValue(nextNode, a, b));			
			node.value = value;
			node.addChild(nextNode);

			if (value >= b)
			{
				return value;
			}
			
			a = Math.max(a, value);
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
	private Integer minValue(GameTreeNodePrune node, Integer a, Integer b) throws Exception
	{
		if (terminalTest(node)) 
		{
			return utility(node.state);
		}
		
		Integer value = Integer.MAX_VALUE;
		
		for (Situation current : nextPossibleStates(node.state) ) 
		{
			GameTreeNodePrune nextNode = new GameTreeNodePrune(current, node.depth + 1);
			
			value = Math.min(value, maxValue(nextNode, a, b));
			node.value = value;	
			node.addChild(nextNode);
			
			if (value <= a)
			{
				return value;
			}
			
			b = Math.min(b, value);
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
	private Integer utility(Situation state)
	{
		if (state.isFinished())
		{
			return heuristics.evaluateFinnishedGame(state);
		}
		else
		{
			return heuristics.evaluateIncompleteGame(state);
		}
	}
}