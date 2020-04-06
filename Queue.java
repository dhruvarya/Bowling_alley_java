/* Queue.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */
 
import java.util.Vector;
 
public class Queue {
	private Vector<Object> v;
	
	/** Queue()
	 * 
	 * creates a new queue
	 */
	public Queue() {
		v = new Vector<>();
	}
	
	public Object next() {
		return v.remove(0);
	}

	public void add(Object o) {
		v.addElement(o);
	}
	
	public boolean hasMoreElements() {
		return v.size() != 0;
	}

	public Vector<Party> asVector() {
		return (Vector<Party>) v.clone();
	}

	/**
	 * Creates a party from a Vector of nickNAmes and adds them to the wait queue.
	 *
	 * @param partyNicks	A Vector of NickNames
	 *
	 */

	public void addPartyQueue(Vector<String> partyNicks) {
		Vector<Bowler> partyBowlers = new Vector<>();
		for (String partyNick : partyNicks) {
			Bowler newBowler = BowlerFile.registerPatron(partyNick);
			partyBowlers.add(newBowler);
		}
		Party newParty = new Party(partyBowlers);
		this.add(newParty);
	}

	/**
	 * Returns a Vector of party names to be displayed in the GUI representation of the wait queue.
	 *
	 * @return a Vecotr of Strings
	 *
	 */

	public Vector<String> getPartyQueue() {
		Vector<String> displayPartyQueue = new Vector<>();
		for (int i = 0; i < (this.asVector()).size(); i++ ) {
			String nextParty =
					((this.asVector().get( i )).getMembers())
							.get(0)
							.getNickName() + "'s Party";
			displayPartyQueue.addElement(nextParty);
		}
		return displayPartyQueue;
	}


}
