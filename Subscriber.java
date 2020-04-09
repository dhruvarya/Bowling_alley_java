import java.util.*;
import java.io.*;

class Subscriber {

    /** The collection of subscribers */
    private Vector<ControlDeskObserver> subscribers;

    public Subscriber(){
        subscribers = new Vector<>();
    }

    /**
     * Allows objects to subscribe as observers
     *
     * @param adding	the ControlDeskObserver that will be subscribed
     *
     */

    public void subscribe(ControlDeskObserver adding) {
        subscribers.add(adding);
    }

    /**
     * Broadcast an event to subscribing objects.
     *
     * @param partyqueue	the ControlDeskEvent to broadcast
     *
     */

    public void publish(Vector<String> partyqueue) {
        for (ControlDeskObserver subscriber : subscribers) {
            subscriber.receiveControlDeskEvent(partyqueue);
        }
    }

}
