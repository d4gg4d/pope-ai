package pope.interfaces;

import fi.zem.aiarch.game.hierarchy.Side;

public interface IResourceManager 
{
	/**
	 * Calculates cut depth respect to available game time.
	 * 
	 * @param timeLeft
	 * @return
	 */
	Integer calculateCutDepth(int timeLeft);

	public boolean timeLimitReached();

	void startTurn(Side side, int timeLeft);
}
