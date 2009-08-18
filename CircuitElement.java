
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: champ9
 * Date: 08.Haz.2004
 * Time: 14:45:21
 * To change this template use File | Settings | File Templates.
 */

public abstract class CircuitElement {
    protected ArrayList connections;
    protected int node1 = 0, node2 = 0;
    protected double voltage, current, resistance,  beta, multiple;
    protected String name;
    protected double x, y;
    protected int type;

    public CircuitElement() {
        connections = new ArrayList();
        voltage = 0;
        current = 0;
        resistance = 0;
        multiple = 0;
        beta = 0;
        name = "";
        x = 0;
        y = 0;
        type = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public abstract boolean contains(double x, double y);
    public abstract void draw(Graphics g);
}
