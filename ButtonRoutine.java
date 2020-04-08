import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonRoutine {
    ActionListener listener;
    public ButtonRoutine(ActionListener list) {
        listener=list;
    }

    public JButton Routine(String button_name, JPanel controlsPanel) {
        JButton button = new JButton(button_name);
        JPanel Panel = new JPanel();
        Panel.setLayout(new FlowLayout());
        button.addActionListener(listener);
        Panel.add(button);
        controlsPanel.add(Panel);
        return button;
    }

}
