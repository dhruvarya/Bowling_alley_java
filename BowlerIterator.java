import java.util.Iterator;
import java.util.Vector;

public class BowlerIterator {
    private Iterator<Bowler> bowlerIterator;
    public BowlerIterator()
    {

    }
    public Iterator<Bowler> getBowlerIterator() {
        return bowlerIterator;
    }

    /** resetBowlerIterator()
     *
     * sets the current bower iterator back to the first bowler
     *
     * @pre the party as been assigned
     * @post the iterator points to the first bowler in the party
     */
    public void resetBowlerIterator(Party party) {
        bowlerIterator = (party.getMembers()).iterator();
    }

    public static Vector getPrintVector(Party party) {
        Vector printVector;
        EndGameReport egr = new EndGameReport( (party.getMembers().get(0)).getNickName() + "'s Party", party);
        printVector = egr.getResult();
        return printVector;
    }



    public void bowlermove(Gamestate state, Pinsetter setter, Scorer scorer,Party party){
        if (getBowlerIterator().hasNext()) {
            state.setCurrentThrower(getBowlerIterator().next());
            setter.setCanThrowAgain(true);
            setter.setTenthFrameStrike(false);
            state.setBall(0);
            while (setter.isCanThrowAgain()) {
                setter.ballThrown();		// simulate the thrower's ball hiting
                state.setBall(state.getBall()+1);
            }

            if (state.getFrameNumber() == 9){
                scorer.Onlastframe(state.getGameNumber(),state.getCurrentThrower());
            }
            setter.reset();
            scorer.bowlIndex++;

        }
        else {

            resetBowlerIterator(party);
            scorer.bowlIndex = 0;
            state.Lastbowler();
        }
    }
}
