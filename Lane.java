
/* $Id$
 *
 * Revisions:
 *   $Log: Lane.java,v $
 *   Revision 1.52  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.51  2003/02/20 20:01:32  ???
 *   Added things.
 *
 *   Revision 1.50  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.49  2003/02/20 11:18:22  ???
 *   Works beautifully.
 *
 *   Revision 1.48  2003/02/20 04:10:58  ???
 *   Score reporting code should be good.
 *
 *   Revision 1.47  2003/02/17 00:25:28  ???
 *   Added disbale controls for View objects.
 *
 *   Revision 1.46  2003/02/17 00:20:47  ???
 *   fix for event when game ends
 *
 *   Revision 1.43  2003/02/17 00:09:42  ???
 *   fix for event when game ends
 *
 *   Revision 1.42  2003/02/17 00:03:34  ???
 *   Bug fixed
 *
 *   Revision 1.41  2003/02/16 23:59:49  ???
 *   Reporting of sorts.
 *
 *   Revision 1.40  2003/02/16 23:44:33  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.39  2003/02/16 23:43:08  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.38  2003/02/16 23:41:05  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.37  2003/02/16 23:00:26  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.36  2003/02/16 21:31:04  ???
 *   Score logging.
 *
 *   Revision 1.35  2003/02/09 21:38:00  ???
 *   Added lots of comments
 *
 *   Revision 1.34  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.33  2003/02/05 11:16:34  ???
 *   Boom-Shacka-Lacka!!!
 *
 *   Revision 1.32  2003/02/05 01:15:19  ???
 *   Real close now.  Honest.
 *
 *   Revision 1.31  2003/02/04 22:02:04  ???
 *   Still not quite working...
 *
 *   Revision 1.30  2003/02/04 13:33:04  ???
 *   Lane may very well work now.
 *
 *   Revision 1.29  2003/02/02 23:57:27  ???
 *   fix on pinsetter hack
 *
 *   Revision 1.28  2003/02/02 23:49:48  ???
 *   Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.27  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.26  2003/02/02 23:11:42  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.25  2003/02/02 22:52:19  ???
 *   Lane compiles
 *
 *   Revision 1.24  2003/02/02 22:50:10  ???
 *   Lane compiles
 *
 *   Revision 1.23  2003/02/02 22:47:34  ???
 *   More observering.
 *
 *   Revision 1.22  2003/02/02 22:15:40  ???
 *   Add accessor for pinsetter.
 *
 *   Revision 1.21  2003/02/02 21:59:20  ???
 *   added conditions for the party choosing to play another game
 *
 *   Revision 1.20  2003/02/02 21:51:54  ???
 *   LaneEvent may very well be observer method.
 *
 *   Revision 1.19  2003/02/02 20:28:59  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.18  2003/02/02 18:18:51  ???
 *   more changes. just need to fix scoring.
 *
 *   Revision 1.17  2003/02/02 17:47:02  ???
 *   Things are pretty close to working now...
 *
 *   Revision 1.16  2003/01/30 22:09:32  ???
 *   Worked on scoring.
 *
 *   Revision 1.15  2003/01/30 21:45:08  ???
 *   Fixed speling of received in Lane.
 *
 *   Revision 1.14  2003/01/30 21:29:30  ???
 *   Fixed some MVC stuff
 *
 *   Revision 1.13  2003/01/30 03:45:26  ???
 *   *** empty log message ***
 *
 *   Revision 1.12  2003/01/26 23:16:10  ???
 *   Improved thread handeling in lane/controldesk
 *
 *   Revision 1.11  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added Lane/Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.10  2003/01/26 20:44:05  ???
 *   small changes
 *
 * 
 */

import java.util.Vector;
import java.util.Iterator;
import java.util.Date;

public class Lane extends Thread implements PinsetterObserver {
	private  Scorer scorer = new Scorer();
	private Party party;
	private Pinsetter setter;
	private Gamestate state;
	private LaneSubscriber laneSubscriber;
	/** Lane()
	 *
	 * Constructs a new lane and starts its thread
	 * 
	 * @pre none
	 * @post a new lane has been created and its thered is executing
	 */
	public Lane() { 
		setter = new Pinsetter();
		laneSubscriber=new LaneSubscriber();
		state= new Gamestate();
		setter.subscribe( this );
		this.start();
	}

