import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.Vector;

public class Scrollable {
    ListSelectionListener object;
    public Scrollable(ListSelectionListener obj) {
        object = obj;
    }

    public JList<String> Scroller(int width, int rowcount, Vector<String> db, JPanel panel){
        JList<String> List = new JList<>(db);
        List.setFixedCellWidth(width);
        List.setVisibleRowCount(rowcount);
        JScrollPane Pane = new JScrollPane(List);
        Pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        if (object != null)
            List.addListSelectionListener(object);

        panel.add(Pane);
        return List;
    }


}
