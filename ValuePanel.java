
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 08.Haz.2004
 * Time: 21:24:21
 * To change this template use File | Settings | File Templates.
 */
public class ValuePanel extends JPanel {
    public CircuitPanel circuitPanel = null;
    public CircuitDesigner circuitDesigner = null;

    public ValuePanel() {
        setLayout(new GridLayout(30, 1));
    }

    public void refresh() {
        if (circuitPanel != null) {
            CircuitElement c = (CircuitElement) circuitPanel.elements.get(circuitPanel.elements.size() - 1);
            if (c.type >= CircuitPanel.RESISTOR && c.type <= CircuitPanel.TRANSISTOR) {
                add(new InputPanel(c));
            }
            circuitDesigner.repaint();
        }
    }
}
