package pope.minmax;

import java.util.Hashtable;

import pope.interfaces.Heuristics;
import fi.zem.aiarch.game.hierarchy.Board;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;
import fi.zem.aiarch.game.hierarchy.Board.Square;

public class MinMaxHeuristic implements Heuristics {

	public enum WeightNames {
		positionWeight, firepowerWeight, rankWeight, 		
		epositionWeight, efirepowerWeight, erankWeight 
	}
	
	private Hashtable<String, Integer> weights;
	
	public MinMaxHeuristic() {		
	}

	@Override
	public void setWeights(Hashtable<String, Integer> weigths) {
		this.weights = weigths; 
	}

	@Override
	public Integer evaluateFinnishedGame(Situation state, Side side) {
		Integer util;
		if (state.getWinner() == side)
		{
			util = Integer.MIN_VALUE; // should here be lose exception?
		}
		else if (state.getWinner() == side)
		{
			util = Integer.MAX_VALUE; // should here be win exception, what it should do??
		}
		else if (state.getWinner() == Side.NONE)
		{
			util = 0;
		}
		else
		{
			return null;
		}		
		return util;
	}
	
	/**
	 * Utility evaluator of Incomplete game.
	 * 
	 * @param state
	 * @return
	 */
	@Override
	public Integer evaluateIncompleteGame(Situation state) {
		Integer value = 0;
						
		Iterable<Square> own = state.getBoard().pieces(state.getTurn());
		Iterable<Square> enemy = state.getBoard().pieces(state.getTurn().opposite());

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
			value += board.firepower(side, current.getX(), current.getY());
		}		
		return value;
	}

	private Integer evalPiecesPosition(Iterable<Square> own, Side side) {
		Integer value = 0;
		for (Square current : own) {
			value -= (current.getOwner() == side.opposite()) ? 1 : 0;
		}
		return value;
	}
}