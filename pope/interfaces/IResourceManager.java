package pope.interfaces;

public interface IResourceManager 
{
	/**
	 * Calculates cut depth respect to available game time.
	 * 
	 * @param timeLeft
	 * @return
	 */
	Integer calculateCutDepth(int timeLeft);
}
