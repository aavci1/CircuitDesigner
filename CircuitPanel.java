
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 08.Haz.2004
 * Time: 15:17:54
 * To change this template use File | Settings | File Templates.
 */

public class CircuitPanel extends JPanel {
    public CircuitDesigner circuitDesigner;
    public ValuePanel valuePanel = null;
    public ArrayList elements = new ArrayList();
    public int nodeNumber = 0;
    public static final int NONE = 0,
                            NODE = 1,
                            RESISTOR = 2,
                            INDEPENDENT_CURRENT_SOURCE = 3,
                            INDEPENDENT_VOLTAGE_SOURCE = 4,
                            CURRENT_DEPENDENT_CURRENT_SOURCE = 5,
                            CURRENT_DEPENDENT_VOLTAGE_SOURCE = 6,
                            VOLTAGE_DEPENDENT_CURRENT_SOURCE = 7,
                            VOLTAGE_DEPENDENT_VOLTAGE_SOURCE = 8,
                            TRANSISTOR = 9,
                            DIODE = 10,
                            OPAMP = 11;
    private int selection = 0;
    private CircuitElement selected = null, dragging = null, mouseOver = null;
    private Node first = null, second = null, third = null;

    public CircuitPanel() {
        addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        CircuitElement c;
                        selected = null;
                        for (int i = 0; i < elements.size(); i++) {
                            c = (CircuitElement) elements.get(i);
                            if (c.contains(e.getX(), e.getY())) {
                                selected = c;
                                if (selection != NONE) {
                                    if (c instanceof Node && selection >= RESISTOR) {
                                        Node n = (Node) c;
                                        if (first == null) {
                                            first = n;
                                        } else if (second == null) {
                                            second = n;
                                            if (selection != TRANSISTOR && selection != OPAMP) {
                                                switch (selection) {
                                                    case 2: c = new Resistor(first, second); break;
                                                    case 3: c = new IndependentCurrentSource(first, second); break;
                                                    case 4: c = new IndependentVoltageSource(first, second); break;
                                                    case 5: c = new CurrentDependentCurrentSource(first, second); break;
                                                    case 6: c = new CurrentDependentVoltageSource(first, second); break;
                                                    case 7: c = new VoltageDependentCurrentSource(first, second); break;
                                                    case 8: c = new VoltageDependentVoltageSource(first, second); break;
                                                    case 10: c = new Diode(first, second); break;
                                                }
                                                elements.add(c);
                                                valuePanel.refresh();
                                                first.connect(c);
                                                second.connect(c);
                                                first = null;
                                                second = null;
                                                selected = null;
                                            }
                                        } else if(third == null) {
                                            if (selection == TRANSISTOR || selection == OPAMP) {
                                                third = n;
                                                switch(selection) {
                                                    case 9: c = new Transistor(first, second, third); break;
                                                    case 11: c = new Opamp(first, second, third); break;
                                                }
                                                elements.add(c);
                                                valuePanel.refresh();
                                                first.connect(c);
                                                second.connect(c);
                                                third.connect(c);
                                                first = null;
                                                second = null;
                                                third = null;
                                                selected = null;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (selection == NODE && selected == null) {
                            c = new Node(e.getX(), e.getY(), nodeNumber);
                            nodeNumber++;
                            elements.add(c);
                        }
                        repaint();
                    }
                    public void mousePressed(MouseEvent e) {
                        for (int i = 0; i < elements.size(); i++) {
                            CircuitElement c = (CircuitElement) elements.get(i);
                            if (c.contains(e.getX(), e.getY())) {
                                dragging = c;
                            }
                        }
                        repaint();
                    }
                    public void mouseReleased(MouseEvent e) {
                        dragging = null;
                        repaint();
                    }
                }
        );
        addMouseMotionListener(
                new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        if (dragging != null) {
                            dragging.setX(e.getX());
                            dragging.setY(e.getY());
                            repaint();
                        }
                    }
                    public void mouseMoved(MouseEvent e) {
                        mouseOver = null;
                        for (int i = 0; i < elements.size(); i++) {
                            CircuitElement c = (CircuitElement) elements.get(i);
                            if (c.contains(e.getX(), e.getY())) {
                                mouseOver = c;
                            }
                        }
                        repaint();
                    }
                }
        );
    }
    public void setCurrent(int c) {
        selection = c;
        first = null;
        second = null;
        third = null;
        selected = null;
        dragging = null;
        mouseOver = null;
    }
    public void reset() {
        elements = new ArrayList();
        first = null;
        second = null;
        third = null;
        selected = null;
        dragging = null;
        mouseOver = null;
        selection = NONE;
        repaint();
    }
    public void write() {
        try {
            FileWriter fw = new FileWriter("circuit.dat");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i =  0; i < elements.size(); i++) {
                CircuitElement  c = (CircuitElement) elements.get(i);
                if (c instanceof Resistor) {
                    Resistor r = (Resistor) c;
                    bw.write("R " + r.name + " " + r.left+ " " + r.right + " 0 0 0 0 0 " + r.resistance + "\n");
                } else if (c instanceof IndependentCurrentSource) {
                    IndependentCurrentSource ics = (IndependentCurrentSource) c;
                    bw.write("CS " + ics.name + " " + ics.left + " " + ics.right + " 0 0 0 0 0 " + ics.current + "\n");
                } else if (c instanceof IndependentVoltageSource) {
                    IndependentVoltageSource ivs = (IndependentVoltageSource) c;
                    bw.write("VS " + ivs.name + " " + ivs.left + " " + ivs.right + " 0 0 0 0 0 " + ivs.voltage + "\n");
                } else if (c instanceof CurrentDependentCurrentSource) {
                    CurrentDependentCurrentSource cscs = (CurrentDependentCurrentSource) c;
                    bw.write("CDCS " + cscs.name + " " + cscs.left + " 0 " + cscs.right + " " + cscs.node1 + " " + cscs.node2 + " 0 0 " + cscs.multiple + "\n");
                } else if (c instanceof CurrentDependentVoltageSource) {
                    CurrentDependentVoltageSource cscs = (CurrentDependentVoltageSource) c;
                    bw.write("CDVS " + cscs.name + " " + cscs.left + " 0 " + cscs.right + " " + cscs.node1 + " " + cscs.node2 + " 0 0 " + cscs.multiple + "\n");
                } else if (c instanceof VoltageDependentCurrentSource) {
                    VoltageDependentCurrentSource cscs = (VoltageDependentCurrentSource) c;
                    bw.write("VDCS " + cscs.name + " " + cscs.left + " 0 " + cscs.right + " " + cscs.node1 + " " + cscs.node2 + " 0 0 " + cscs.multiple + "\n");
                } else if (c instanceof VoltageDependentVoltageSource) {
                    VoltageDependentVoltageSource cscs = (VoltageDependentVoltageSource) c;
                    bw.write("VDVS " + cscs.name + " " + cscs.left + " 0 " + cscs.right + " " + cscs.node1 + " " + cscs.node2 + " 0 0 " + cscs.multiple + "\n");
                } else if (c instanceof Diode) {
                    Diode diode = (Diode) c;
                    bw.write("D " + diode.name + " " + diode.left + " " + diode.right + " 0 0 0 0 0 0 0" + "\n");
                } else if (c instanceof Transistor) {
                    Transistor transistor = (Transistor) c;
                    bw.write("T " + transistor.name + " " + transistor.b + " " + transistor.e + " " + transistor.c + " 0 0 0 0 0" + transistor.beta + "\n");
                } else if (c instanceof Opamp) {
                    Opamp opamp = (Opamp) c;
                    bw.write("O " + opamp.name + " " + opamp.o + " " + opamp.n + " " + opamp.i + " 0 0 0 0 0 0\n");                    
                }
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < elements.size(); i++) {
            CircuitElement c = (CircuitElement) elements.get(i);
            if (c == selected) {
                g.setColor(Color.RED);
            } else if (c == first || c == second || c == third) {
                g.setColor(Color.ORANGE);
            } else if (c == dragging) {
                g.setColor(Color.GRAY);
            } else if (c == mouseOver) {
                g.setColor(Color.CYAN);
            } else {
                g.setColor(Color.BLUE);
            }
            c.draw(g);
        }
    }
}
