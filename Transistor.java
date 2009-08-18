
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 08.Haz.2004
 * Time: 17:18:14
 * To change this template use File | Settings | File Templates.
 */
public class Transistor extends CircuitElement {
    private double lines[][] =  {
                         {-2, -2, 1, -1},
                         {-2,  2, 1,  1},
                         {-0.5, 1.5, 0, 0},
                         {-0.5, 1.5, 0.5, 2.5},
                         { 1, -2, 1,  2},
                         { 1,  0, 2, 0}
                        };
    double xRatio = 5;
    double yRatio = 5;
    double width = 20;
    double height = 20;
    int b, c, e;

    public Transistor(Node n, Node i, Node o) {
        connections.add(n);
        connections.add(i);
        connections.add(o);
        c = n.number;
        e = i.number;
        b = o.number;
        x = (n.getX() + i.getX() + o.getX()) / 3;
        y = (n.getY() + i.getY() + o.getY()) / 3;
        name = "T";
        type = 10;
    }

    public void draw(Graphics g) {
        Node n = (Node)(connections.get(0));
        Node i = (Node)(connections.get(1));
        Node o = (Node)(connections.get(2));

        double last[][];
        double sin = 0, cos = 1;

        if (n != null && i != null && o != null) {
            sin = ((n.getY() + i.getY()) / 2 - o.getY()) / Math.hypot(o.getX() - (n.getX() + i.getX()) / 2 , o.getY() - (n.getY() + i.getY()) / 2 );
            cos = (o.getX() - (n.getX() + i.getX()) / 2) / Math.hypot(o.getX() - (n.getX() + i.getX()) / 2 , o.getY() - (n.getY() + i.getY()) / 2 );

            last = new double[lines.length][4];

            for (int j = 0; j < lines.length; j++) {
                last[j][0] = x + (lines[j][0] * xRatio * cos + lines[j][1] * yRatio * sin);
                last[j][1] = y - (lines[j][0] * xRatio * sin - lines[j][1] * yRatio * cos);
                last[j][2] = x + (lines[j][2] * xRatio * cos + lines[j][3] * yRatio * sin);
                last[j][3] = y - (lines[j][2] * xRatio * sin - lines[j][3] * yRatio * cos);

                g.drawLine((int)(last[j][0]), (int)(last[j][1]), (int)(last[j][2]), (int)(last[j][3]));
            }

            cos = (n.getX() - x - width / 2) / Math.hypot(x - width / 2 - n.getX(), y - height / 2 - n.getY());
            sin = (y - height / 2 - n.getY()) / Math.hypot(x - width / 2 - n.getX(), y - height / 2 - n.getY());

            g.drawLine((int) last[0][0], (int) last[0][1],(int) (n.getX() - n.getRadius() * cos), (int) (n.getY() + n.getRadius() * sin));

            cos = (i.getX() - x - width / 2) / Math.hypot(x - width / 2 - i.getX(), y - height / 2 - i.getY());
            sin = (y - height / 2 - i.getY()) / Math.hypot(x - width / 2 - i.getX(), y - height / 2 - i.getY());

            g.drawLine((int) last[1][0], (int) last[1][1],(int) (i.getX() - i.getRadius() * cos), (int) (i.getY() + i.getRadius() * sin));

            cos = (o.getX() - x - width / 2) / Math.hypot(x - width / 2 - o.getX(), y - height / 2 - o.getY());
            sin = (y - height / 2 - o.getY()) / Math.hypot(x - width / 2 - o.getX(), y - height / 2 - o.getY());

            g.drawLine((int) last[last.length - 1][2], (int) last[last.length - 1][3],(int) (o.getX() - o.getRadius() * cos), (int) (o.getY() + o.getRadius() * sin));
        }
    }

    public boolean contains(double x, double y) {
        return (Math.abs(x - this.x) < (width / 2)) && (Math.abs(y - this.y) < (height / 2));
    }

}
