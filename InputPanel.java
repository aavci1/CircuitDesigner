import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 09.Haz.2004
 * Time: 09:59:52
 * To change this template use File | Settings | File Templates.
 */

public class InputPanel extends JPanel {
    CircuitElement circuitElement;
    public InputPanel(CircuitElement c) {
        this.circuitElement = c;
        setLayout(new GridLayout(1, 3));
        JTextField nameField = new JTextField(circuitElement.name);
        nameField.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitElement.name = ((JTextField) e.getSource()).getText();
                    }

                }
        );
        add(nameField);
        switch (circuitElement.type) {
            case 2: JTextField resistanceField = new JTextField(circuitElement.resistance + "");
                    resistanceField.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.resistance = Double.parseDouble(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(resistanceField);
                    add(new JLabel("ohm"));
                    break;
            case 3: JTextField amperField = new JTextField(circuitElement.current + "");
                    amperField.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.current = Double.parseDouble(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(amperField);
                    add(new JLabel("amperes"));
                    break;
            case 4: JTextField voltageField = new JTextField(circuitElement.voltage + "");
                    voltageField.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.voltage = Double.parseDouble(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(voltageField);
                    add(new JLabel("volts"));
                    break;

            case 5: JTextField cdcsField= new JTextField(circuitElement.multiple + "");
                    cdcsField.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.multiple = Double.parseDouble(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(cdcsField);
                    add(new JLabel("volts"));
                    break;
            case 6: JTextField cdvsField = new JTextField(circuitElement.multiple+ "");
                    cdvsField.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.voltage = Double.parseDouble(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(cdvsField);
                    add(new JLabel("volts"));
                    break;
            case 7: setLayout(new GridLayout(1, 5));
                    JTextField vdcs1Field= new JTextField(circuitElement.multiple + "");
                    vdcs1Field.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.node1 = Integer.parseInt(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(vdcs1Field);
                    JTextField vdcs2Field= new JTextField(circuitElement.multiple + "");
                    vdcs2Field.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.node2 = Integer.parseInt(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(vdcs2Field);
                    JTextField vdcsField= new JTextField(circuitElement.multiple + "");
                    vdcsField.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.multiple = Double.parseDouble(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(vdcsField);
                    add(new JLabel("volts"));
                    break;
            case 8: setLayout(new GridLayout(1, 5));
                    JTextField vdvs1Field= new JTextField(circuitElement.multiple + "");
                    vdvs1Field.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.node1 = Integer.parseInt(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    JTextField vdvs2Field= new JTextField(circuitElement.multiple + "");
                    add(vdvs1Field);
                    vdvs2Field.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.node2 = Integer.parseInt(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    JTextField vdvsField = new JTextField(circuitElement.multiple + "");
                    add(vdvs2Field);
                    vdvsField.addActionListener (
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                circuitElement.multiple= Double.parseDouble(((JTextField) e.getSource()).getText());
                            }
                        }
                    );
                    add(vdvsField);
                    add(new JLabel("volts"));
                    break;
        }
    }
}
