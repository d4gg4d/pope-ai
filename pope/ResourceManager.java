package pope;

import java.lang.management.ManagementFactory;

import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.SoftTimeLimiter;
import pope.interfaces.IResourceManager;

public class ResourceManager implements IResourceManager {

	private static final Integer DEFAULT_SEEKDEPTH = 7;
	private static final long MEGABYTE = (long) Math.pow(2, 20);
		
	private Integer depth = DEFAULT_SEEKDEPTH;

	private SoftTimeLimiter timer;

	private int nextTimeInterval ;

	private int timeStep;
	
	private long memoryLimit = 250*MEGABYTE;
	
	public ResourceManager()
	{
	}
	
	public ResourceManager(Integer depth)
	{
		this.depth = depth;
	}
	
	public ResourceManager(Integer totalGameTime, Integer maxTurns)
	{
		timeStep = totalGameTime / maxTurns;
	}
	
	@Override
	public Integer calculateCutDepth(int timeLeft) {
		return depth;
	}

	@Override
	public void startTurn(Side side, int timeLeft) {
		timer = new SoftTimeLimiter(timeLeft);
		timer.start(side);
		nextTimeInterval = timeLeft - timeStep;
		//FIXME
//		System.out.println("Starting turn, (time left, nextStop):" + timer.getTimeLeft() + " : " + nextTimeInterval);
//		System.out.println("(timeLimit, timer.timeLimit):" + timeLeft + " : " + timer.getTimeLimit());
	}

	@Override
	public boolean timeLimitReached() {
		//FIXME
//		System.out.println("timer: " + timer.getTimeLeft());
		if (timer.getTimeLeft() > nextTimeInterval)
		{
			return false;
		}
		else
		{
			timer.stop();
			return true;
		}
	}

	@Override
	public boolean memoryLimitReached() {
//		System.out.println("mem: " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() +":" + memoryLimit);		
		if (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() > memoryLimit)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}