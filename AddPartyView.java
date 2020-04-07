/* AddPartyView.java
 *
 *  Version:
 * 		 $Id$
 * 
 *  Revisions:
 * 		$Log: AddPartyView.java,v $
 * 		Revision 1.7  2003/02/20 02:05:53  ???
 * 		Fixed addPatron so that duplicates won't be created.
 * 		
 * 		Revision 1.6  2003/02/09 20:52:46  ???
 * 		Added comments.
 * 		
 * 		Revision 1.5  2003/02/02 17:42:09  ???
 * 		Made updates to migrate to observer model.
 * 		
 * 		Revision 1.4  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/**
 * Class for GUI components need to add a party
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

/**
 * Constructor for GUI used to Add Parties to the waiting party queue.
 *  
 */

public class AddPartyView implements ActionListener, ListSelectionListener {

	private int maxSize;

	private JFrame win;
	private JButton addPatron, newPatron, remPatron, finished;
	private JList<String> partyList;
	private JList<String> allBowlers;
	private Vector<String> party;
	private Vector<String> bowlerdb;
	private ControlDeskView controlDesk;

	private String selectedNick, selectedMember;

	public AddPartyView(ControlDeskView controlDesk, int max) {

		this.controlDesk = controlDesk;
		maxSize = max;

		win = new JFrame("Add Party");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(1, 3));

		// Party Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Your Party"));

		party = new Vector<>();
		Vector<String> empty = new Vector<>();
		empty.add("(Empty)");

		Scrollable scroll= new Scrollable(this);

		partyList=scroll.Scroller(120,5,empty,partyPanel);

		// Bowler Database
		JPanel bowlerPanel = new JPanel();
		bowlerPanel.setLayout(new FlowLayout());
		bowlerPanel.setBorder(new TitledBorder("Bowler Database"));

		try {
			bowlerdb = new Vector<>(BowlerFile.getBowlers());
		} catch (Exception e) {
			System.err.println("File Error");
			bowlerdb = new Vector<>();
		}

		allBowlers=scroll.Scroller(120,8,bowlerdb,bowlerPanel);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));

		ButtonRoutine routine = new ButtonRoutine(this);
		addPatron=routine.Routine("Add to Party",buttonPanel);

		remPatron = routine.Routine("Remove Member",buttonPanel);

		newPatron = routine.Routine("New Patron",buttonPanel);

		finished = routine.Routine("Finished",buttonPanel);

		// Clean up main panel
		colPanel.add(partyPanel);
		colPanel.add(bowlerPanel);
		colPanel.add(buttonPanel);

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}



	public void actionPerformed(ActionEvent e) {
		addPatron(e);
		if (e.getSource().equals(remPatron)) {
			if (selectedMember != null) {
				party.removeElement(selectedMember);
				partyList.setListData(party);
			}
		}
		if (e.getSource().equals(newPatron)) {
			NewPatronView newPatron = new NewPatronView( this );
		}
		if (e.getSource().equals(finished)) {
			if ( party != null && party.size() > 0) {
				controlDesk.updateAddParty( this );
			}
			win.hide();
		}

	}

	private void addPatron(ActionEvent e) {
		if (e.getSource().equals(addPatron)) {
			if (selectedNick != null && party.size() < maxSize) {
				if (party.contains(selectedNick)) {
					System.err.println("Member already in Party");
				} else {
					party.add(selectedNick);
					partyList.setListData(party);
				}
			}
		}
	}

	/**
 * Handler for List actions
 * @param e the ListActionEvent that triggered the handler
 */

	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource().equals(allBowlers)) {
			selectedNick =
				((String) ((JList) e.getSource()).getSelectedValue());
		}
		if (e.getSource().equals(partyList)) {
			selectedMember =
				((String) ((JList) e.getSource()).getSelectedValue());
		}
	}

/**
 * Accessor for Party
 */

	public Vector getNames() {
		return (Vector) party.clone();
	}

/**
 * Called by NewPatronView to notify AddPartyView to update
 * 
 * @param newPatron the NewPatronView that called this method
 */

	public void updateNewPatron(NewPatronView newPatron) {
		try {
			Bowler checkBowler = BowlerFile.getBowlerInfo( newPatron.getNick() );
			if ( checkBowler == null ) {
				BowlerFile.putBowlerInfo(
					newPatron.getNick(),
					newPatron.getFull(),
					newPatron.getEmail());
				bowlerdb = new Vector<>(BowlerFile.getBowlers());
				allBowlers.setListData(bowlerdb);
				party.add(newPatron.getNick());
				partyList.setListData(party);
			} else {
				System.err.println( "A Bowler with that name already exists." );
			}
		} catch (Exception e2) {
			System.err.println("File I/O Error");
		}
	}

/**
 * Accessor for Party
 */

	public Vector<String> getParty() {
		return (Vector<String>) party.clone();
	}

}
