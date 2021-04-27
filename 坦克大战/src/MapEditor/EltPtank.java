package MapEditor;

import java.awt.*;

public class EltPtank extends Element {
    protected int direction;
    public EltPtank(int x, int y,int direction) {
        super(x, y);
        this.direction = direction;
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getSingletonInstance().drawPtank(g,this);
        super.draw(g);
    }

    public int getDirection() {
        return direction;
    }
    public Element clone() {
        return new EltPtank(x,y,direction);
    }
    public String toString(){
        return "pTankPos="+(x+17)+","+(y+17)+","+direction;
    }
}
