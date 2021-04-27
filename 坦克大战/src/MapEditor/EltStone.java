package MapEditor;

import java.awt.*;

public class EltStone extends Element {
    public EltStone(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getSingletonInstance().drawStone(g,this.x,this.y);
        super.draw(g);
    }
    public Element clone() {
        return new EltStone(x,y);
    }
    public String toString(){
        return "stone="+x/34+","+y/34;
    }
}
