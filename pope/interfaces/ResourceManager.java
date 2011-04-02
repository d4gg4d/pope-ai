package pope.interfaces;

public interface ResourceManager 
{
	/**
	 * Calculates cut depth respect to available game time.
	 * 
	 * @param timeLeft
	 * @return
	 */
	Integer calculateCutDepth(int timeLeft);
}
