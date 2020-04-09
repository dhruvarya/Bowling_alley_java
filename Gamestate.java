import java.util.Iterator;
import java.util.Vector;

public class Gamestate {
    private boolean gameIsHalted;
    private boolean partyAssigned;
    private boolean gameFinished;
    private int gameNumber;
    private int frameNumber;
    private int ball;
    private Bowler currentThrower;			// = the thrower who just took a throw
    public BowlerIterator bowlerIterator;

    public Gamestate()
    {
        gameIsHalted = false;
        partyAssigned = false;
        gameNumber = 0;
        bowlerIterator = new BowlerIterator();
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public boolean isGameIsHalted() {
        return gameIsHalted;
    }

    public void setGameIsHalted(boolean gameIsHalted) {
        this.gameIsHalted = gameIsHalted;
    }

    public boolean isPartyAssigned() {
        return partyAssigned;
    }

    public void setPartyAssigned(boolean partyAssigned) {
        this.partyAssigned = partyAssigned;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getBall() {
        return ball;
    }

    public void setBall(int ball) {
        this.ball = ball;
    }

    public Bowler getCurrentThrower() {
        return currentThrower;
    }

    public void setCurrentThrower(Bowler currentThrower) {
        this.currentThrower = currentThrower;
    }

    public void Lastbowler(){
        frameNumber++;
        if (frameNumber > 9) {
            gameFinished=true;
            gameNumber++;
        }
    }


    public  void reset()
    {
        partyAssigned=true;
        gameNumber=0;
        gameFinished=false;
        frameNumber=0;
    }
}

