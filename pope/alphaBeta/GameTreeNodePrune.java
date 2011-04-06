package pope.alphaBeta;

import fi.zem.aiarch.game.hierarchy.Situation;

public class GameTreeNodePrune extends GameTreeNode {

	public Integer a = Integer.MIN_VALUE;
	public Integer b = Integer.MAX_VALUE;	
	
	public GameTreeNodePrune(Situation state, Integer depth)
	{
		super(state, depth);
	}
}
