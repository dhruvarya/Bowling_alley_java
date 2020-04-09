/* ControlDeskObserver.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

import java.util.Vector;

/**
 * Interface for classes that observe control desk events
 *
 */

public interface ControlDeskObserver {

	void receiveControlDeskEvent(Vector<String> partyqueue);

}
