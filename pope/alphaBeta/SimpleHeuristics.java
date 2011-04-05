package pope.alphaBeta;

import java.util.Hashtable;

import pope.interfaces.IHeuristics;
import fi.zem.aiarch.game.hierarchy.Board;
import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;
import fi.zem.aiarch.game.hierarchy.Board.Square;

public class SimpleHeuristics implements IHeuristics {
	
	private Hashtable<WeightNames, Integer> weights;
	
	public Side sideOfAI;
	
	public SimpleHeuristics() {		
	}

	@Override
	public void setWeights(Hashtable<WeightNames, Integer> weigths) {
		if (weigths == null)
		{			
			Hashtable<WeightNames, Integer> tmp = new Hashtable<WeightNames, Integer>();
			tmp.put(WeightNames.positionWeight, 1);
			tmp.put(WeightNames.firepowerWeight, 1);
			tmp.put(WeightNames.rankWeight, 1);

			tmp.put(WeightNames.epositionWeight, 1);
			tmp.put(WeightNames.efirepowerWeight, 1);
			tmp.put(WeightNames.erankWeight, 1);

			this.weights = tmp;
		}
		else
		{
			this.weights = weigths;
		}
	}

	@Override
	public Integer evaluateFinnishedGame(Situation state) throws Exception {
		Integer util;
		if (state.getWinner() == sideOfAI)
		{
			util = 1000000;
		}
		else if (state.getWinner() == sideOfAI.opposite())
		{
			util = -1000000;
		}
		else
		{
			util = 0; // draw.
		}
		return util;
	}
	
	/**
	 * Utility evaluator of Incomplete game. Value is positioned to player in turn.
	 * 
	 * @param state
	 * @return
	 */
	@Override
	public Integer evaluateIncompleteGame(Situation state, Side side) {
		Integer value = 0;
						
		Iterable<Square> own = state.getBoard().pieces(side);
		Iterable<Square> enemy = state.getBoard().pieces(side.opposite());

		//OWN AS POSITIVE value
		
		//eval own pieces position 
		value += weights.get(WeightNames.positionWeight) * evalPiecesPosition(own, state.getTurn());
		
		//eval own pieces firepower
		value += weights.get(WeightNames.firepowerWeight) * evalPiecesFirePower(own, state.getBoard(), state.getTurn());

		//eval own pieces rank
		value += weights.get(WeightNames.rankWeight) * evalPiecesRank(own);
		
		//ENEMY AS negative value
		
		//eval enemy pieces position 
		value -= weights.get(WeightNames.epositionWeight) * evalPiecesPosition(enemy, state.getTurn().opposite());
		
		//eval enemy pieces firepower
		value -= weights.get(WeightNames.efirepowerWeight) * evalPiecesFirePower(enemy, state.getBoard(), state.getTurn().opposite());

		//eval enemy pieces rank
		value -= weights.get(WeightNames.erankWeight) * evalPiecesRank(enemy);
		
		return value;
	}
	
	private Integer evalPiecesRank(Iterable<Square> own) {
		Integer value = 0;
		for (Square current : own) {
			value += current.getPiece().getValue();
		}		
		return value;
	}

	private Integer evalPiecesFirePower(Iterable<Square> own, Board board, Side side) {
		Integer value = 0;
		for (Square current : own) {
			Integer factor = (current.getOwner() == side) ? 0 : 1;
			value += board.firepower(side, current.getX(), current.getY()) * factor;
		}		
		return value;
	}

	private Integer evalPiecesPosition(Iterable<Square> own, Side side) {
		Integer value = 0;
		for (Square current : own) {
			Integer factor = current.getPiece().getValue();
			value += (current.getOwner() == side.opposite()) ? -1*factor : 0;
			value += (current.getOwner() == side.NONE) ? 2/factor : 0;
		}
		return value;
	}

	@Override
	public void setSide(Side side) {
		sideOfAI = side;
	}
}