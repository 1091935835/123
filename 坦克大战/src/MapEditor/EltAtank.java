package MapEditor;

import java.awt.*;

public class EltAtank extends Element {
    protected int direction;
    public EltAtank(int x, int y,int direction) {
        super(x, y);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getSingletonInstance().drawAtank(g,this);
        super.draw(g);

    }

    @Override
    public Element clone() {
        return new EltAtank(x,y,direction);
    }
    public String toString(){
        return "sTankPos="+(x+17)+","+(y+17)+","+direction;
    }
}
