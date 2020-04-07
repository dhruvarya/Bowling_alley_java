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

}
