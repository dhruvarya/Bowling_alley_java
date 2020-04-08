import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class ScoreView {

    private int maxSize;

    private JFrame win;
    private JButton addPatron, newPatron, remPatron, finished;
    private JList<String> partyList;
    private JList<String> allBowlers;
    private Vector<String> party;
    private Vector<String> bowlerdb;
    private Integer lock;
    private ControlDeskView controlDesk;
    private HashMap<String, Integer> map;
    private String HighestScore;
    private String LowestScore;


    private String selectedNick, selectedMember;

    public ScoreView() throws IOException{
        BufferedReader in =
                new BufferedReader(new FileReader("SCOREHISTORY.DAT"));
        String Values;
        map = new HashMap<String, Integer>();

        Integer MaxScore = 0;
        Integer MinScore = 2000;
        while ((Values = in.readLine()) != null) {
            System.out.println(Values);
            String[] scoredata = Values.split("\t");
            Integer num = Integer.parseInt(scoredata[2]);
            if(num > MaxScore) {
                MaxScore = num;
                HighestScore = scoredata[0] + ": " + scoredata[2];
            }
            if(num < MinScore) {
                MinScore = num;
                LowestScore = scoredata[0] + ": " + scoredata[2];
            }
            if (map.containsKey(scoredata[0])) {
                map.put(scoredata[0], map.get(scoredata[0]) + num + 10000000);
            } else {
                map.put(scoredata[0], num + 10000000);
            }
        }

        win = new JFrame("Scores");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 3));

        // Party Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Average Scores"));

        party = new Vector<>();
        Vector<String> empty = getAverageScores(map);

        partyList = new JList<>(empty);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(5);
//        partyList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(partyList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);

        // Party Panel
        JPanel partyPanel1 = new JPanel();
        partyPanel1.setLayout(new FlowLayout());
        partyPanel1.setBorder(new TitledBorder("Highest Individual Score"));

        party = new Vector<>();
        Vector<String> empty1 = new Vector<>();
        empty1.add(HighestScore);

        partyList = new JList<>(empty1);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(5);
//        partyList.addListSelectionListener(this);
        JScrollPane partyPane1 = new JScrollPane(partyList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel1.add(partyPane1);

        // Party Panel
        JPanel partyPanel3 = new JPanel();
        partyPanel3.setLayout(new FlowLayout());
        partyPanel3.setBorder(new TitledBorder("Lowest Individual Score"));

        party = new Vector<>();
        Vector<String> empty3 = new Vector<>();
        empty3.add(LowestScore);

        partyList = new JList<>(empty3);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(5);
//        partyList.addListSelectionListener(this);
        JScrollPane partyPane3 = new JScrollPane(partyList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel3.add(partyPane3);

        // Party Panel
        JPanel partyPanel2 = new JPanel();
        partyPanel2.setLayout(new FlowLayout());
        partyPanel2.setBorder(new TitledBorder("Top Player(Best Avg)"));

        party = new Vector<>();
        Vector<String> empty2 = new Vector<>();
        empty2.add(getTopScorer(map));

        partyList = new JList<>(empty2);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(5);
//        partyList.addListSelectionListener(this);
        JScrollPane partyPane2 = new JScrollPane(partyList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel2.add(partyPane2);

        // Clean up main panel
        colPanel.add(partyPanel);
        colPanel.add(partyPanel1);
        colPanel.add(partyPanel3);
        colPanel.add(partyPanel2);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();
    }

    private Vector<String> getAverageScores(HashMap<String, Integer> map) {
        Vector<String> temp = new Vector<>();
        for(String key: map.keySet()) {
            String content = key + ": " + String.valueOf((float)(map.get(key)%1000000)/(map.get(key)/1000000));
            temp.add(content);
        }
        return temp;
    }

    private String getTopScorer(HashMap<String, Integer> map) {
        float Highest = 0;
        String Content = "";
        for(String key: map.keySet()) {
            if (Highest < (float)(map.get(key)%1000000)/(map.get(key)/1000000)) {
                Highest = (float)(map.get(key)%1000000)/(map.get(key)/1000000);
                Content = key + ": " + String.valueOf((float)(map.get(key)%1000000)/(map.get(key)/1000000));
            }
        }
        return Content;
    }
}