	/** run()
	 * 
	 * entry point for execution of this lane 
	 */
	public void run() {
		
		while (true) {
			game();
			try {
				sleep(10);
			} catch (Exception ignored) {}

		}
	}

	public void game(){
		if (state.isPartyAssigned())
		{
			if(state.isGameFinished())
			{
				int result=party.getresult();
				if (result == 1) {
					scorer.resetScore(party);
					state.setGameFinished(false);
					state.setFrameNumber(0);
					state.bowlerIterator.resetBowlerIterator(party);

				}
				else {
					// no, dont want to play another game
					NotPlayingAgain();
				}

			}
			else
			{
				while (state.isGameIsHalted()) {
					try {
						sleep(10);
					} catch (Exception ignored) {}
				}

				state.bowlerIterator.bowlermove(state,setter,scorer,party);

			}
		}

	}


	private void NotPlayingAgain()
	{
		Vector printVector = BowlerIterator.getPrintVector(party);
		BowlerIterator scoreIt = new BowlerIterator();
		scoreIt.resetBowlerIterator(party);
		party = null;
		state.setPartyAssigned(false);
		laneSubscriber.publish(lanePublish());

		int myIndex = 0;
		while (scoreIt.getBowlerIterator().hasNext()){
			Bowler thisBowler = scoreIt.getBowlerIterator().next();
			ScoreReport sr = new ScoreReport( thisBowler, scorer.finalScores[myIndex++], state.getGameNumber());
			sr.sendEmail(thisBowler.getEmail());
			for (Object o : printVector) {
				if (thisBowler.getNick().equals(o)) {
//					System.out.println("Printing " + thisBowler.getNick());
					sr.sendPrintout();
				}
			}

		}
	}


	/** recievePinsetterEvent()
	 * 
	 * recieves the thrown event from the pinsetter
	 *
	 * @pre none
	 * @post the event has been acted upon if desiered
	 * 
	 * @param pe 		The pinsetter event that has been received.
	 */
	public void receivePinsetterEvent(PinsetterEvent pe) {
		
		if (pe.pinsDownOnThisThrow() >=  0) {			// this is a real throw
			scorer.markScore(state.getCurrentThrower(), state.getFrameNumber() + 1, pe.getThrowNumber(), pe.pinsDownOnThisThrow());
			laneSubscriber.publish( lanePublish() );
			setter.ThisIsARealThrow(pe,state.getFrameNumber());
			}
	}

		
	/** assignParty()
	 * 
	 * assigns a party to this lane
	 * 
	 * @pre none
	 * @post the party has been assigned to the lane
	 * 
	 * @param theParty		Party to be assigned
	 */
	public void assignParty( Party theParty ) {
		party = theParty;
		state.bowlerIterator.resetBowlerIterator(party);
		state.reset();
		scorer.curScores = new int[party.getMembers().size()];
		scorer.cumulScores = new int[party.getMembers().size()][10];
		scorer.finalScores = new int[party.getMembers().size()][128];
		scorer.resetScore(party);
	}



	/** lanePublish()
	 *
	 * Method that creates and returns a newly created laneEvent
	 * 
	 * @return		The new lane event
	 */
	private LaneEvent lanePublish(  ) {
		return new LaneEvent(party, scorer.bowlIndex, state.getCurrentThrower(), scorer.cumulScores, scorer.scores, state.getFrameNumber()+1, scorer.curScores, state.getBall(), state.isGameIsHalted());
	}


	/** isPartyAssigned()
	 * 
	 * checks if a party is assigned to this lane
	 * 
	 * @return true if party assigned, false otherwise
	 */
	public boolean isPartyAssigned() {
		return state.isPartyAssigned();
	}
	

	/** subscribe
	 * 
	 * Method that will add a subscriber
	 * 
	 * @param adding	Observer that is to be added
	 */

	public void subscribe( LaneObserver adding ) {
		laneSubscriber.subscribe(adding);
	}

	/**
	 * Accessor to get this Lane's pinsetter
	 * 
	 * @return		A reference to this lane's pinsetter
	 */

	public Pinsetter getPinsetter() {
		return setter;
	}

	/**
	 *  Used to pause/rresume
	 */
	public void pauseGame(){
		state.setGameIsHalted(true);
		laneSubscriber.publish(lanePublish());
	}

	public void unpauseGame(){
		state.setGameIsHalted(false);
		laneSubscriber.publish(lanePublish());
	}
}
