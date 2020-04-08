import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ControlsPanelRoutine {
    JPanel controlsPanel;
    public ControlsPanelRoutine(ControlDeskView controlDeskView) {
        this.controlsPanel = new JPanel();
        this.controlsPanel.setLayout(new GridLayout(3, 1));
        this.controlsPanel.setBorder(new TitledBorder("Controls"));

        ButtonRoutine routine = new ButtonRoutine(controlDeskView);
        routine.Routine("Add Party",this.controlsPanel);
        routine.Routine("Player Records", this.controlsPanel);
//		routine.Routine("Assign Lanes",this.controlsPanel);
        routine.Routine("Finished",this.controlsPanel);

    }

    public JPanel getPanel() {
      return this.controlsPanel;
    }
}
