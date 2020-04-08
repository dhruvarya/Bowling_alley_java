/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/*
 * Class for representation of the control desk
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.*;

public class ControlDeskView implements ActionListener, ControlDeskObserver {

	private JFrame win;
	private JList<String> partyList;

	/** The maximum  number of members in a party */
	
	private ControlDesk controlDesk;

	/**
	 * Displays a GUI representation of the ControlDesk
	 *
	 */

	public ControlDeskView(ControlDesk controlDesk) {
		this.controlDesk = controlDesk;

		win = new JFrame("Control Desk");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());



		// Controls Panel
		JPanel controlsPanel = new ControlsPanelRoutine(this).getPanel();
		// Lane Status Panel
		JPanel laneStatusPanel = new LaneStatusRoutine(controlDesk).getPanel();
		// Party Queue Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Party Queue"));

		Vector<String> empty = new Vector<>();
		empty.add("(Empty)");
		Scrollable scroll= new Scrollable(null);

		partyList=scroll.Scroller(120,10,empty,partyPanel);


		// Clean up main panel
		colPanel.add(controlsPanel, "East");
		colPanel.add(laneStatusPanel, "Center");
		colPanel.add(partyPanel, "West");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		/* Close program when this window closes */
		win.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
				((screenSize.width) / 2) - ((win.getSize().width) / 2),
				((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}


	/**
	 * Handler for actionEvents
	 *
	 * @param e	the ActionEvent that triggered the handler
	 *
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add Party")) {
			AddPartyView addPartyWin = new AddPartyView(this);
		}
		if(e.getActionCommand().equals("Player Records")) {
			try {
				ScoreView scoreViewWin = new ScoreView();
			} catch(Exception er) {
				System.out.println(e);
			}
		}
		if (e.getActionCommand().equals("Assign Lanes")) {
			controlDesk.assignLane();
		}
		if (e.getActionCommand().equals("Finished")) {
			win.setVisible(false);
			System.exit(0);
		}
	}

	/**
	 * Receive a new party from andPartyView.
	 *
	 * @param addPartyView	the AddPartyView that is providing a new party
	 *
	 */

	public void updateAddParty(AddPartyView addPartyView) {
		controlDesk.addPartyQueue(addPartyView.getParty());
	}

	/**
	 * Receive a broadcast from a ControlDesk
	 *
	 * @param ce	the ControlDeskEvent that triggered the handler
	 *
	 */
	/// move this one somewhere
	public void receiveControlDeskEvent(ControlDeskEvent ce) {
		partyList.setListData((ce.getPartyQueue()));
	}
}
