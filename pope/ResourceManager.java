package pope;

import pope.interfaces.IResourceManager;

public class ResourceManager implements IResourceManager {

	private static final Integer DEFAULT_SEEKDEPTH = 3;
	
	public Integer depth = DEFAULT_SEEKDEPTH;

	public ResourceManager()
	{
	}
	
	public ResourceManager(Integer depth)
	{
		this.depth = depth;
	}
	
	@Override
	public Integer calculateCutDepth(int timeLeft) {
		return depth;
	}
}
