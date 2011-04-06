package pope.interfaces;

import pope.SoftTimeLimitException;

public interface IHaltingCondition {	
	public void isTimeLimitReached() throws SoftTimeLimitException;
}
