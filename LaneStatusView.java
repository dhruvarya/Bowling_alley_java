/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private JPanel jp;

	private JLabel curBowler;
	private JLabel pinsDown;
	private JButton viewLane;
	private JButton viewPinSetter, maintenance;

	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	int laneNum;

	boolean laneShowing;
	boolean psShowing;

	public LaneStatusView(Lane lane, int laneNum ) {

		this.lane = lane;
		this.laneNum = laneNum;

		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		ps.subscribe(psv);

		lv = new LaneView( lane, laneNum );
		lane.subscribe(lv);


		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );
		JLabel fLabel = new JLabel( "Foul: " );
		JLabel foul = new JLabel(" ");
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		ButtonRoutine routine = new ButtonRoutine(this);
		viewLane= routine.Routine("View Lane",buttonPanel);
		viewPinSetter = routine.Routine("Pinsetter",buttonPanel);
		maintenance = routine.Routine("     ",buttonPanel);
		maintenance.setBackground( Color.GREEN );

		viewLane.setEnabled( false );
		viewPinSetter.setEnabled( false );

		jp.add( cLabel );
		jp.add( curBowler );
		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);

	}

	public JPanel showLane() {
		return jp;
	}

	public void actionPerformed( ActionEvent e ) {

		viewPinSet(e);
		if (e.getSource().equals(viewLane) && lane.isPartyAssigned() ) {
				if (!laneShowing) {
					lv.show();
					laneShowing=true;
				} else {
					lv.hide();
					laneShowing=false;
				}
		}
		if (e.getSource().equals(maintenance) && lane.isPartyAssigned() ) {
				lane.unPauseGame();
				maintenance.setBackground( Color.GREEN );
		}
	}

	private void viewPinSet(ActionEvent e) {
		if ( lane.isPartyAssigned() && e.getSource().equals(viewPinSetter)) {
				if (!psShowing) {
					psv.show();
					psShowing=true;
				} else {
					psv.hide();
					psShowing=false;
				}
		}
	}

	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( le.getBowler().getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.setBackground( Color.RED );
		}	
		if (!lane.isPartyAssigned()) {
			viewLane.setEnabled( false );
			viewPinSetter.setEnabled( false );
		}
		else {
			viewLane.setEnabled( true );
			viewPinSetter.setEnabled( true );
		}
	}

	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( (Integer.valueOf(pe.totalPinsDown())).toString() );

	}

}
