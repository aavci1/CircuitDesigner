
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 08.Haz.2004
 * Time: 21:12:41
 * To change this template use File | Settings | File Templates.
 */
public class IndependentVoltageSource extends CircuitElement {
    double  lines[][] =  {
                         {-3,  0, -3,  0},
                         {-2,  0,  0,  0},
                         {-1, -1, -1, 1},
                         { 1,  0,  2,  0},
                         { 3,  0,  3,  0}
                        };
    double xRatio = 4;
    double yRatio = 4;
    double width = 24;
    double height = 24;
    int left, right;
    public IndependentVoltageSource(Node p, Node n) {
        connections.add(p);
        connections.add(n);
        x = (p.getX() + n.getX()) / 2;
        y = (p.getY() + n.getY()) / 2;
        left = p.number;
        right = n.number;
        name = "IVS";
        type = 4;
    }

    public void draw(Graphics g) {
        Node p = (Node)(connections.get(0));
        Node n = (Node)(connections.get(1));
        double last[][];
        double sin, cos;

        if (p != null && n != null) {
            sin = (p.getY() - n.getY()) / Math.hypot(n.getX() - p.getX(), n.getY() - p.getY());
            cos = (n.getX() - p.getX()) / Math.hypot(n.getX() - p.getX(), n.getY() - p.getY());
            last = new double[lines.length][4];

            g.drawOval((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);            
            g.drawString(name + " : " + voltage + " volts", (int) (x - width / 2), (int) (y - height / 2));
            for (int i = 0; i < lines.length; i++) {
                last[i][0] = x + (lines[i][0] * xRatio * cos + lines[i][1] * yRatio * sin);
                last[i][1] = y - (lines[i][0] * xRatio * sin - lines[i][1] * yRatio * cos);
                last[i][2] = x + (lines[i][2] * xRatio * cos + lines[i][3] * yRatio * sin);
                last[i][3] = y - (lines[i][2] * xRatio * sin - lines[i][3] * yRatio * cos);

                g.drawLine((int)(last[i][0]), (int)(last[i][1]), (int)(last[i][2]), (int)(last[i][3]));
            }

            cos = (x - width / 2 - p.getX()) / Math.hypot(x - width / 2 - p.getX(), y - height / 2 - p.getY());
            sin = (p.getY() - y - height / 2) / Math.hypot(x - width / 2 - p.getX(), y - height / 2 - p.getY());

            g.drawLine((int) (p.getX() + p.getRadius() * cos), (int) (p.getY() - p.getRadius() * sin), (int) last[0][0], (int) last[0][1]);

            cos = (n.getX() - x - width / 2) / Math.hypot(x - width / 2 - n.getX(), y - height / 2 - n.getY());
            sin = (y - height / 2 - n.getY()) / Math.hypot(x - width / 2 - n.getX(), y - height / 2 - n.getY());

            g.drawLine((int) last[last.length - 1][2], (int) last[last.length - 1][3],(int) (n.getX() - n.getRadius() * cos), (int) (n.getY() + n.getRadius() * sin));
        }
    }

    public boolean contains(double x, double y) {
        return (Math.abs(x - this.x) < (width / 2)) && (Math.abs(y - this.y) < (height / 2));
    }
}
