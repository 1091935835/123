package MapEditor;

import java.awt.*;

public class EltBrick extends Element {
    public EltBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getSingletonInstance().drawBrick(g,this.x,this.y);
        super.draw(g);
    }

    @Override
    public Element clone() {
        return new EltBrick(x,y);

    }
    public String toString(){
        return "brick="+x/34+","+y/34;
    }
}
