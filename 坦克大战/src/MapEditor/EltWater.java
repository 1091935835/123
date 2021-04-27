package MapEditor;

import java.awt.*;

public class EltWater extends Element{
    public EltWater(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getSingletonInstance().drawWater(g,this.x,this.y);
    }
    public Element clone() {
        return new EltWater(x,y);
    }
    public String toString(){
        return "water="+x/34+","+y/34;
    }
}
