package pope.alphaBeta;

import java.util.BitSet;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Situation;

public class GameTreeNodePrune extends GameTreeNode {

	public static Engine engine;
	
	private BitSet stateBitSet;
	
	public GameTreeNodePrune(Situation state, Integer depth)
	{
		super(null, depth);
		setState(state);
	}
	
	public Situation getState()
	{
		return engine.decode(stateBitSet);
	}
	
	public void setState(Situation state)
	{
		stateBitSet = state.encode(null);
	}	
}
