
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 08.Haz.2004
 * Time: 15:30:50
 * To change this template use File | Settings | File Templates.
 */
public class CircuitDesigner extends JFrame {
    ValuePanel valuePanel = new ValuePanel();
    CircuitPanel circuitPanel = new CircuitPanel();
    JFileChooser fileChooser = new JFileChooser();
    JButton calculateButton = new JButton("Calculate");
    Font menuFont = new Font("Tahoma", Font.BOLD, 11);
    JTextArea resultArea = new JTextArea();
    CircuitDesigner cd = this;

    public CircuitDesigner() {
        super("CircuitDesigner 2.0");

        valuePanel.circuitPanel = circuitPanel;
        circuitPanel.valuePanel = valuePanel;
        valuePanel.circuitDesigner = cd;
        circuitPanel.circuitDesigner = cd;

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(menuFont);
        fileMenu.setMnemonic('f');

        JMenuItem openItem = new JMenuItem("Open...");
        openItem.setFont(menuFont);
        openItem.setMnemonic('o');
        openItem.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_MASK));
        openItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fileChooser.showOpenDialog(null);
                    }
                }
        );
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setFont(menuFont);
        saveItem.setMnemonic('s');
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_MASK));
        saveItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fileChooser.showSaveDialog(null);
                    }
                }
        );
        fileMenu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save As...");
        saveAsItem.setFont(menuFont);
        saveAsItem.setMnemonic('a');
        saveAsItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fileChooser.showSaveDialog(null);
                    }
                }
        );
        fileMenu.add(saveAsItem);

        JMenuItem closeItem = new JMenuItem("Close");
        closeItem.setFont(menuFont);
        closeItem.setMnemonic('c');
        fileMenu.add(closeItem);


        fileMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(menuFont);
        exitItem.setAccelerator(KeyStroke.getKeyStroke('X', KeyEvent.ALT_MASK));
        exitItem.setMnemonic('x');
        exitItem.setFocusTraversalKeysEnabled(true);
        exitItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to exit without saving ?");
                        switch (choice) {
                            case JOptionPane.YES_OPTION     :   System.exit(0); break;
                            case JOptionPane.NO_OPTION      :   break;
                            case JOptionPane.CANCEL_OPTION  :   break;
                        }
                    }
                }
        );
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setFont(menuFont);
        drawMenu.setMnemonic('d');

        JMenuItem noneItem = new JMenuItem("None");
        noneItem.setFont(menuFont);
        noneItem.setMnemonic('r');
        noneItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.NONE);
                    }
                }
        );
        drawMenu.add(noneItem);

        JMenuItem nodeItem = new JMenuItem("Node", new ImageIcon("images/node.png"));
        nodeItem.setFont(menuFont);
        nodeItem.setMnemonic('n');
        nodeItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.NODE);
                    }
                }
        );
        drawMenu.add(nodeItem);

        JMenuItem resItem = new JMenuItem("Resistor", new ImageIcon("images/res.png"));
        resItem.setFont(menuFont);
        resItem.setMnemonic('r');
        resItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.RESISTOR);
                    }
                }
        );
        drawMenu.add(resItem);

        JMenuItem cdcsItem = new JMenuItem("Current Dependent Current Source", new ImageIcon("images/dcs.png"));
        cdcsItem.setFont(menuFont);
        cdcsItem.setMnemonic('d');
        cdcsItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.CURRENT_DEPENDENT_CURRENT_SOURCE);
                    }
                }
        );
        drawMenu.add(cdcsItem);

        JMenuItem cdvsItem = new JMenuItem("Current Dependent Voltage Source", new ImageIcon("images/dvs.png"));
        cdvsItem.setMnemonic('d');
        cdvsItem.setFont(menuFont);
        cdvsItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.CURRENT_DEPENDENT_VOLTAGE_SOURCE);
                    }
                }
        );
        drawMenu.add(cdvsItem);

        JMenuItem vdcsItem = new JMenuItem("Voltage Dependent Current Source", new ImageIcon("images/dcs.png"));
        vdcsItem.setFont(menuFont);
        vdcsItem.setMnemonic('d');
        vdcsItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.VOLTAGE_DEPENDENT_CURRENT_SOURCE);
                    }
                }
        );
        drawMenu.add(vdcsItem);

        JMenuItem vdvsItem = new JMenuItem("Voltage Dependent Voltage Source", new ImageIcon("images/dvs.png"));
        vdvsItem.setMnemonic('d');
        vdvsItem.setFont(menuFont);
        vdvsItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.VOLTAGE_DEPENDENT_VOLTAGE_SOURCE);
                    }
                }
        );
        drawMenu.add(vdvsItem);

        JMenuItem icsItem = new JMenuItem("Independent Current Source", new ImageIcon("images/ics.png"));
        icsItem.setMnemonic('i');
        icsItem.setFont(menuFont);
        icsItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.INDEPENDENT_CURRENT_SOURCE);
                    }
                }
        );
        drawMenu.add(icsItem);

        JMenuItem ivsItem = new JMenuItem("Independent Voltage Source", new ImageIcon("images/ivs.png"));
        ivsItem.setMnemonic('i');
        ivsItem.setFont(menuFont);
        ivsItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.INDEPENDENT_VOLTAGE_SOURCE);
                    }
                }
        );
        drawMenu.add(ivsItem);

        JMenuItem diodeItem = new JMenuItem("Diode", new ImageIcon("images/diode.png"));
        diodeItem.setMnemonic('d');
        diodeItem.setFont(menuFont);
        diodeItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.DIODE);
                    }
                }
        );
        drawMenu.add(diodeItem);

        JMenuItem transistorItem = new JMenuItem("Transistor", new ImageIcon("images/transistor.png"));
        transistorItem.setMnemonic('t');
        transistorItem.setFont(menuFont);
        transistorItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.TRANSISTOR);
                    }
                }
        );
        drawMenu.add(transistorItem);

        JMenuItem opampItem = new JMenuItem("Opamp", new ImageIcon("images/opamp.png"));
        opampItem.setMnemonic('o');
        opampItem.setFont(menuFont);
        opampItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel.setCurrent(CircuitPanel.OPAMP);
                    }
                }
        );
        drawMenu.add(opampItem);

        drawMenu.addSeparator();

        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.setFont(menuFont);
        clearItem.setMnemonic('c');
        clearItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        circuitPanel = new CircuitPanel();
                        valuePanel = new ValuePanel();
                        valuePanel.circuitDesigner = cd;
                        circuitPanel.circuitDesigner = cd;
                    }
                }
        );
        drawMenu.add(clearItem);
        menuBar.add(drawMenu);

        setJMenuBar(menuBar);

        JPanel eastPanel = new JPanel(new GridLayout(2, 1));
        eastPanel.add(new JScrollPane(valuePanel));
        eastPanel.add(new JScrollPane(resultArea));
        resultArea.setEditable(false);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, circuitPanel, eastPanel);
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(800);
        getContentPane().add(splitPane);
        calculateButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    circuitPanel.write();
                    Circuit circuit = new Circuit() ;
                    double[] d = circuit.getResult() ;
                    ArrayList str = new ArrayList() ;
                    int c = 1 ;
                    str.add(new String("Node Currents")) ;
                    int j = 0;
                    for(int i=0; i<circuitPanel.elements.size(); i++) {
                        if (!(circuitPanel.elements.get(i) instanceof Node)) {
                            str.add(new String("I"+(j+1)+" : "+d[j])) ;
                            j++;
                        }
                    }
                    str.add(new String("Node Voltages")) ;

                    for(int i=j-1; i<d.length; i++) {
                        str.add(new String("V"+(c++)+" : "+d[i])) ;
                    }
                    resultArea.setText("");
                    for(int i=0; i<str.size(); i++) {
                        resultArea.append(str.get(i).toString()+"\n");
                    }

                }

            }
        );
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocation(263, 185);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new CircuitDesigner();
    }
}
