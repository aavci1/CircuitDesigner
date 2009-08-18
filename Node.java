
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 08.Haz.2004
 * Time: 14:54:29
 * To change this template use File | Settings | File Templates.
 */
public class Node extends CircuitElement {
    private double radius = 5;
    public int number;

    public Node(double x, double y, int n) {
        this.x = x;
        this.y = y;
        this.number = n;
        type = 1;
    }

    public double getRadius() {
        return radius;
    }
    public void connect(CircuitElement c) {
        connections.add(c);
    }
    public boolean contains(double x, double y) {
        return (Math.abs(x - this.x) < (radius)) && (Math.abs(y - this.y) < (radius));
    }
    public void draw(Graphics g) {
        g.fillOval((int)(x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
    }
}
