package pope.alphaBeta;

import java.util.ArrayList;
import java.util.List;

import fi.zem.aiarch.game.hierarchy.Situation;

public class GameTreeNode {

	public Integer value;	
	public Integer depth;
	
	private ArrayList<GameTreeNode> childs;
	
	public GameTreeNode(Situation state, Integer depth)
	{
		this.depth = depth;
		childs = new ArrayList<GameTreeNode>();
	}
	
	public void addChild(GameTreeNode child)
	{
		childs.add(child);
	}
	
	public List<GameTreeNode> getChilds()
	{
		return childs;
	}
}
