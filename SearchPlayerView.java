/* AddPartyView.java
 *
 *  Version
 *  $Id$
 *
 *  Revisions:
 * 		$Log: NewPatronView.java,v $
 * 		Revision 1.3  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 *
 *
 */

/**
 * Class for GUI components need to add a patron
 *
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class SearchPlayerView implements ActionListener, Serializable {

    private JFrame win;
    private JButton search;
    private JTextField nickField;
    private JList<String> partyList;
    private Vector<String> party;

    public SearchPlayerView() {


        win = new JFrame("Search Player");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new BorderLayout());

        // Patron Panel
        JPanel patronPanel = new JPanel();
        patronPanel.setLayout(new GridLayout(3, 1));
        patronPanel.setBorder(new TitledBorder("Your Nick"));

        JPanel nickPanel = SetPanel("Nick Name");

        patronPanel.add(nickPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        Insets buttonMargin = new Insets(4, 4, 4, 4);

        ButtonRoutine routine = new ButtonRoutine(this);

        search = routine.Routine("Search",buttonPanel);

        // Party Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Scores"));

        party = new Vector<>();
        Vector<String> empty = new Vector<>();
        empty.add("(Empty)");

        partyList = new JList<>(empty);
        partyList.setFixedCellWidth(300);
        partyList.setVisibleRowCount(5);
//        partyList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(partyList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);


        // Clean up main panel
        colPanel.add(patronPanel, "Center");
        colPanel.add(buttonPanel, "East");
        colPanel.add(partyPanel, "South");
        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }

    private JPanel SetPanel(String field_name) {
        JPanel Panel = new JPanel();
        Panel.setLayout(new FlowLayout());
        JLabel Label = new JLabel(field_name);
        Panel.add(Label);
        this.nickField = new JTextField("", 15);
        Panel.add(this.nickField);
        return Panel;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(search)) {
            party.clear();
            party.add("(Empty)");
            partyList.setListData(party);
            try{
                getScores(nickField.getText());
            } catch(Exception et) {
                System.out.println(et);
            }
        }
    }

    private void getScores(String nickname) throws IOException {
        Vector<String> st = new Vector<>();
        BufferedReader in =
                new BufferedReader(new FileReader("SCOREHISTORY.DAT"));
        String Values;
        while ((Values = in.readLine()) != null) {
            String[] scoredata = Values.split("\t");
            if (scoredata[0].equals(nickname)) {
                party.removeElement("(Empty)");
                partyList.setListData(party);
                party.add(scoredata[0] + " " + scoredata[1] + " " + scoredata[2]);
                partyList.setListData(party);
            }
        }
    }
}

