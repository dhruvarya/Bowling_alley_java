import java.util.Vector;

public class LaneSubscriber {

    private Vector<LaneObserver> subscribers;

    public  LaneSubscriber(){
        subscribers = new Vector<>();
    }

    /** subscribe
     *
     * Method that will add a subscriber
     *
     * @param adding	Observer that is to be added
     */

    public void subscribe( LaneObserver adding ) {
        subscribers.add( adding );
    }

    /** unsubscribe
     *
     * Method that unsubscribes an observer from this object
     *
     * @param removing	The observer to be removed
     */

    public void unsubscribe( LaneObserver removing ) {
        subscribers.remove( removing );
    }

    /** publish
     *
     * Method that publishes an event to subscribers
     *
     * @param event	Event that is to be published
     */

    public void publish( LaneEvent event ) {
        for (Object subscriber : subscribers) {
            ((LaneObserver) subscriber).receiveLaneEvent(event);
        }
    }

}
