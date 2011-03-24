package minmaxsimple;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

/**
 * 
 * Classic MinMax adversarial search Algorithm with tree memorization.
 * 
 * @author samiaira
 */

public class MinMaxBasic {
	
	public static GameTree gameTree;
	
	public static Engine game;
	/**
	 * 
	 * @param state current State of The game.
	 * @return best possible move to MaxPlayer (player that has turn)
	 */
	static public Move minMaxDecision(Situation state)
	{
		gameTree = new GameTree();
		Integer value = maxValue(state);
		return null;
		return game.decode(gameTree.get(state.encode(null), value)).getPreviousMove();
	}
	
	static public Integer maxValue(Situation state)
	{
		if (terminalTest(state)) 
		{
			return utility(state);
		}
		
		Integer value = Integer.MIN_VALUE;
				
		for (Situation current : nextPossibleStates(state) ) 
		{
			value = Math.max(value, minValue(current));
			states.put(current.encode(null), value);
		}
		gameTree.get(state.encode(null)).putAll(states);
		return value;
	}
	
	static public Integer minValue(Situation state)
	{
		if (terminalTest(state)) 
		{
			return utility(state);
		}
		
		int value = Integer.MAX_VALUE;
		
		for (Situation current : nextPossibleStates(state) ) 
		{
			value = Math.min(value, maxValue(current));
		}
		return value;
	}
	
	static public int utility(Situation state)
	{
		return 1;
	}
	
	static public Boolean terminalTest(Situation state)
	{
		return state.isFinished();
	}
	
	static public Collection<Situation> nextPossibleStates(Situation state)
	{
		Collection<Situation> nextStates = new ArrayList<Situation>(); 
		
		for (Move current : state.legal(state.getTurn())) 
		{
			nextStates.add(state.copyApply(current));
		}		
		return nextStates;
	}
}
