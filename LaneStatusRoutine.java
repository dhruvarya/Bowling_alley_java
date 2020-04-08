import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;

public class LaneStatusRoutine {
    JPanel laneStatusPanel;
    public LaneStatusRoutine(ControlDesk controlDesk) {
        laneStatusPanel = new JPanel();

		laneStatusPanel.setLayout(new GridLayout(controlDesk.getNumLanes(), 1));
		laneStatusPanel.setBorder(new TitledBorder("Lane Status"));

		HashSet<Lane> lanes=controlDesk.getLanes();
		Iterator<Lane> it = lanes.iterator();
		int laneCount=0;
		while (it.hasNext()) {
			Lane curLane = it.next();
			LaneStatusView laneStat = new LaneStatusView(curLane,(laneCount+1));
			curLane.subscribe(laneStat);
			curLane.getPinsetter().subscribe(laneStat);
			JPanel lanePanel = laneStat.showLane();
			lanePanel.setBorder(new TitledBorder("Lane" + ++laneCount ));
			laneStatusPanel.add(lanePanel);
		}
    }

    public JPanel getPanel() {
        return laneStatusPanel;
    }
}
